package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import hello.core.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
//      Before Refactor : AppConfig appConfig = new AppConfig();
//      구현체가 지정된 memberServiceImpl 을 반환.
//      MemberService memberService = appConfig.memberService();

//      REFACTOR : Spring Container Version
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println("findMember.getName() : " + findMember.getName());
        System.out.println("memberA.getName() : " + memberA.getName());

    }
}
