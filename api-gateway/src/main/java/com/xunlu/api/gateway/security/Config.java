package com.xunlu.api.gateway.security;

import com.xunlu.api.gateway.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 配置Spring Security.
 * 网关服务使用Spring Security只完成认证方式中token的认证，及鉴权.
 * @author liweibo
 */
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenService tokenService;

    /**
     * redisTemplate, key为string, value为json
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous().and()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .logout().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .anyRequest().permitAll();// 网关负责鉴权

        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter("/user-service/token");
        http.addFilterAfter(tokenAuthenticationFilter, LogoutFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        TokenAuthenticationProvider tokenAuthenticationProvider = new TokenAuthenticationProvider(tokenService);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
