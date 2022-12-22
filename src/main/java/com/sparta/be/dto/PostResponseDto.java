package com.sparta.be.dto;

import com.sparta.be.entity.Post;
import com.sparta.be.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto{
    private Long id;
    private String username;

    private String title;

    private String content;

    private String category;

    private String imageUrl;

    private int like;

    private int views;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.imageUrl = post.getImageUrl();
        this.like = post.getLikes().size();
        this.views = post.getViews();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

}
