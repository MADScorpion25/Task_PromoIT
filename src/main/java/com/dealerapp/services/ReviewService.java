package com.dealerapp.services;

import com.dealerapp.dto.ReviewDto;
import com.dealerapp.dto.UserDto;
import com.dealerapp.models.Dealer;
import com.dealerapp.models.Review;
import com.dealerapp.repo.ReviewRepository;
import com.dealerapp.validation.exceptions.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.dealerapp.services.ImgService.deleteImg;
import static com.dealerapp.services.ImgService.uploadImg;

@Service
@Validated
@RequiredArgsConstructor
public class ReviewService {

    @Value("${upload.path}")
    private String path;

    private final ReviewRepository reviewRepository;

    private final MappingUtils mappingUtils;

    private final UserService userService;

    public ReviewDto getReviewById(long id) throws ReviewNotFoundException {
        return mappingUtils.mapToReviewDto(
                reviewRepository.findById(id)
                        .orElseThrow(() -> new ReviewNotFoundException(id)));
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

    public ReviewDto updateReview(@Valid ReviewDto reviewDto, MultipartFile fileUp) throws IOException, ReviewNotFoundException {
        Review review = mappingUtils.mapToReviewEntity(reviewDto);
        Review currentReview = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new ReviewNotFoundException(reviewDto.getId()));

        UserDto userDto = userService.getUserByLogin(reviewDto.getDealerLogin());
        Dealer dealer = (Dealer)mappingUtils.mapToReviewEntity(userDto);
        review.setDealer(dealer);

        currentReview.setTitle(review.getTitle());
        currentReview.setText(review.getText());

        String imgFileName = "";
        if(fileUp != null)imgFileName = uploadImg(fileUp, path);
        else review.setImgPath(currentReview.getImgPath());
        if(StringUtils.hasLength(imgFileName)) {
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
        if(StringUtils.hasLength(imgFileName)) review.setImgPath(imgFileName);

        return mappingUtils.mapToReviewDto(reviewRepository.save(review));
    }

    public void deleteReview(long id) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        deleteImg(review.getImgPath(), path);
        reviewRepository.deleteById(id);
    }

}
