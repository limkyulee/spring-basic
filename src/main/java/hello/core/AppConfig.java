package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import hello.core.discount.RateDiscountPolicy;

/*
    FIXME : AppConfig [Dependency Injection (의존 관계 주입)]
        > 애플리케이션의 실제 동작에 필요한 구현 객체를 생성.
        > 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입(연결)함.
        > 해당 구성 영역에서 구체적인 구현체들의 연결을 담당함.
        > OrderApp 과 같은 client 코드의 변경을 필요로하지않음.
*/

@Configuration
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

//  Q : 이렇게 되면 각각 다른 두개의 MemoryMemberRepository 가 생성될텐데
//      싱글톤 패턴이 꺠지는 것이 아닌가?
//  @Bean memberService -> new MemoryMemberRepository()
//  @Bean orderService -> new MemoryMemberRepository()
//  A : @Configuration 을 사용하면 스프링 빈이 싱글톤이 되도록 보장해줌. (AppConfig@CGLIB)
//        > @Bean 이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환.
//        > 스프링 빈이 없으면 생성해서 스프링 빈을 등록하고 반환하는 코드가 동적으로 만들어짐.

//   Q : @Configuration 을 붙이지않으면?
//   A : 동작은 되는데 싱글톤 패턴이 깨짐. (즉, 스프링 컨테이너가 관리하지않음. 그냥 자바 코드 실행하는 격.)
//     | new MemoryMemberRepository() 반복 생성.

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

//  PLUS : 할인 정책 변경 시, 해당 코드만 변경하면 됨. [OCP 원칙 준수]
    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(
                memberRepository()
        );
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );

//        return null;
    }
}
