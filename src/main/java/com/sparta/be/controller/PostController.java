package com.sparta.be.controller;

import com.sparta.be.dto.PostRequestDto;
import com.sparta.be.security.UserDetailsImpl;
import com.sparta.be.service.AwsS3Service;
import com.sparta.be.service.LikeService;
import com.sparta.be.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"Post API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final AwsS3Service awsS3Service;
    private final LikeService likeService;

    //게시글 전체 조회
    @ApiOperation(value = "게시글 전체 조회")
    @GetMapping("/posts")
    public ResponseEntity<?> posts() {
        return postService.posts();
    }
    @ApiOperation(value = "메인화면 카테고리별 top5")
    @GetMapping("/tops")
    public ResponseEntity<?> Top() {
        return postService.Top();
    }


    //게시글 작성 및 파일 업로드
    @ApiOperation(value = "게시 글 작성 및 파일 업로드")
    @PostMapping("/post")
    public ResponseEntity<?> uploadFile(@ModelAttribute PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return postService.savePost(postRequestDto, userDetails.getUser());
    }

    //게시글 상세 조회
    @ApiOperation(value = "게시글 상세 조회")
    @GetMapping("/post/{id}")
    public ResponseEntity<?> postDetailed(@PathVariable Long id) {
        return postService.postDetailed(id);
    }

    //게시글 삭제
    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> postDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.postDelete(id, userDetails.getUser());
    }

    //게시글 수정
    @ApiOperation(value = "게시글 수정")
    @PatchMapping("post/{id}")
    public ResponseEntity<?> postUpdate(@PathVariable Long id,
                                        @ModelAttribute PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.postUpdate(id,postRequestDto,userDetails.getUser());
    }

    // 게시글 좋아요
    // ResponseEntity 구조 : HttpStatus, HttpHeaders, HttpBody
    @ApiOperation(value = "게시글 좋아요")
    @PostMapping("/post/{postId}/like")
    private ResponseEntity<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postId, userDetails.getUsername());
    }

}
