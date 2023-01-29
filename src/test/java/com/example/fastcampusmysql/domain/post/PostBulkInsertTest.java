package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.domain.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {

        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2023, 1, 1)
        );
        var stopWatch = new StopWatch();
        stopWatch.start();
        var posts = IntStream.range(0, 300 * 10000)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간 " + stopWatch.getTotalTimeSeconds());

        var queryWatch = new StopWatch();
        queryWatch.start();
        postRepository.bulkInsert(posts);
        queryWatch.stop();
        System.out.println("DB INSERT 시간 " + queryWatch.getTotalTimeSeconds());
    }
}

