package com.dealerapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Column(length = 65535, columnDefinition="TEXT")
    @Type(type = "text")
    private String text;
    @Column(name = "img_path")
    private String imgPath;
    @ManyToMany(mappedBy = "reviews")
    private Set<Dealer> dealers;

    public Review() {
    }

    public Review(long id, String title, String text, String imgPath, Set<Dealer> dealers) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.imgPath = imgPath;
        this.dealers = dealers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Set<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(Set<Dealer> dealers) {
        this.dealers = dealers;
    }
}
