package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineWriteService {
    final private TimelineRepository timelineRepository;

    public void delieveryToTimeline(Long postId, List<Long> toMemberIds){
        var timelines = toMemberIds.stream()
                .map((memberId) -> Timeline.builder().postId(postId).memberId(memberId)
                        .build()).toList();
        timelineRepository.save(timelines);
    }
}
