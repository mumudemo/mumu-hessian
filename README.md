# mumu-hessian rpc服务和序列化
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumudemo/mumu-hessian/blob/master/LICENSE) 
[![Build Status](https://travis-ci.org/mumudemo/mumu-hessian.svg?branch=master)](https://travis-ci.org/mumudemo/mumu-hessian)
[![codecov](https://codecov.io/gh/mumudemo/mumu-hessian/branch/master/graph/badge.svg)](https://codecov.io/gh/mumudemo/mumu-hessian)

***mumu-hessian是一个研究hessian远程服务（rpc）、序列化的学习项目，通过这个项目了解hessian的基本使用方法和架构思想。通过hessian可以将服务接口暴露出来供客户端调用，其间通过二进制协议（binary-rpc）来传输数据。而且hessian自带了性能非常优异的序列化组件，通过自带的序列化组件大大减少了网络传输的数据量。***


## hessian集成
### pom依赖
  创建maven项目，在pom.xml配置文件中加入hessian依赖。
  ```
   <dependency>
        <groupId>com.caucho</groupId>
        <artifactId>hessian</artifactId>
        <version>4.0.38</version>
</dependency>
   ```
### 服务端  
##### 配置web.xml
   ```
   <servlet>
        <servlet-name>hessian</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
</servlet>

    <servlet-mapping>
        <servlet-name>hessian</servlet-name>
        <url-pattern>/hessian/*</url-pattern>
    </servlet-mapping>
   ```
##### 配置hessian服务
   ```
   <bean id="userService" class="com.lovecws.mumu.hessian.server.UserServiceImpl"/>
    <bean name="/user" class="org.springframework.remoting.caucho.HessianServiceExporter">
        <property name="service" ref="userService"/>
        <property name="serviceInterface" value="com.lovecws.mumu.hessian.api.UserService"/>
    </bean>
   ```
### 客户端
##### spring方式调用
```
 <bean id="userServiceClient" class="org.springframework.remoting.caucho.HessianProxyFactoryBean">
        <property name="serviceUrl" value="http://localhost:8080/hessian/user"/>
        <property name="serviceInterface" value="com.lovecws.mumu.hessian.api.UserService"/>
        <property name="overloadEnabled" value="true" />
    </bean>
```
##### 手动方式调用
```
public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8080/hessian/user";
        HessianProxyFactory factory = new HessianProxyFactory();
        UserService userService = (UserService) factory.create(UserService.class, url);
        String add = userService.add();
        System.out.println(add);
}
```
## 序列化
#### 对象序列化
```
/**
 * hession序列化
 *
 * @param obj         对象
 * @param hessianType hession序列化方式 HESSIAN、HESSIAN2、HessianSerializer
 * @return
 * @throws IOException
*/
public static byte[] serialize(Object obj, HessianType hessianType) throws IOException {
    if (obj == null) throw new NullPointerException();
    if (hessianType == null) hessianType = HessianType.HESSIAN;        ByteArrayOutputStream os = null;
    AbstractHessianOutput output = null;
    try {
    os = new ByteArrayOutputStream();
        switch (hessianType) {
            case HESSIAN:
                output = new HessianOutput(os);
                break;
            case HESSIAN2:
                output = new Hessian2Output(os);
                break;
            case HessianSerializer:
                output = new HessianSerializerOutput(os);
                break;
            default:
                output = new HessianOutput(os);
                break;
        }
        output.writeObject(obj);
        output.close();
        return os.toByteArray();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return null;
}
```
#### 反序列化
```
/**
 * hessian反序列化
 *
 * @param bytes       hessian序列化字节数组
 * @param hessianType hessian序列化类型 HESSIAN、HESSIAN2、HessianSerializer
 * @return
 */
public static Object deserialize(byte[] bytes, HessianType hessianType) {
    if (bytes == null) throw new NullPointerException();
    if (hessianType == null) hessianType = HessianType.HESSIAN;
    ByteArrayInputStream is = null;
    AbstractHessianInput input = null;
    try {
        is = new ByteArrayInputStream(bytes);
        switch (hessianType) {
            case HESSIAN:
                input = new HessianInput(is);
                break;
            case HESSIAN2:
                input = new Hessian2Input(is);
                break;
            case HessianSerializer:
                input = new HessianSerializerInput(is);
                break;
            default:
                input = new HessianInput(is);
                break;
        }
        return input.readObject();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            is.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return null;    
}

public enum HessianType {
    HESSIAN,
    HESSIAN2,
    HessianSerializer
}
```
## 相关阅读
[Hessian原理分析](https://www.cnblogs.com/happyday56/p/4268249.html)

## 联系方式
**以上观点纯属个人看法，如有不同，欢迎指正。  
email:<babymm@aliyun.com>  
github:[https://github.com/babymm](https://github.com/babymm)**