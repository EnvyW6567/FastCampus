package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreatePostLikeUsecase;
import com.example.fastcampusmysql.application.usecase.CreatePostUsecase;
import com.example.fastcampusmysql.application.usecase.GetTimelinePostsUsecase;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    final private PostReadService postReadService;
    final private GetTimelinePostsUsecase getTimelinePostsUsecase;
    final private CreatePostUsecase createPostUsecase;
    final private PostWriteService postWriteService;
    final private CreatePostLikeUsecase createPostLikeUsecase;

    @PostMapping("")
    public Long create(PostCommand command){
        return createPostUsecase.execute(command);
    }

    @PostMapping("/{postId}/like/v1")
    public void postLikeV1(@PathVariable Long postId){ // using version column (Optimistic Lock)
        postWriteService.likePostWithOptimisticLock(postId);
    }

    @PostMapping("/{postId}/like/v2")
    public void likePostWithOptimisticLockV2(@PathVariable Long postId, @RequestParam Long memberId){ // using postLike entity to manage post likes
        createPostLikeUsecase.execute(postId, memberId);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> create(@RequestParam DailyPostCountRequest request){
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
            Pageable pageable
    ){
        return postReadService.getPostsByMemberId(memberId, pageable);
    }
    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPosts(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ){
        return postReadService.getPostsByMemberId(memberId, cursorRequest);
    }
    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimelines(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ){
        return getTimelinePostsUsecase.executeByTimeline(memberId, cursorRequest);
    }
}
