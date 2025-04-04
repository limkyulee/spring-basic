package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

//  REFACTOR : MemoryMemberRepository 에 의존하지않도록 로직 수정.
//      > MemberServiceImpl 는 MemberRepository 생성자를 통해 어떤 구현 객체가 들어올지 알 수 없음.
//      > MemberServiceImpl 의 생성자를 통해 어떤 구현 객체를 주압힐지는 AppConfig 에서 결정함.
//      > 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중함.

//  PLUS : @Autowired
//    > 생성자에서 여러 의존관계를 자동으로 한번에 주입받을 수 있음.
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

//  테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
