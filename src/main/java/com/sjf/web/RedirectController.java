package com.sjf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by SJF on 2017/2/17.
 */
@Controller
@ApiIgnore
public class RedirectController {

    @RequestMapping("/swagger")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }
}
