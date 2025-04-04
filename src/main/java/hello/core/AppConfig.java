package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/*
    FIXME : AppConfig [Dependency Injection (의존 관계 주입)]
        > 애플리케이션의 실제 동작에 필요한 구현 객체를 생성.
        > 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입(연결)함.
        > 해당 구성 영역에서 구체적인 구현체들의 연결을 담당함.
        > OrderApp 과 같은 client 코드의 변경을 필요로하지않음.
*/

public class AppConfig {
/*
    BEFORE REFACTOR : 구현체 바로 주입(연결).
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
*/

/*
    REFACTOR : 구현체 변경 로직 분리.
      > new MemoryMemberRepository 중복 선언 제거.
      > 구현체 변경 시, memoryRepository 함수 내부만 변경하면 됨.
      > 역할과 구현 클래스의 분리로 한 눈에 확인 가능.
*/
    private static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

//  PLUS : 할인 정책 변경 시, 해당 코드만 변경하면 됨. [OCP 원칙 준수]
    private static DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }

    public MemberService memberService(){
        return new MemberServiceImpl(
                memberRepository()
        );
    }

    public OrderService orderService(){
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }
}
