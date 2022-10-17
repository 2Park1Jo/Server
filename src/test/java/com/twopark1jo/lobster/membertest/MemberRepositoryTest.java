package com.twopark1jo.lobster.membertest;

import com.twopark1jo.lobster.member.Member;
import com.twopark1jo.lobster.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testInsertMember(){
        //Member mem = memberRepository.save(new Member("bbb", "박지민"));
        System.out.println("memberRepository = " + memberRepository.getMember("bbb"));
        Assertions.assertThat(memberRepository.getMember("bbb"));

    }

}
