package io.ggammu.realspringbootjpa.service;

import com.sun.java.util.jar.pack.Package;
import io.ggammu.realspringbootjpa.domain.Member;
import io.ggammu.realspringbootjpa.repository.MemberRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("hwang");

        //when

        //then
    }

    @Test
    void 중복_회원_예외() throws Exception {
    }
}