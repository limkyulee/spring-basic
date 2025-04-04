package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService;

//  PLUS : BeforeEach | Test 가 돌기 전에 미리 실행.
    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
//      given
        Member memberA = new Member(1L, "memberA", Grade.VIP);

//      when
        memberService.join(memberA);
        Member findMember = memberService.findMember(1L);

//      then (검증) | Assertions 사용.
        Assertions.assertThat(findMember).isEqualTo(memberA);
    }
}
