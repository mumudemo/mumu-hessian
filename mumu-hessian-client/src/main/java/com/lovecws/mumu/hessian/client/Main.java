package com.lovecws.mumu.hessian.client;

import com.lovecws.mumu.hessian.api.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-hessian-client.xml");
            UserService userService = (UserService) context.getBean("userServiceClient");
            userService.add();
            userService.update();
            context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
