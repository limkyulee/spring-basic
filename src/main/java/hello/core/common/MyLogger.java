package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

// PLUS : proxyMode = ScopedProxyMode.TARGET_CLASS
//    > 싱글톤 빈을 사용하듯이 편리하게 request scope 를 사용할 수 있음.
//    > 실제 요청이 들어올 때까지 가짜 프록시로 버티고있는 것과 비슷함.
//    > 마치 provider 를 주입하듯이 가짜 프록시 클래스를 만들어두고
//      HTTP request 와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있음. (CGLIB 의 사용)
//    > 하지만 무분별한 사용은 권장하지않음.

/*
* 동작 정리
* 1. CGLIB 라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입.
* 2. 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직을 포함하고있음.
* 3. 실제 request scope 와는 관계가 없지만,
*    원본 클래스를 상속 받아서 만들어졌기 떄문에 이 객체를 사용하는 클라이언트 입장에서는 원본인지 아닌지 구분할 수 없음.
*/

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] " + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString(); // 진심 죽어도 절대로 겹치지않는 값.
        System.out.println("[" + uuid + "] requset scrope bean create : " + this );
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] requset scrope bean close : " + this );
    }
}
