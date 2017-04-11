package com.sjf.security;

import com.sjf.dao.SysUserRepository;
import com.sjf.dto.MyJson;
import com.sjf.entity.SysRole;
import com.sjf.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by SJF on 2017/2/17.
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private static final String VERIFYCODE = "verifyCode";

    @Autowired
    private SysUserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        session.setAttribute("username", request.getParameter("username"));

        String username = request.getParameter("username");
        Cookie usernameCookie = new Cookie("username", username);
        usernameCookie.setMaxAge(1200);
        usernameCookie.setPath("/");
        response.addCookie(usernameCookie);


        //SysUser sysUser = userRepository.findByUsername()
        MyJson myJson = new MyJson();
        myJson.setCode(1);
        myJson.setMessage("登陆成功");
        myJson.setData(new SysRole(2L, "TEST-JSON"));
        JSONObject json = new JSONObject(myJson);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json.toString());
    }
}
