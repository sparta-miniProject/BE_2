package com.sparta.be.service;

import com.sparta.be.dto.LikeResponseDto;
import com.sparta.be.dto.ResponseDto;
import com.sparta.be.entity.Like;
import com.sparta.be.entity.Post;
import com.sparta.be.entity.User;
import com.sparta.be.repository.LikeRepository;
import com.sparta.be.repository.PostRepository;
import com.sparta.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> likePost(final Long postId, final String userId) {
        Post post = checkPost(postId);
        User user = checkUser(userId);

        // .orElse null이면 new Like()를 만듦
        Like postLikes = likeRepository.findByUserAndPost(user, post).orElse(new Like());

        if (postLikes.getId() != null) {

            likeRepository.deleteById(postLikes.getId());

            return ResponseEntity.ok(new LikeResponseDto(false, "좋아요 취소", HttpStatus.OK.value()));
        } else {
            Like like = new Like(user, post);
            likeRepository.save(like);
            return ResponseEntity.ok(new LikeResponseDto(true,"좋아요", HttpStatus.OK.value()));
        }
    }

    private Post checkPost(final Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다.")
        );
    }

    private User checkUser(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }

}
