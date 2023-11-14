# Pet Store 宠物商店
### 将官方的pet store用 SpringBoot项目复刻了一遍
Reference参考地址:

https://petstore.swagger.io/

https://github.com/swagger-api/swagger-petstore


### 相关内容版本：

| 内容                   | 版本     |
|----------------------|--------|
| spring-boot          | 2.7.17 |
| springdoc-openapi-ui | 1.7.0  |
| OAS                  | 3.0    |
| JDK                  | 1.8    |
| Maven                | 3.6.3  |


### 快速启动
```
#打包
mvn clean package; cd target;
#启动
nohup java -jar pet-store.jar >> pet-store.log &
#停止
kill -9 $(ps aux | grep 'pet-store.jar' | awk '{print $2}') >/dev/null 2>&1
```
http://localhost:13000/swagger-ui/index.html
http://localhost:13000/v3/api-docs
