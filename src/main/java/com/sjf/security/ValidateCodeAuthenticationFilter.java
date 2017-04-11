package com.sjf.security;

import com.sjf.dto.MyJson;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by SJF on 2017/2/18.
 */
public class ValidateCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String VERIFYCODE = "verifyCode";

    private String servletPath;

    public ValidateCodeAuthenticationFilter(String servletPath) {
        super(servletPath);
        this.servletPath = servletPath;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if ("POST".equalsIgnoreCase(request.getMethod()) && servletPath.equals(request.getServletPath())) {
            String verifyCode = request.getParameter("verifyCode");
            String real_verifyCode = (String) request.getSession().getAttribute(VERIFYCODE);
            //request.getSession().removeAttribute(VERIFYCODE);

            if (StringUtils.isEmpty(verifyCode) || !StringUtils.equalsIgnoreCase(verifyCode, real_verifyCode)) {
                MyJson myJson = new MyJson();
                myJson.setCode(-1);
                myJson.setMessage("验证码错误");
                myJson.setData(null);
                JSONObject json = new JSONObject(myJson);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(json.toString());
                //unsuccessfulAuthentication(request, response, new InsufficientAuthenticationException("输入的验证码不正确"));
                return;
            }

            //String rememberMe = request.getParameter("rememberMe");
            //if ("on".equalsIgnoreCase(rememberMe)) {
            //    String username = request.getParameter("username");
            //    String password = request.getParameter("password");
            //    char[] array = (username + "^" + password).toCharArray();
            //    for(int i = 0; i < array.length; i++) {
            //        array[i]=(char)(array[i] ^ 19680625);
            //    }
            //    Cookie remember_me = new Cookie("remember-me",new String(array));
            //    remember_me.setMaxAge(1200);
            //    remember_me.setPath("/");
            //    response.addCookie(remember_me);
            //}
        }
        chain.doFilter(req, res);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        return null;
    }
}