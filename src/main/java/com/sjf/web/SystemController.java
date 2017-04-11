package com.sjf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SJF on 2017/2/17.
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/check")
    public String check() {
        return "only ROLE_ROOT can see this page";
    }
}
