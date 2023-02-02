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
    private Long likeCount;
    final private LocalDate createdDate;
    final private LocalDateTime createdAt;
    private Long version;

    @Builder
    public Post(Long id, Long memberId, String content, Long likeCount,
                LocalDate createdDate, LocalDateTime createdAt, Long version) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.content = content;
        this.likeCount = likeCount == null ? 0 : likeCount;
        this.createdDate = createdDate == null? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null? LocalDateTime.now() : createdAt;
        this.version = version == null ? 0 : version;
    }
    public void incrementLikeCount(){
        likeCount += 1;
    }
}
