package com.example.testing.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModel {

    private String userId;
    private String reviewId;
    private String reviewStar;
    private String reviewText;
    private String writerNickName;
    private Long createAt;

}
