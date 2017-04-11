package com.sjf.service.impl;

import com.sjf.dao.SysUserRepository;
import com.sjf.entity.SysUser;
import com.sjf.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by SJF on 2017/2/19.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private SysUserRepository userRepository;

    @Override
    public boolean isUser(String username, String password) {

        SysUser user = userRepository.findByUsername(username);
        String realPassword = user.getPassword();

        BCryptPasswordEncoder util = new BCryptPasswordEncoder();
        return util.matches(password, realPassword);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
