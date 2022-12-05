package com.dealerapp.services;

import com.dealerapp.dto.ReviewDto;
import com.dealerapp.dto.UserDto;
import com.dealerapp.models.*;
import com.dealerapp.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dealerapp.services.ImgService.deleteImg;
import static com.dealerapp.services.ImgService.uploadImg;

@Service
@Validated
@RequiredArgsConstructor
public class ReviewService {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final MappingUtils mappingUtils;
    @Autowired
    private final UserService userService;

    public ReviewDto getReviewById(long id){
        return mappingUtils.mapToReviewDto(reviewRepository.getReferenceById(id));
    }

    public List<ReviewDto> getReviewsList(){
        return reviewRepository.findAll()
                .stream().map(mappingUtils::mapToReviewDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsByDealer(Dealer dealer){
        return reviewRepository.findByDealer(dealer)
                .stream().map(mappingUtils::mapToReviewDto)
                .collect(Collectors.toList());
    }

    public ReviewDto updateReview(@Valid ReviewDto reviewDto, MultipartFile fileUp) throws IOException {
        Review review = mappingUtils.mapToReviewEntity(reviewDto);
        Review currentReview = reviewRepository.getReferenceById(review.getId());

        UserDto userDto = userService.getUserByLogin(reviewDto.getDealerLogin());
        Dealer dealer = (Dealer)mappingUtils.mapToReviewEntity(userDto);
        review.setDealer(dealer);

        currentReview.setTitle(review.getTitle());
        currentReview.setText(review.getText());

        String imgFileName = "";
        if(fileUp != null)imgFileName = uploadImg(fileUp, path);
        else review.setImgPath(currentReview.getImgPath());
        if(!"".equals(imgFileName)) {
            deleteImg(currentReview.getImgPath(), path);
            review.setImgPath(imgFileName);
            currentReview.setImgPath(imgFileName);
        }

        currentReview = reviewRepository.save(review);
        return mappingUtils.mapToReviewDto(currentReview);
    }

    public ReviewDto saveReview(@Valid ReviewDto reviewDto, MultipartFile file) throws IOException {
        Review review = mappingUtils.mapToReviewEntity(reviewDto);

        UserDto userDto = userService.getUserByLogin(reviewDto.getDealerLogin());
        Dealer dealer = (Dealer)mappingUtils.mapToReviewEntity(userDto);
        review.setDealer(dealer);

        String imgFileName = uploadImg(file, path);
        if(!"".equals(imgFileName)) review.setImgPath(imgFileName);

        return mappingUtils.mapToReviewDto(reviewRepository.save(review));
    }

    public void deleteReview(long id){
        Review review = reviewRepository.findById(id).get();
        deleteImg(review.getImgPath(), path);
        reviewRepository.deleteById(id);
    }

}
