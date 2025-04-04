package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//  PLUS : @ComponentScan
//    > @Component 애노테이션이 붙은 클래스를 스캔해서 스프인 빈으로 등록함.
//    > basePackages | 탐색할 패키지의 시작 위치를 지정함.
//                     이 패키지를 포함하여 하위 패키지를 모두 탐색.
//                   | 지정하지않으면, 해당 애노테이션이 붙은 패키지의 시작위치가 됨.
//      [권장 사용 법] | 프로젝트 시작 루트에 메인 설정 정보를 두고 @ComponentScan 을 붙임.
//                   | 참고로 스프링부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를
//                     루트 위지에 두는 것이 관례 (안에 @ComponentScan 포함하기 떄문)
//    > excludeFilters | ComponentScan 의 대상으로 설정하지 않을 조건 설정.

/*
@Component 말고도 아래 애노테이션은 스프링 빈 등록 대상이 된다.

@Controller : 스프링 MVC 컨트롤러에서 사용.
@Service : 스프링 비즈니스 로직에서 사용.
@Repository : 스프링 데이터 접근 계층에서 사용.
@Configuration : 스프링 설정 정보에서 사용.
*/

@Configuration
@ComponentScan(
//        basePackages = "hello.core.member",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
