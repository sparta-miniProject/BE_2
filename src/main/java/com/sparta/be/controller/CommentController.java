package com.sparta.be.controller;

import com.sparta.be.dto.CommentRequestDto;
import com.sparta.be.security.UserDetailsImpl;
import com.sparta.be.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Comment API"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/posts/{postid}/comment")
    public ResponseEntity<?> createComment(@PathVariable Long postid, @RequestBody CommentRequestDto commentRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createComment(postid, commentRequestDto, userDetails.getUser());

    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping ("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<?> updateComment(@PathVariable Long postid ,@PathVariable Long commentid, @RequestBody CommentRequestDto commentRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {//

        return  commentService.updateComment(postid, commentid, commentRequestDto, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postid,@PathVariable Long commentid,@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.deleteComment(postid,commentid, userDetails.getUser());
    }
}
