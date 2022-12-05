package com.dealerapp.models;

import com.dealerapp.models.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dealers")
@DiscriminatorValue("DEALER")
public class Dealer extends User{
    @OneToMany(mappedBy="dealer")
    @JsonIgnore
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
