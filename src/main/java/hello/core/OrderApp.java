package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import hello.core.member.MemberServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
//      구현체가 지정된 memberServiceImpl 을 반환.
        MemberService memberService = appConfig.memberService();
//      구현체가 지정된 orderServiceImpl 을 반환.
        OrderService orderService = appConfig.orderService();

//      회원 생성
        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(memberA);

        Order order = orderService.createOrder(memberId, "itemA", 10_000);
        System.out.println("order : " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());

    }
}
