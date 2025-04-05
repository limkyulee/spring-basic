package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * 웹 스코프
 * 웹 환경에서만 동작하는 스코프
 * 프로토타입과 다르게 스프링이 해당 스코프의 종료 시점까지 관리함.
 * 따라서, 종료 메서드가 호출됨.
 * request : http 요청 하나가 들어오고 나갈 떄까지 유지되는 스코프,
 *           http 요청마다 별도의 빈 인스턴스가 생성되고 관리됨.
 * session : http session 과 동일한 생명주기를 가지는 스코프
 * application : 서블릿 컨텍스트와 동일한 생명주기를 가지는 스코프
 * websocket : 웹 소켓과 동일한 생명주기를 가지는 스코프
 */

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
