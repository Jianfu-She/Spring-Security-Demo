package com.sjf.security;

import com.sjf.dto.MyJson;
import com.sjf.entity.SysRole;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by SJF on 2017/2/17.
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        MyJson myJson = new MyJson();
        myJson.setCode(0);
        myJson.setMessage("登陆失败");
        myJson.setData(null);
        JSONObject json = new JSONObject(myJson);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json.toString());
    }
}
