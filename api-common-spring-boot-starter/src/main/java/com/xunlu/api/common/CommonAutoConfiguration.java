package com.xunlu.api.common;

import com.xunlu.api.common.codeenum.CodeEnumTypeHandler;
import com.xunlu.api.common.codeenum.StringToBaseCodeEnumConverterFactory;
import com.xunlu.api.common.restful.condition.SortFormatter;
import com.xunlu.api.common.restful.exception.RestResponseEntityExceptionHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;

/**
 * 自动配置类
 * @author liweibo
 */
@Configuration
public class CommonAutoConfiguration {
    /**
     * 向Mybatis注册TypeHandler：CodeEnumTypeHandler
     */
    @Configuration
    @ConditionalOnClass({MybatisAutoConfiguration.class, org.apache.ibatis.session.SqlSession.class})
    @AutoConfigureBefore(MybatisAutoConfiguration.class)
    public static class MybatisConfiguration {

        @Bean
        public ConfigurationCustomizer codeEnumTypeHandlerCustomizer() {
            return new CodeEnumTypeHandlerCustomizer();
        }

        public static class CodeEnumTypeHandlerCustomizer implements ConfigurationCustomizer{
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.getTypeHandlerRegistry().register(CodeEnumTypeHandler.class);
            }
        }
    }


    /**
     * Spring MVC自动注册.
     * 注册Converter：{@link StringToBaseCodeEnumConverterFactory},
     * 注册ExceptionHandler： {@link RestResponseEntityExceptionHandler}
     */
    @Configuration
    @ConditionalOnWebApplication
    @ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class, FormatterRegistry.class, WebMvcAutoConfiguration.class})
    @AutoConfigureBefore(WebMvcAutoConfiguration.class)
    public static class MvcConfiguration  implements WebMvcConfigurer {
        @Bean
        public RestResponseEntityExceptionHandler restExceptionHandler() {
            return new RestResponseEntityExceptionHandler();
        }

        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverterFactory(new StringToBaseCodeEnumConverterFactory<>());
            registry.addFormatter(new SortFormatter());
        }
    }
}
