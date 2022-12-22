package com.sparta.be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostListResponseDto extends ResponseDto{
    List<PostResponseDto> postList = new ArrayList<>();

    public void addPostList(PostResponseDto postResponseDto) {
        postList.add(postResponseDto);
    }

    public PostListResponseDto() {
        super("게시글 목록 조회 성공", HttpStatus.OK.value());
    }
}
