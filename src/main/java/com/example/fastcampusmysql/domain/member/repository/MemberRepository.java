package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    public Member save(Member member){
        /*
        id 유무에 따라 삽입, 갱신 결정
        return 값에 id 포함
         */
        return member;
    }
}
