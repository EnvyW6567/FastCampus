package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostsUsecase {

    final private PostReadService postReadService;
    final private FollowReadService followReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest){
        var followingMemberIds = followReadService.getFollwings(memberId).stream().map(Follow::getToMemberId).toList();
        System.out.println(followingMemberIds);
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }
    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest){
        var followingMemberIds = followReadService.getFollwings(memberId).stream().map(Follow::getToMemberId).toList();
        System.out.println(followingMemberIds);
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }
}
