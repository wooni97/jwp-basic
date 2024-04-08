#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.

1. 서블릿 컨테이너는 웹 애플리케이션의 상태를 관리하는 ServletContext를 생성한다.
   ServletContext 객체는 웹 애플리케이션이 실행되면서 애플리케이션 전체의 공통 자원이나 정보를 미리 바인딩해 서블릿들이 공유한다.
2. ServletContext가 초기화되면 컨텍스트의 초기화 이벤트가 발생한다.
3. 등록된 ServletContextListener의 콜백 메소드가 호출된다. 이 코드에서는 ContextListener의 contextInitialized 메소드가 호출된다. 이 작업이 가능한 이유는 ContextLoadListener가 ServletContextListener의 인터페이스를 구현하고 @WebListener 어노테이션이 설정되어 있기 때문이다. 해당 어노테이션이 있으면 서블릿 컨테이너를 시작하는 과정에서 contextInitialized 메소드를 호출해 초기화 작업을 진행한다.
4. jwp.sql 파일에서 SQL 문을 실행해 데이터베이스를 초기화 한다.
5. 서블릿 컨테이너는 클라이언트로부터 최초 요청시 DispatcherServlet을 생성한다. 이에 대한 설정은 @WebServlet의 loadOnStartup 속성으로 설정할 수 있다. 이 문제에서는 속성이 설정되어 있기 때문에 서블릿 켄테이너가 시작하는 시점에 인스턴스르 ㄹ생성한다.
6. DispatcherServlet의 init 메서드를 호출해 초기화 작업으 ㄹ진행한다.
7. init() 메소드 안에 있는 RequestMappping 객체를 생성한다.
8. RequestMapping 인스턴스의 initMapping() 메소드를 호출한다.


#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
