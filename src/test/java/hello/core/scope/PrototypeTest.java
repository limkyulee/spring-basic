package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrototypeTest {

//  PLUS : 프로토타입 빈은 스프링 컨테이너에 요창할 떄 마다 새로 생성됨.
    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find PrototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find PrototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 : " + prototypeBean1);
        System.out.println("prototypeBean2 : " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean init method");
        }

//      PLUS : 프로토타입 빈은 스프링 컨테이너가 생성과 의존과계 주입 그리고 초기화 까지만 관여함.
//           : 따라서, 종료 메서드가 전혀 실행되지 않음.
        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean destroy method");
        }
    }
}
