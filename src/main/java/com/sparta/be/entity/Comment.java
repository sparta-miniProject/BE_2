package com.sparta.be.entity;

import com.sparta.be.dto.CommentRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자를 만들어주는 역활 // access ? 알아봐야함
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // // id 값을 1씩 증가시켜주는 역활
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT") // null값을 허용하지않음 columnDeefinition
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩이랑 즉시로딩중에서 지연로딩으로 왠만하면 현업에서 지연로딩을 쓰는게 좋을것같아서 지연로딩으로함
    @JoinColumn(name = "post_id", nullable = false) // post_id를 가져와서 쓰겠다는 것같음
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(Post post, CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getContent();
        this.post = post;
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        content = commentRequestDto.getContent();
    }
}
