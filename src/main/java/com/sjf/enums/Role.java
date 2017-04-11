package com.sjf.enums;

/**
 * Created by SJF on 2017/2/17.
 */
public enum Role {

    ROOT("ROLE_ROOT"),
    MANAGER("ROLE_MANAGER"),
    STAFF("ROLE_STAFF");

    String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
