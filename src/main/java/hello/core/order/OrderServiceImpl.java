package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemoryMemberRepository;

/*
 * CHECK POINT
 * 역할과 구현을 분리하였는가? OK
 * 다형성도 확용하고 인터페이스와 구현 객체를 분리하였는가? OK
 * OCP, DIP 같은 객체 지향 설계 원칙을 준수하였는가? NO
 *   > DIP(위반) : 주문 서비스 클라이언트 (OrderServiceImpl) 는 DiscountPolicy 인터페이스에 의존하여 DIP 를 준수한 거 같지만.
 *           추상 인터페이스인 DiscountPolicy 및 구현 클래스 FixDiscountPolicy, RateDiscountPolicy 에도 의존하고있다.
 *   > OCP(위반) : 현재 코드 기능을 확장해서 변경하면, 클라이언트 코드 (OrderServiceImpl) 에 영향을 준다.
 */

//    PLUS : DIP 규칙 미준수 | 구체화 객체 의존.
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

public class OrderServiceImpl implements OrderService {
//  REFACTOR : DIP 규칙 준수 | 추상화 객체 의존.
//      >  OrderServiceImpl 은 생성자를 통해 어떤 구현 객체가 들어올지(주입될지) 할 수 없음.
//      >  OrderServiceImpl 의 생성자를 통해 어떤 구현 객체를 주압힐지는 AppConfig 에서 결정함.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
