package com.sjf.config;

import com.sjf.enums.Role;
import com.sjf.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Created by SJF on 2017/2/17.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    private RememberMeSuccessfulHandlerImpl rememberMeSuccessfulHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/verifycode").permitAll()
                    .antMatchers("/confirm").permitAll()
                    .antMatchers("/home").authenticated()
                    .antMatchers("/manager/**").hasAnyRole(Role.ROOT.name(), Role.MANAGER.name())
                    .antMatchers("/system/**").hasRole(Role.ROOT.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ValidateCodeAuthenticationFilter("/login"), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/login")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .permitAll()
                .and()
                .rememberMe()
                    .rememberMeParameter("rememberMe")
                    //.authenticationSuccessHandler(rememberMeSuccessfulHandler)
                    .tokenValiditySeconds(1200)
                    .key("123")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .permitAll()
                .and()
                .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .sessionRegistry(sessionRegistry()).expiredUrl("/verifycode");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //// Register HttpSessionEventPublisher
    //@Bean
    //public static ServletListenerRegistrationBean httpSessionEventPublisher() {
    //    return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    //}

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

}
