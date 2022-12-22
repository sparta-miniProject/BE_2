package com.sparta.be.repository;

import com.sparta.be.entity.Like;
import com.sparta.be.entity.Post;
import com.sparta.be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
