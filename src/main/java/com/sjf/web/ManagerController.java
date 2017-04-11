package com.sjf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SJF on 2017/2/17.
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @RequestMapping("/pay")
    public String pay() {
        return "Pay!";
    }
}
