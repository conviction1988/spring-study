# 스프링 스터디

## 개발 스택
* SpringBoot 2.7.3
* Java 11
* Gradle 7.5
* jUnit5
* h2

## API 명세 문서
* [Swagger](http://localhost:8080/swagger-ui/index.html)
* [Rest Docs](http://localhost:8080/docs/index.html) 


## 설계 내용

### 패키지 구성
1. com.spring.study.domain : 도메인을 담당합니다.
   1. api : 버전별로 컨트롤러를 관리합니다.
      1. v1 : 컨트롤러 클래스가 있습니다.
   2. domain : 도메인 엔티티 클래스가 있습니다. 
   3. dto : Request, Response 클래스가 있습니다.
   4. service : 서비스 클래스가 있습니다.
   5. repository : 리파지토리 클래스가 있습니다.
2. com.spring.study.global : 프로젝트 전체에서 사용되는 부분을 담당합니다.
   1. common : 공통으로 사용되는 응답 클래스, 엔티티 코드 정의, 엔티티 컬럼 타입, 엔티티 공통 필드 리소스가 구성되어있습니다.
   2. config : i18n, jwt, security, swagger 설정들로 구성되어있습니다.
   3. exception : 공통으로 에러 처리하기 위한 Exception 클래스가 있습니다.


### 도메인 구성
1. User : 회원 도메인
2. Product : 상품 도메인
3. Order : 주문 도메인  


### API 구성

* 공통 응답 클래스 사용   
  다건/단건 응답을 공통 응답 클래스를 상속받아서 구성 했습니다.
  모든 API에서 동일한 API 응답 포맷을 유지 하기 위해서 입니다.
  단건/다건/에러 응답 클래스를 별도로 구성을 한것은 Controller 레벨에서 응답 형태를 구분을 용의하기 하기 위해서입니다.


* 공통 예외처리 구성
  API 내의 모든 에러는 처리는 한곳에서 처리하도록 했습니다.

1. 회원가입 API [POST] /api/v1/auth/sign-up
2. 로그인 API : [POST] /api/v1/auth/sign-in
3. 로그아웃 API : [POST] /api/v1/auth/sign-out
4. 상품 조회 API : [GET] /api/v1/products/{id}
5. 상품 주문 API : [POST] /api/v1/products/{id}/orders
6. 회원 주문 내역 API : [GET] /api/v1/my-page/orders


### 테스트 코드
* com.spring.study
* Rest Docs 

1. Auth관련 통합테스트 : AuthIntegrationTests.java
2. Product관련 통합테스트 : ProductIntegrationTests.java
3. MyPage관련 통합테스트 : MyPageIntegrationTests.java

