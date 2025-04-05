package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
 * CHECK POINT
 * 역할과 구현을 분리하였는가? OK
 * 다형성도 확용하고 인터페이스와 구현 객체를 분리하였는가? OK
 * OCP, DIP 같은 객체 지향 설계 원칙을 준수하였는가? NO
 *   > DIP(위반) : 주문 서비스 클라이언트 (OrderServiceImpl) 는 DiscountPolicy 인터페이스에 의존하여 DIP 를 준수한 거 같지만.
 *           추상 인터페이스인 DiscountPolicy 및 구현 클래스 FixDiscountPolicy, RateDiscountPolicy 에도 의존하고있다.
 *   > OCP(위반) : 현재 코드 기능을 확장해서 변경하면, 클라이언트 코드 (OrderServiceImpl) 에 영향을 준다.
 */

//    PLUS : [DIP 위반] | 구체화 객체 의존.
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

//    PLUS : @RequiredArgsConstructor
//      > final 이 붙은 필드를 모아서 생성자를 자동으로 만들어줌.
//      > 생성자를 따로 만들어줄 필요 없음.
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
//  REFACTOR : [DIP 준수] | 추상화 객체 의존.
//      >  OrderServiceImpl 은 생성자를 통해 어떤 구현 객체가 들어올지(주입될지) 할 수 없음.
//      >  OrderServiceImpl 의 생성자를 통해 어떤 구현 객체를 주압힐지는 AppConfig 에서 결정함.

//  FIXME : 필드 주입.
//    > Field injection is not recommended. | 사용을 권장하지않음.
//    > 코드는 간결하지만 외부에서 변경이 불가해서 테스트하기 힘듬.
//    > 외부에서 값을 변결하려면 setter 를 만들어서 사용해야함. (이렇게 되면, setter 주입하는게 나음)

//    @Autowired private  MemberRepository memberRepository;
//    @Autowired private  DiscountPolicy discountPolicy;

//  FIXME : 수정자 주입(setter 주입).
//    > setter 라 불리는 필드의 값을 변경하는 수정자 메서드를 통해 의존관계 주입.
//    > 선택, 변경 가능성이 있는 의존관계에 사용.
//    > 선택적으로 사용하기 위해서는 (required=false) 사용하면 됨.

//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

//  FIXME : 생성자 주입.
//    > 생성자를 통해서 의존관계 주입.
//    > 불변, 필수 의존관계에 사용.

/*
* 조회 대상 빈이 2개이상일 때 해결 방법
* @Autowired 필드 명 매칭
* @Qualifier(추가 구분자) -> @Qualifier 끼리 매칭 -> 빈 이름 매칭
* @Primary(우선순위 지정)
*/

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

//  테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }


//  FIXME : 일반 메서드 주입.
//    > 스프링 컨데이터가 관리하는 스프링 빈일 때, 일반 메서드를 통해서 주입 반을 수 있음.
//    > 일반적으로 잘 사용하진않음.

//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
}
