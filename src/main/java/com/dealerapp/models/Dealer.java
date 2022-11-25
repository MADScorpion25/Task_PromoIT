package com.dealerapp.models;

import com.dealerapp.models.enums.UserRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dealers")
@DiscriminatorValue("DEALER")
public class Dealer extends User{
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "dealer_review",
            joinColumns = { @JoinColumn(name = "dealer_id") },
            inverseJoinColumns = { @JoinColumn(name = "review_id") }
    )
    private Set<Review> reviews;

    public Dealer() {
        setRole(UserRole.DEALER);
    }

    public Dealer(long id, String login, String password, Set<Review> reviews) {
        super(id, login, password);
        this.reviews = reviews;
        setRole(UserRole.DEALER);
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
