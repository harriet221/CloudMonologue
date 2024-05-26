# Member_domain
작업: 24.05.22 - 25
문서화: 24.05.26

## API 명세 (Controller)

| **기능**   | **HTTP** | **URI** | **메서드 명** | **반환값**              |
|----------|----------|---------|-----------|----------------------|
| 메인홈      | GET      | /main   |    public String showMain     | main.html            |
| 회원가입 (폼) | GET      | /sign   |   public String signup      | signup.html (회원가입 폼) |
| 회원가입     | POST     | /sign  |    public String signup     | /login으로 redirect    |
| 로그인 (폼)  | GET      | /login  |  public String login()       | login.html   (로그인 폼) |
| 로그인      | POST     | /login  |     public String login    | /main으로 redirect     |
| 로그아웃     | GET      | /logout |   public String logout      | /으로 redirect         |

## Service - Repository - mapper.xml
- save
  - SqlSessionTemplate 클래스 insert 메서드 사용
  - DB에 회원 정보 저장
- findByUserId
  - SqlSessionTemplate 클래스 selectOne 메서드 사용
  - MemberDto 객체 반환

## ETC
- Entity 별도로 사용하지 않고 Dto 객체 활용하고 있음.
  - Entity와 DTO 간의 변환 코드 없이 구조를 단순하게 유지하려는 목적.
  - 프로젝트 초반이라 데이터베이스 스키마가 자주 변경될 것을 염두.
  - 다만, 유지 보수 및 테스트 용이성 면세어 비즈니스 로직과 데이터 전송 로직은 분리하는 것이 좋음.
  - 차후 JPA로 리팩토링하면서 Entity 분리할 예정.
- 에러 핸들링: `model.addAttribute("error", msg);`
  - 에러 메시지를 모델에 추가하여 뷰에서 쉽게 접근할 수 있게 함.
  - 동일한 모델에 에러 메시지를 포함함으로써 뷰에서 데이터와 다양한 에러 메시지 동시에 처리 가능.
  - 그러나, 로직이 분산되고 일관성 유지 문제 등이 발생할 수 있어 따로 관리 필요.
  - 차후 프로젝트 내에서 활용하는 GlobalException 클래스 만들어 표준화 예정.
  - 그전에 BindingResult 객체 사용 방법을 한 번 거칠지 고민중.