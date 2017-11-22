package com.lovecws.mumu.hessian.server;

import com.lovecws.mumu.hessian.api.UserService;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/6/006.
 */
public class UserServiceImpl implements UserService {

    @Override
    public String add() {
        System.out.println("add........................");
        return new Date().toLocaleString();
    }

    @Override
    public String update() {
        System.out.println("update........................");
        return new Date().toLocaleString();
    }
}
