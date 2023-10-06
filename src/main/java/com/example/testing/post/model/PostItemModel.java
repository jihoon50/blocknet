package com.example.testing.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostItemModel {
    private String userId ;
    private String userNickname;
    private String userPhone;
    private String postId;
    //private List<String> postImageUrl;
    private String postImageUrl;
    private String postTitle;
    private String postPrice;
    private String postContent;
    private Long createdAt;

}
