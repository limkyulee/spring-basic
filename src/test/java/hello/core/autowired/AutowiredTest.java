package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

//   FIXME : 옵션 처리.
//  @Autowired(required=false) | 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안된.
//  org.springframework.lang.@Nullable | 자동 주입할 대상이 없으면 null 이 입력됨.
//  Optional<> | 자동 주입할 대상이 없으면 Optional.empty 가 입력됨.
public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

//  IF : Member 는 스프링 빈이 아님.

    static class TestBean{

//      자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨. |
//      Autowired members must be defined in valid Spring bean (@Component|@Service|...)
        @Autowired(required = false)
        public void setNoBean1(Member member){
            System.out.println("setNoBean1 : " + member);
        }

//      null 호출
        @Autowired
        public void setNoBean2(@Nullable Member member){
            System.out.println("setNoBean2 : " + member);
        }

//      Optional.empty 호출
        @Autowired(required = false)
        public void setNoBean3(Optional<Member> member){
            System.out.println("setNoBean3 : " + member);
        }
    }
}
