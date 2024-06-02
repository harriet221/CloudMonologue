# Question_domain
작업: 24.05.27 - 06.02
문서화: 24.06.01, 02

## API 명세 (Controller)

| **기능**      | **HTTP** | **URI** | **메서드 명** | **반환값**              |
|-------------|----------|---------|-----------|----------------------|
| 오늘의 질문 보여주기 | GET      | /today      |    public String showTodayQuestion     | today.html           |
| 해당 질문 답변 거부 | GET      | /banned/{id}  |   public String banQuestion      | /today로 redirect     |

## Service - Repository - mapper.xml
- findQuestionById
  - SqlSessionTemplate 클래스 selectOne 메서드 사용
  - QuestionDto 객체 반환
- pickQuestion
  - 파라미터 없는 경우, 빈 List 만들어서 그것을 인자로 오버로드 메서드 호출
  - Random 클래스 사용, 거부된 질문 목록 피해 새로운 랜덤 질문 id 생성
  - Long 타입 questionId 반환
- countTotalQuestions
  - SqlSessionTemplate 클래스 selectOne 메서드 사용
  - int 타입 정수 반환, 질문 총 개수
  - SQL문에서 count 함수 사용

## ETC
- Entity / Dto 분리와 에러 관련은 Member 쪽과 같음.
- question id 전달과 관련한 몇 개의 에러에 부딪힘.
  - 한 html 페이지에서, question.content는 잘 나오는데 question.id는 null 값이 들어오는 문제 발생.
  - DB에선 모든 question에 대해 id가 존재함 = DB 문제 아님.
  - question-mapper.xml에서 findById(selectOne)로 Dto 객체를 가져올 때, content만 가져오고 있었음을 발견.
  - id 추가하여 해결.
  - 같은 문제가 member bannedQuestions 발견되어 member-mapper.xml 또한 수정.
- bannedQuestions 관해, set 이후에 해당 내용이 DB에 반영되지 않는 문제 발견.
  - save 되어야 하는 것으로 판단, 그러나 기존 save 함수가 insert into를 실행하고 있어 멤버가 중복 저장됨.
  - update 함수를 새로 구현하는 것으로 해결.
  - 확장성을 고려해, 고유한 id를 제외하고 다른 필드를 모두 update 하는 것으로 구현함.