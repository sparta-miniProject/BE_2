package com.sparta.be.dto;

import com.sparta.be.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String category;
    private MultipartFile file;
    private String imageUrl;

    public PostRequestDto(String title, String content, String category, String imageUrl) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageUrl = imageUrl;
    }

}
