package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/*
* 빈 생명주기 콜백 지원
* 1. 인터페이스 (implements InitializingBean, DisposableBean) - 거의 사용하지않음.
* 2. 설정 정보에 초기화 메서드, 종료 메서드 지정
* 3. @PostConstruct, @preDestroy 애노테이션 지원
*   - 자바 표준.
*   - 해당 애노테이션을 사용하는 것을 권장.
*   - 코드를 고찰 수 없는 외부라이브러리르 초기화하거나 종료해야할 때는 사용 못함.
* */
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

//  서비스 시작 시, 호출
    public void connect(){
        System.out.println("connecting to " + url);
    }

    public void call(String message){
        System.out.println("calling " + message);
    }

//  서비스 종료 시, 호출
    public void disconnect(){
        System.out.println("disconnecting from " + url);
    }

//  FIXME : 빈 등록 초기화, 소멸 메서드 지정
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("init");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }

//  FIXME : 인터페이스 InitializingBean, DisposableBean
//  의존 관게 주입이 끝나면 실행
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        connect();
//        call("조기화 연결 메세지");
//    }
//
//  빈이 종료될 때 실행
//    @Override
//    public void destroy() throws Exception {
//        disconnect();
//    }
}
