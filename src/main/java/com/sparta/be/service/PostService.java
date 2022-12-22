package com.sparta.be.service;

import com.sparta.be.dto.*;

import com.sparta.be.entity.*;
import com.sparta.be.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;


    @Transactional
    public ResponseEntity<?> savePost(PostRequestDto postRequestDto, User user) throws IOException {
        String imageUrl = null;

        //첨부파일 존재할 때
        if (!postRequestDto.getFile().isEmpty()) {
            imageUrl = awsS3Service.uploadFile(postRequestDto.getFile());
        }

        System.out.println("postRequestDto.getTitle() : " + postRequestDto.getTitle());
        System.out.println("postRequestDto.getContent() : " + postRequestDto.getContent());
        System.out.println("postRequestDto.getCategory() : " + postRequestDto.getCategory());

        log.info("postRequestDto.getTitle() : {} ", postRequestDto.getTitle());
        log.info("postRequestDto.getContent() : {} ", postRequestDto.getContent());
        log.info("postRequestDto.getCategory() : {} ", postRequestDto.getCategory());

        PostRequestDto requestDto = new PostRequestDto(postRequestDto.getTitle(), postRequestDto.getContent(), postRequestDto.getCategory(), imageUrl);

        postRepository.saveAndFlush(new Post(requestDto, user));

        return ResponseEntity.ok(new ResponseDto("게시글 작성 완료", HttpStatus.OK.value()));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> Top() {
        TopListResponseDto topResponseDto = new TopListResponseDto();

        String category = "drink";
        List<Post> drinkList = postRepository.findTop5ByCategoryOrderByLikesDesc(category);

        category = "recipe";
        List<Post> recipeList = postRepository.findTop5ByCategoryOrderByLikesDesc(category);

        category = "food";
        List<Post> foodList = postRepository.findTop5ByCategoryOrderByLikesDesc(category);

        //작성일 기준 내림차순
        for (Post post : drinkList) {
            topResponseDto.addDrinkList(new PostResponseDto(post));
        }
        for (Post post : recipeList) {
            topResponseDto.addRecipeList(new PostResponseDto(post));
        }
        for (Post post : foodList) {
            topResponseDto.addFoodList(new PostResponseDto(post));
        }


        return ResponseEntity.ok(topResponseDto);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> posts() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();

        //작성일 기준 내림차순
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        for (Post post : postList) {
            postListResponseDto.addPostList(new PostResponseDto(post));
        }
        return ResponseEntity.ok(postListResponseDto);
    }

    @Transactional
    public ResponseEntity<?> postDetailed(Long id) {
        //조회되는 게시글 없을 때
        if (!postRepository.existsById(id)) {
            return ResponseEntity.ok(new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        Post post = postRepository.findById(id).orElseThrow();

        //게시글 상세조회시 조회수 +1
        post.updateViews(post.getViews()+1);

        return ResponseEntity.ok(new PostResponseDto(post));
    }

    @Transactional
    public ResponseEntity<?> postDelete(Long id, User user) {
        //조회되는 게시글 없을 때
        if (!postRepository.existsById(id)) {
            return ResponseEntity.ok(new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        //user 와 작성자 일치 여부 확인
        if (!postRepository.existsByIdAndUser(id, user)) {
            return ResponseEntity.ok(new ResponseDto("글 작성자만 삭제 가능합니다.", HttpStatus.BAD_REQUEST.value()));
        }

        Post post = postRepository.findById(id).orElseThrow();

        //첨부파일 있을 경우 파일 삭제 처리
        if (post.getImageUrl() != null) {
            String fileName = post.getImageUrl().split(".com/")[1];
            awsS3Service.deleteFile(fileName);
        }

        //게시글 삭제
        postRepository.delete(post);

        return ResponseEntity.ok(new ResponseDto("게시글 삭제 완료", HttpStatus.OK.value()));
    }

    @Transactional
    public ResponseEntity<?> postUpdate(Long id, PostRequestDto postRequestDto, User user) {
        //user 와 작성자 일치 여부 확인
        if (!postRepository.existsByIdAndUser(id, user)) {
            return ResponseEntity.ok(new ResponseDto("글 작성자만 수정 가능합니다.", HttpStatus.BAD_REQUEST.value()));
        }

        Post post = postRepository.findById(id).orElseThrow();

        String imageUrl = null;

        //기존 글에 첨부파일 존재시
        if (post.getImageUrl() != null) {
            if (!postRequestDto.getFile().isEmpty()) {
                //첨부파일 수정시 기존 첨부파일 삭제
                String fileName = post.getImageUrl().split(".com/")[1];
                awsS3Service.deleteFile(fileName);

                //새로운 파일로 업로드
                imageUrl = awsS3Service.uploadFile(postRequestDto.getFile());

            } else {
                //첨부파일 수정 안함
                imageUrl = post.getImageUrl();
            }
        } else {
            //첨부파일 없는 글에
            if (!postRequestDto.getFile().isEmpty()) {
                //첨부파일 적용
                imageUrl = awsS3Service.uploadFile(postRequestDto.getFile());

            }
        }

        post.updateViews(postRequestDto, imageUrl);

        return ResponseEntity.ok(new ResponseDto("게시글 수정 완료", HttpStatus.OK.value()));
    }
}
