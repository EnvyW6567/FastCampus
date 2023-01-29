package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MemberTest {
    @Test
    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    public void testChangeName(){
        var member = MemberFixtureFactory.create();
        var expected = "pnu";

        member.changeNickname(expected);
        Assertions.assertEquals(expected, member.getNickname());

    }

    @Test
    @DisplayName("회원은 닉네임은 10자를 초과할 수 없다.")
    public void testNickNameMaxLength(){
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "pnsakajsjasdfg";

        Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->{member.changeNickname(overMaxLengthName);}
        );
    }

}
