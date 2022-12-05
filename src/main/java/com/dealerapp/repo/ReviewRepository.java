package com.dealerapp.repo;

import com.dealerapp.models.Dealer;
import com.dealerapp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDealer(Dealer dealer);
}
