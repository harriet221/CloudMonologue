# Monologue_domain
작업: 24.05.27 - 06.02
문서화: 24.06.01

## API 명세 (Controller)

| **기능**      | **HTTP** | **URI** | **메서드 명** | **반환값**          |
|-------------|----------|---------|-----------|------------------|
| 오늘의 질문 답변하기 | GET      | /{id}/answer      |    public String giveAnswer     | monologue.html   |
| 오늘의 질문 답변하기 | POST     | /{id}/answer  |   public String giveAnswer       | /main으로 redirect |

## Service - Repository - mapper.xml
- save
  - SqlSessionTemplate 클래스 insert into 메서드 사용

## ETC
- Entity / Dto 분리와 에러 관련은 Member 쪽과 같음
- question과 동일한 에러에 시달리는 중...