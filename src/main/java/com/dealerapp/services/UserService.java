package com.dealerapp.services;

import com.dealerapp.dto.UserDto;
import com.dealerapp.models.Admin;
import com.dealerapp.models.Client;
import com.dealerapp.models.Dealer;
import com.dealerapp.models.User;
import com.dealerapp.models.enums.UserRole;
import com.dealerapp.repo.AdminRepository;
import com.dealerapp.repo.ClientRepository;
import com.dealerapp.repo.DealerRepository;
import com.dealerapp.repo.UserRepository;
import com.dealerapp.validation.exceptions.UserAlreadyExistsException;
import com.dealerapp.validation.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final DealerRepository dealerRepository;

    private final ClientRepository clientRepository;

    private final PasswordEncoder encoder;

    private final MappingUtils mappingUtils;

    public void register(@Valid UserDto userDto) throws UserAlreadyExistsException {
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        if(userRepository.existsByLogin(userDto.getLogin())) throw new UserAlreadyExistsException(userDto.getLogin());
        if(userRepository.findAll().size() == 0) {
            userDto.setRole(UserRole.ADMIN);
            Admin admin = new Admin();
            admin.setLogin(userDto.getLogin());
            admin.setPassword(userDto.getPassword());
            userRepository.save(admin);
        }
        else {
            userDto.setRole(UserRole.CLIENT);
            Client client = new Client();
            client.setLogin(userDto.getLogin());
            client.setPassword(userDto.getPassword());
            client.setRole(UserRole.CLIENT);
            userRepository.save(client);
        }
    }
    public void createUser(@Valid UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = mappingUtils.mapToReviewEntity(userDto);

        UserRole userRole = userDto.getRole();
        switch (userRole){
            case ADMIN:
                Admin admin = new Admin();
                admin.setLogin(user.getLogin());
                admin.setPassword(user.getPassword());
                userRepository.save(admin);
                break;
            case DEALER:
                Dealer dealer = new Dealer();
                dealer.setLogin(user.getLogin());
                dealer.setPassword(user.getPassword());
                userRepository.save(dealer);
                break;
            case CLIENT:
                Client client = new Client();
                client.setLogin(user.getLogin());
                client.setPassword(user.getPassword());
                userRepository.save(client);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userDto.getRole());
        }
    }

    public List<String> getUserRoles(){
        return Arrays.stream(UserRole.values()).map(UserRole::toString).collect(toList());
    }

    public User updateUser(@Valid UserDto userDto) throws UserNotFoundException {
        User currentUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));

        if(StringUtils.hasLength(userDto.getPassword())) userDto.setPassword(encoder.encode(userDto.getPassword()));
        else userDto.setPassword(currentUser.getPassword());

        currentUser.setLogin(userDto.getLogin());
        currentUser.setPassword(userDto.getPassword());

        currentUser = userRepository.save(currentUser);
        return currentUser;
    }

    public UserDto getUserById(long id){
        User user = userRepository.getReferenceById(id);
        user.setPassword("");
        return userDtoAddition(user);
    }

    public UserDto getUserByLogin(String login) throws UserNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        return userDtoAddition(user);
    }

    public List<UserDto> getAdmins(){
        return adminRepository.findAll()
                .stream().map(this::userDtoAddition)
                .collect(toList());
    }

    public List<UserDto> getDealers(){
        return dealerRepository.findAll()
                .stream().map(this::userDtoAddition)
                .collect(toList());
    }

    public List<UserDto> getClients(){
        return clientRepository.findAll()
                .stream().map(this::userDtoAddition)
                .collect(toList());
    }

    private UserDto userDtoAddition(User user){
        UserDto userDto = mappingUtils.mapToReviewDto(user);

        if(user instanceof Admin){
            userDto.setRole(UserRole.ADMIN);
        }
        else if(user instanceof Dealer){
            userDto.setRole(UserRole.DEALER);
        }
        else if(user instanceof Client){
            userDto.setRole(UserRole.CLIENT);
        }
        return userDto;
    }
}
