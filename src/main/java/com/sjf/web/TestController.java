package com.sjf.web;

import com.sjf.entity.SysUser;
import com.sjf.service.TestService;
import com.sjf.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by SJF on 2017/2/18.
 */
@Controller
public class TestController {

    private static final String VERIFYCODE = "verifyCode";

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("username");
        if (name != null) {
            return "已登录用户" + name;
            //return "index";
        }

        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        if (cookies != null && cookies.length > 0) {
            String username = null;
            for (Cookie cookie : cookies) {
                if ("remember-me".equals(cookie.getName())) {
                    flag = true;
                }
                if ("username".equals(cookie.getName())) {
                    SysUser user = testService.getUserByUsername(cookie.getValue());
                    username = user.getUsername();
                }
            }
            if (flag) {
                HttpSession session2 = request.getSession();
                session2.setAttribute("username", username);
                return "记住的用户" + username;
                //return "index3";
            }
        }
        return "未登录的用户";
        //return "index2";
    }

    @RequestMapping("/verifycode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 存入会话session
        HttpSession session = request.getSession(true);
        session.removeAttribute(VERIFYCODE);
        session.setAttribute(VERIFYCODE, verifyCode);
        // 生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public String confirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(request.getParameter("username"));
        return request.getParameter("username");
    }
}
