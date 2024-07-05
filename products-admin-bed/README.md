# Backend: Products Admin
# Marcus Andrade <marcusandrade816@gmail.com>

This project handle all operations executed by project products-admin-fed

* Spring Boot 3
* Java 17

## Run application
```
./gradlew bootRun
```
The Spring Boot Server run at port `8080`.

Each API request requires a JWT token, given by `/productsadmin/auth/signin`

## User to generate a token and access resources 
```
User: admin
Password: admin


User: user
Password: user
```
## Postman
Please refer to the postman collection at `/postman` folder

## Run tests
```
./gradlew test
```

## Build executable JAR
```
./gradlew bootJar
```
