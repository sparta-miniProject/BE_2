package com.sparta.be.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {

    @Column(updatable = false)
    @CreatedDate // 생성시간
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate // 수정시간
    private LocalDateTime modifiedAt;
}
