# Monologue_domain
작업: 24.05.27 - 06.02
문서화: 24.06.01, 02

## API 명세 (Controller)

| **기능**      | **HTTP** | **URI** | **메서드 명** | **반환값**          |
|-------------|----------|---------|-----------|------------------|
| 오늘의 질문 답변하기 | GET      | /{id}/answer      |    public String giveAnswer     | monologue.html   |
| 오늘의 질문 답변하기 | POST     | /{id}/answer  |   public String giveAnswer       | /main으로 redirect |

## Service - Repository - mapper.xml
- save
  - SqlSessionTemplate 클래스 insert into 메서드 사용

## ETC
- Entity / Dto 분리와 에러 관련은 Member 쪽과 같음.
- content 저장에 문제가 있었음. (DB에 null로 저장됨)
  - 알아보니, 컨트롤러의 메서드가 받는 파라미터 이름와 textarea 태그의 name 값을 통일하지 않아서 발생.
  - content로 통일하여 해결함.
- 각 Dto id 컬럼의 데이터 타입 문제
  - MySQL에는 long 타입이 없음.
  - member, question, monologue 모두 Long id는 bigint 사용중.
  - 차후 도메인이 추가된다면 같은 방식 사용할 예정.
- MonologueDto 관련 컬럼 데이터 타입
  - question에 대한 답변, 즉 monologue의 content는 최대 500 단어로 상정하고 text 타입으로 결정.
  - 또한, 이를 고려해 input text가 아니라 애초에 textarea로 받기로 결정함.