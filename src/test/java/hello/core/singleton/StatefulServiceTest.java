package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;


//  FIXME : 싱글톤 방식의 주의점
//     > 특정 클라이언트가 값을 변경할 수 있는 필드가 존재해서는 안됨.
//     > 스프링 빈의 필드에 공유값을 설정하면 정말 큰 장애가 발생할 수 있음.
//     > 때문에, 무상태(stateless) 로 설계하여야 함.
class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService.class);
        StatefulService s1 = ac.getBean(StatefulService.class);
        StatefulService s2 = ac.getBean(StatefulService.class);

//      ThreadA : A 사용자 10_000 원 주문.
        s1.order("member1", 10_000);
//      ThreadB : B 사용자 20_000 원 주문.
        s2.order("member2", 20_000);

//      ThreadA 사용자 주문 금액 조회.
        int price = s1.getPrice();
        System.out.println("price : " + price);

        Assertions.assertThat(s1.getPrice()).isEqualTo(20_000); // 이렇게 되면 안됨.
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}