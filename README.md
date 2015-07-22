# Spring Boot

start_spring_io.sh fortunes actuator,web

server.port=8080

# add fortunes from https://goo.gl/0lVrBw
# static random

# Config Server

start_spring_io.sh configserver actuator,web,cloud-server

server.port=8888


https://github.com/spencergibb/oscon-config-repo

add exclusion to s-c-config-server
<exclusions>
  <exclusion>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-rsa</artifactId>
  </exclusion>
</exclusions>


in bootstrap.properties
encrypt.key=foobar
spring.application.name=configserver

http :8888/foo/default
http --form :8888/encrypt password=
http --form :8888/decrypt $ENCRYPTED=

in config-repo:application.yml set encrypted.value: '{cipher}$ENCRYPTED'

add config client to fortunes
in bootstrap.properties
  spring.application.name=fortunes

show profiles and labels

# Eureka

start_spring_io.sh eureka actuator,web,cloud-eureka-server

server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

add spring-cloud-starter-eureka and @EnableDiscoveryClient to fortunes and configserver
run more than 1

# Ribbon

start_spring_io.sh fortunesui actuator,web,cloud-ribbon,cloud-config,cloud-eureka

server.port=9000

in bootstrap.properties
  spring.application.name=fortunesui

add @EnableDiscoveryClient
add /choose that show LoadBalancerClient in action

add RestOperations to /

# Feign

add spring-cloud-starter-feign to fortunesUi
add @EnableFeignClients
add @FeignClient public interface...

# Hystrix

add spring-cloud-starter-hystrix to fortunesUi
add @SpringCloudApplication
create FortuneService with @HystrixCommand with fallback

# Hystrix Dashboard

start_spring_io.sh hystrixdashboard actuator,web,cloud-hystrix-dashboard,cloud-eureka,cloud-config

server.port=7979

in bootstrap.properties
  spring.application.name=hystrixdashboard

stop fortune service and refresh to open circuit

http --form POST :9000/env hystrix.command.fortune.circuitBreaker.forceOpen=true
http --form POST :9000/refresh

# Zuul

start_spring_io.sh zuul actuator,web,cloud-config,cloud-eureka,cloud-zuul

server.port=9080

in bootstrap.properties
  spring.application.name=zuul

add @EnableDiscoveryClient @EnableZuulProxy to zuul app

management.port=9081
zuul.routes.fortunesui=/**

# Actuator

Add fortunesui.prefix property class and add to ui

http --form POST :9000/env fortunesui.prefix="Awesomeness: "

Change something in oscon-config-repo/fortunesui.yml

http POST :9000/refresh
http --form POST :9000/env fortunesui.prefix='really awesome: '

# Spring Cloud Bus

add spring-cloud-starter-bus-amqp to fortunes and fortunesui

http --form POST :9000/bus/env info.url=http://spencer.gibb.us

Change something in oscon-config-repo/application.yml

http POST :9000/bus/refresh

# Sidecar

start_spring_io.sh sidecar actuator,web,cloud-config,cloud-eureka

add spring-cloud-netflix-sidecar dependency

get https://goo.gl/XUTHgG and place in src/main/python

server.port=5678
sidecar.port=5680
sidecar.health-uri=http://localhost:${sidecar.port}/health

Run with
  --spring.application.name=pyfortunes

http://localhost:8761/eureka/apps to show correct port and homepage

add /py to fortunesui

# Security

start_spring_io.sh authserver actuator,web,security,cloud-config,cloud-eureka

server.port=9999
security.user.password=password
server.contextPath=/uaa

in bootstrap.properties
  spring.application.name=authserver

add spring-security-oauth2 to pom.xml

from https://github.com/dsyer/spring-security-angular/tree/master/oauth2-vanilla

Update AuthserverApp https://goo.gl/qoFs5L

visit
http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=acme&redirect_uri=http://example.com

get code from browser

http --form --auth acme:acmesecret POST :9999/uaa/oauth/token grant_type=authorization_code client_id=acme redirect_uri=http://example.com code=7bx7ci

export TOKEN= access_token from response

in fortunes add spring-security-oauth2 & spring-cloud-starter-security to pom.xml

add @EnableOAuth2Resource to FortunesApp

add spring.oauth2.resource.userInfoUri: http://localhost:9999/uaa/user

http :8080 should be 401
http :8080 Authorization:"Bearer $TOKEN"
http :9999/uaa/user Authorization:"Bearer $TOKEN"

in zuul add spring-boot-starter-security, spring-security-oauth2 & spring-cloud-starter-security to pom.xml

add https://goo.gl/29Qs56 to zuul application.yml

in fortunesui add spring-cloud-starter-security,spring-security-oauth2

add @EnableOAuth2Resource to FortunesuiApp

```
@Autowired
@LoadBalanced
private OAuth2RestOperations rest;
```

add https://goo.gl/29Qs56 to fortunesui application.yml

# Turbine

TODO

# Turbine AMQP

TODO
