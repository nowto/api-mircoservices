package com.xunlu.api.user.security;

import com.xunlu.api.user.service.SmsService;
import com.xunlu.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 配置Spring Security
 * @author liweibo
 */
@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_FAILURE_FORWARD_URL = "/authentication_failure";
    public static final String AUTHENTICATION_SUCCESS_FORWARD_URL = "/authentication_success";

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AntPathRequestMatcher tokenRequestMatcher = new AntPathRequestMatcher("/token", HttpMethod.POST.name());




        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .logout().disable()
                .httpBasic().disable()
                .authorizeRequests()
                    .anyRequest().permitAll();

        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter(tokenRequestMatcher);
        smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(new ForwardAuthenticationFailureHandler(AUTHENTICATION_FAILURE_FORWARD_URL));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(new ForwardAuthenticationSuccessHandler(AUTHENTICATION_SUCCESS_FORWARD_URL));

        http.addFilterAfter(smsCodeAuthenticationFilter, LogoutFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider(userService, smsService);
        auth.authenticationProvider(smsCodeAuthenticationProvider);
    }
}
