package com.lovecws.mumu.hessian.server.serialize;

import java.io.Serializable;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户信息
 * @date 2017-11-22 14:09
 */
public class UserInfo implements Serializable {
    private int userId;
    private String userName;
    private String password;

    public UserInfo(final int userId, final String userName, final String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
