package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostWriteService {

    final private PostRepository postRepository;

    public Long create(PostCommand command){
        var post = Post
                .builder()
                .memberId(command.memberId())
                .content(command.content())
                .build();

        return postRepository.save(post).getId();
    }

//    @Transactional
//    public void likePost(Long postId){
//        var post = postRepository.findById(postId, true).orElseThrow();
//        post.incrementLikeCount();
//        postRepository.save(post);
//    }
    /*
    낙관적 락 -> Transactional 어노테이션 불필요
    즉, 시스템에 의한 락이 아닌 쿼리 실패시에 Throw 해주는 방식...
     */
    public void likePostWithOptimisticLock(Long postId){
        var post = postRepository.findById(postId, true).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }
}
