package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostLikeRepository;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService{
    final private PostRepository postRepository;
    final private PostLikeRepository postLikeRepository;

    public Post getPosts(Long postId){
        return postRepository.findById(postId, false).orElseThrow();
    }
    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request){
        return postRepository.groupByCreatedDate(request);

    }
    public Page<PostDto> getPostsByMemberId(Long memberId, Pageable pageRequest){
        return postRepository.findAllByMemberId(memberId, pageRequest).map(this::toDto);
    }

    public PageCursor<Post> getPostsByMemberId(Long memberId, CursorRequest cursorRequest){
        var posts = findAllByMemberId(memberId, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPostsByMemberId(List<Long> memberIds, CursorRequest cursorRequest){
        var posts = findAllByMemberId(memberIds, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPostsByPostIds(List<Long> postIds, CursorRequest cursorRequest){
        var posts = findAllByMemberId(postIds, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> findAllByMemberId(Long memberId, CursorRequest cursorRequest){
        if (cursorRequest.hasKey()){
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
    }

    public List<Post> findAllByMemberId(List<Long> memberIds, CursorRequest cursorRequest){
        if (cursorRequest.hasKey()){
            return postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        }
        return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
    }

    public List<Post> getPosts(List<Long> ids){
        return postRepository.findAllByInId(ids);
    }

    public PostDto toDto(Post post){
        return new PostDto(
                post.getId(),
                post.getContent(),
                post.getCreatedAt(),
                postLikeRepository.getCount(post.getId())
        );
    }
    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
