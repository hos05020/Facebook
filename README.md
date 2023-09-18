**프로젝트 개요**

- Facebook 스타일의 웹 애플리케이션 개발: 사용자 관리, 게시글 및 댓글 기능, 인증 및 인가 등의 핵심 기능 포함.

**주요 기능**

- **회원 관리**
    - 아이디 중복 검사
    - 회원 가입 및 로그인
    - 친구 목록 확인
- **게시글 관리**
    - 게시글 작성 및 조회
    - 게시글 좋아요 기능
    - 특정 게시글 확인 및 전체 목록 보기
- **댓글 기능**
    - 게시글에 댓글 작성 (알림 기능 포함)
    - 댓글 목록 확인
- **인증 및 인가**
    - 로그인 시 토큰 발급
    - API 접근 시 토큰 확인
    - 게시글 접근 권한 검사

**기술적 특징 및 경험**

- **인증 및 인가**
    - **`AccessDecisionManager`**를 활용하여 게시글 접근 권한 관리
    - JWT를 활용한 인증 및 인가 처리
- **비동기 처리**
    - Java 8 **`completefuture`**를 이용한 S3 사진 업로드
- **데이터베이스 처리**
    - 게시글 좋아요 여부 확인을 위한 **`Like`** 테이블과 **`Post`** 테이블의 JOIN 연산
- **이벤트 기반 알림 시스템**
    - 댓글 작성 시 이벤트 발생, **`EventBus`**와 Kafka를 통한 알림 전송 처리
- **서버 아키텍쳐**
    - API 서버와 PUSH 서버의 분리를 통해 MSA (Microservices Architecture) 체험

       

- Tech Stack
    - Back-End : Spring Boot, Spring Security(+ JWT), Spring Data JPA, QueryDSL, Kafka
    - DataBase : H2-Console
