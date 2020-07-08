# scg-demo 

## 说明
 scg-demo 是基于spring-cloud-gateway v2.0.2，且集成TSF服务注册发现功能的一个简单网关应用DEMO。


## 物料准备
### 物料列表
- provider-demo
- scg-demo

## 服务部署
### provider-demo
1. 编译打包
```
cd ./tsf-demo-simple/provider-demo
mvn clean complie install
```
2. 启动应用
```
cd ./tsf-demo-simple/provider-demo/target
java -jar provider-demo-xxx.jar
```
- `xxx` 版本号, 按需改动 

### scg-demo

1. 编译打包
```
cd ./tsf-demo-simple/scg-demo
mvn clean complie install
```
2. 启动应用
```
cd ./tsf-demo-simple/scg-demo/target
java -jar scg-demo-xxx.jar
```
- `xxx` 版本号, 按需改动 
## 功能测试 

provider-demo 中提供了多个API接口, 可通过scg进行代理访问。以/echo/{param}接口为例进行说明。

### API访问

可通过curl命令或者浏览器发送请命令。

```
curl -i -X GET http://<网关IP>:<网关PORT>/provider-demo/echo/123
```
> <网关IP>:<网关port> 自行替换

- 预期返回
```
request param: 2, response from echo-provider-default-nam
```





 