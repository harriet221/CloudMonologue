# Question_domain
작업: 24.05.27 - 06.02
문서화: 24.06.01

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
- Entity / Dto 분리와 에러 관련은 Member 쪽과 같음
- question id 전달과 관련한 몇 개의 에러에 부딪힘...