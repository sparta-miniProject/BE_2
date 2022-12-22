package com.sparta.be.repository;

import com.sparta.be.entity.Post;
import com.sparta.be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findTop5ByCategoryOrderByLikesDesc(String category);
    boolean existsByIdAndUser(Long id, User user);

    
}
