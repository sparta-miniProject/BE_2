package com.sparta.be.service;

import com.sparta.be.dto.CommentRequestDto;
import com.sparta.be.dto.ResponseDto;
import com.sparta.be.entity.Comment;
import com.sparta.be.entity.Post;
import com.sparta.be.entity.User;
import com.sparta.be.repository.CommentRepository;
import com.sparta.be.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<?> createComment(Long postid, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("없는 글입니다.") //IllegalArgumentException: 적합하지 않거나(illegal) 적절하지 못한(inappropriate) 인자를 메소드에 넘겨주었을 때 발생
        );
        Comment save = new Comment(post, commentRequestDto, user);
        commentRepository.saveAndFlush(save);
        return ResponseEntity.ok(new ResponseDto("댓글 작성성공", HttpStatus.OK.value()));

    }

    @Transactional
    public ResponseEntity<?> updateComment(Long postid,Long commentid, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.") //IllegalArgumentException: 적합하지 않거나(illegal) 적절하지 못한(inappropriate) 인자를 메소드에 넘겨주었을 때 발생
        );
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("없는 댓글입니다.") //IllegalArgumentException: 적합하지 않거나(illegal) 적절하지 못한(inappropriate) 인자를 메소드에 넘겨주었을 때 발생
        );
        if (comment.getUser().getUsername().equals(user.getUsername())) {
            comment.update(commentRequestDto);
            return ResponseEntity.ok(new ResponseDto("댓글 수정 성공", HttpStatus.OK.value()));
        } else {
            return ResponseEntity.ok(new ResponseDto("자기 댓글만 수정가능합니다", HttpStatus.OK.value()));
        }
    }
    @Transactional
    public ResponseEntity<?> deleteComment(Long postid,Long commentid, User user) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.") //IllegalArgumentException: 적합하지 않거나(illegal) 적절하지 못한(inappropriate) 인자를 메소드에 넘겨주었을 때 발생
        );
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("없는 댓글입니다.") //IllegalArgumentException: 적합하지 않거나(illegal) 적절하지 못한(inappropriate) 인자를 메소드에 넘겨주었을 때 발생
        );
        if (comment.getUser().getUsername().equals(user.getUsername())) {
            commentRepository.deleteById(commentid);
            return ResponseEntity.ok(new ResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        } else {
            return ResponseEntity.ok(new ResponseDto("자기댓글만 삭제 가능합니다", HttpStatus.OK.value()));
        }
    }
}
