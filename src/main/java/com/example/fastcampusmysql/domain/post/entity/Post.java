package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {
    final private Long id;
    final private Long memberId;
    final private String content;
    final private LocalDate createdDate;
    final private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String content, LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.content = content;
        this.createdDate = createdDate == null? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null? LocalDateTime.now() : createdAt;
    }
}
