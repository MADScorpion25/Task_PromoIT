package com.dealerapp.controllers;

import com.dealerapp.dto.ReviewDto;
import com.dealerapp.dto.UserDto;
import com.dealerapp.models.Client;
import com.dealerapp.models.Dealer;
import com.dealerapp.models.Order;
import com.dealerapp.models.Review;
import com.dealerapp.services.MappingUtils;
import com.dealerapp.services.ReviewService;
import com.dealerapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final MappingUtils mappingUtils;

    @GetMapping
    public List<ReviewDto> getReviews(){
        return reviewService.getReviewsList();
    }

    @GetMapping("/{id}")
    public ReviewDto getReview(@PathVariable long id){
        return reviewService.getReviewById(id);
    }

    @GetMapping("/dealer/{login}")
    public List<ReviewDto> getOrdersByClient(@PathVariable String login){
        UserDto userDto = userService.getUserByLogin(login);
        Dealer dealer = (Dealer) mappingUtils.mapToReviewEntity(userDto);
        return reviewService.getReviewsByDealer(dealer);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReviewDto> createReview(@ModelAttribute ReviewDto reviewDto,
                                               @RequestParam("fileUp") MultipartFile file) throws URISyntaxException, IOException {
        ReviewDto saveReview = reviewService.saveReview(reviewDto, file);
        return ResponseEntity.created(new URI("/orders/" + saveReview.getId())).body(saveReview);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/edit/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @ModelAttribute ReviewDto reviewDto, @RequestParam(value = "fileUp", required = false) MultipartFile file) throws IOException {
        ReviewDto review = reviewService.updateReview(reviewDto, file);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable long id){
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}
