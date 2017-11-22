package com.lovecws.mumu.hessian.client;

import com.caucho.hessian.client.HessianProxyFactory;
import com.lovecws.mumu.hessian.api.UserService;

import java.net.MalformedURLException;

/**
 * 客户端调用（会依赖服务接口）
 *
 * @author leizhimin 2009-8-14 12:29:33
 */
public class Client {
    public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8080/hessian/user";
        HessianProxyFactory factory = new HessianProxyFactory();
        UserService userService = (UserService) factory.create(UserService.class, url);
        String add = userService.add();
        System.out.println(add);
    }
}