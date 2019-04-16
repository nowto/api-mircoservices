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
                .authorizeRequests()// 网关负责鉴权
                    .antMatchers(
                            "/act/**",
                            "/article/share",
                            "/article/collect/option",
                            "/article/collect/query",
                            "/article/collect/status",
                            "/chart/plans",
                            "/chart/plan/del",
                            "/feature/**",
                            "/user/feedback/**",
                            "/hotel/**",
                            "/logout",
                            "/merchant/addMerchantEntry",
                            "/plan/delPlan",
                            "/plan/renamePlan",
                            "/plan/timeMeasure",
                            "/plan/geneby",
                            "/plan/smallTraffic/list",
                            "/plan/smallTraffic",
                            "/plan/train/list",
                            "/plan/hotel/list",
                            "/plan/hotel/list/filter",
                            "/plan/hotel",
                            "/plan/collect/add",
                            "/plan/collect/cancel",
                            "/plan/share",
                            "/plan/listFlight",
                            "/plan/changeFlight",
                            "/plan/changeTrain",
                            "/plan/listHotel",
                            "/plan/changeHotel",
                            "/plan/save",
                            "/plan/be56e057f20f883e",
                            "/plan/adjust/sight",
                            "/plan/quote",
                            "/plan/listRoute/filter",
                            "/plan/listssNew",
                            "/plan/listss/filter",
                            "/plan/listss",
                            "/plan/ssList/filter",
                            "/plan/routeList",
                            "/plan/routeList/filter",
                            "/plan/listssByAdd",
                            "/route/collect/add",
                            "/route/collect/cancel",
                            "/route/share",
                            "/scenic_spots/editPlanSS",
                            "/scenic_spots/collect/option",
                            "/scenic_spots/share",
                            "/system/*",
                            "/topic/collect/add",
                            "/topic/collect/cancel",
                            "/topic/share",
                            "/user/plan/list",
                            "/user/collect/ss",
                            "/user/collect/plan",
                            "/user/collect/topic",
                            "/user/collect/travels",
                            "/user/collect/route",
                            "/user/userCenter",
                            "/user/prefer",
                            "/user/nickname/modify",
                            "/user/personsign/modify",
                            "/user/password/modify",
                            "/user/photo/uptoken"
                    )
                        .hasRole("USER")
                    .anyRequest().permitAll();//剩余的都是不需要登录也能访问的请求

        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter("/user-service/token");
        tokenAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        http.addFilterAfter(tokenAuthenticationFilter, LogoutFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        TokenAuthenticationProvider tokenAuthenticationProvider = new TokenAuthenticationProvider(tokenService);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
