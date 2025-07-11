### application-dev.yml

```yaml
server:
  port:
spring:
  application:
    name: raon-back
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url:
    username:
    password:
jwt:
  secret:
  access-token-expiration-time:
  refresh-token-expiration-time:
```

### 개발 DB 서버 구축

```bash
cd db
docker image build -t <이미지이름> .
docker container run --name <컨테이너이름> -e MYSQL_ROOT_PASSWORD=<DB비밀번호> -p 3306:3306 -v <볼륨이름>:/var/lib/mysql -d <이미지이름>
```

### 개발 DB 컨테이너 정지 및 시작

```bash
docker container stop <컨테이너이름>
docker container start <컨테이너이름>
```
