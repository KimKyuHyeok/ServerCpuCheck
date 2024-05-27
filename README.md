## CPU 모니터링 시스템 구현
- springboot
- gradle
- java 17
- mariadb
- jpa
- swagger

  ### 주요 기능
  - OperatingSystemMXBean 을 이용하여 CPU 사용량을 가져온 후
  - ScheduledExecutorService 로 매 분마다 실행하여 JPA 를 통해 저장
  - 수집한 데이터를 지정한 구간의 분/시/일 단위 CPU 를 API 로 조회
  - 파라미터를 형식에 맞지 않게 입력하거나 데이터 조회 기간이 옳바르지 않을 경우 등 예외처리를 ExceptionHandler 를 사용
    - 분 단위 API - 최근 1주 데이터 제공
    - 시 단위 API - 최근 3달 데이터 제공
    - 일 단위 API - 최근 1년 데이터 제공


  ### API
  |Method|URL|설명|
  |:---|:---|:---|
  |GET|/api/v1/minute|분 단위 데이터 조회|
  |GET|/api/v1/hour|시 단위 데이터 조회|
  |GET|/api/v1/month|월 단위 데이터 조회|

  ### 사용 방법 예시
  - 원하는 기간과 단위를 선택하여 URL 을 통해 접속
    - 예시 : api/v1/month?start=2024-05-26T00:00&end=2024-10-27T00:00
  - /swagger-ui/index.html/ 를 통해 swagger 도 확인 가능
