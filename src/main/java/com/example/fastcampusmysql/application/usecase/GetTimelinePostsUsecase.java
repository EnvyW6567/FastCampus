package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTimelinePostsUsecase {

    final private PostReadService postReadService;
    final private FollowReadService followReadService;
    final private TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest){
        var followingMemberIds = followReadService.getFollwings(memberId).stream().map(Follow::getToMemberId).toList();
        return postReadService.getPostsByMemberId(followingMemberIds, cursorRequest);
    }
    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest){
        var pageTimelines = timelineReadService.getTimeline(memberId, cursorRequest);
        var postIds = timelineReadService.
                getTimeline(memberId, cursorRequest).body()
                .stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPosts(postIds);
        return new PageCursor<>(pageTimelines.nextCursorRequest(), posts);
    }
}
