package com.sjf.service;

import com.sjf.entity.SysUser;

/**
 * Created by SJF on 2017/2/19.
 */
public interface TestService {

    boolean isUser(String username, String password);

    SysUser getUserByUsername(String username);
}
