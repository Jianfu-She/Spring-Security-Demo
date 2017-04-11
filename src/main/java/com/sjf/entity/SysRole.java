package com.sjf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SJF on 2017/2/17.
 */
@Entity
@Table(name = "t_role")
public class SysRole {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public SysRole() {
        super();
    }

    public SysRole(String name) {
        this.name = name;
    }

    public SysRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
