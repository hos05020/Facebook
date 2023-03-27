# 웹 Facebook  프로젝트 (2023.02 ~ 2023.03)
- 프로젝트 설명 

  실제 Facebook과 유사하게, 사용자와 친구를 맺고, 친구의 게시글을 확인하고 댓글을 달 수 있는 웹 애플리케이션
  
* 기술적 경험


    * AccessDecisionManager를 통한 해당 Authentication 이 특정한 Object에 접근할 때 필요한 ConfigAttributes 를 만족하는지 확인
    
      * 로그인 유저와 확인하고자 하는 게시글의 작성자가 친구 관계인지 파악하고, 친구 관계가 아닐 경우 API 접근 불가
      
    * JWT를 통한 인증 및 인가
    
      * 로그인시 AuthenticationProvider에서 인증 후 ID, 이름, 이메일 및 접근 권한을 암호화 후 암호화된 정보가 담긴 토큰을 리턴 -> 토큰을 통해 API에 접근시, JwtAuthenticationTokenFilter에서 인가 처리
      
    * 회원 가입시 사진 업로드를 실패해도 사진 이외 정보를 통해 회원 가입 성공
    
      * java 8 completefuture를 활용해 S3에 사진 업로드를 비동기 처리
      
    * 로그인 유저가 해당 게시글에 대해 좋아요를 눌렀는지 파악 가능
    
      * Post 테이블에서 Like 테이블(유저 정보와 유가 좋아요 누른 게시글 정보가 담긴 테이블) 을 Left JOIN 처리한 이후 반환하여, Post 객체에서 로그인 유저의 게시글 좋아요 여부 파악 가능(Post 테이블에는 좋아요를 눌렀는지에 대한 칼럼 존재X)
      
      
      
      
      
* Tech Stack

  * Spring Boot, H2-Console, Spring Data JPA, QueryDSL
