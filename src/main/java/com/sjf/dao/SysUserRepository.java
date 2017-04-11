package com.sjf.dao;

import com.sjf.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SJF on 2017/2/17.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);
}
