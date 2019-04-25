package com.xunlu.api.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunlu.api.common.codeenum.CodeEnumTypeHandler;
import com.xunlu.api.common.codeenum.StringToBaseCodeEnumConverterFactory;
import com.xunlu.api.common.restful.condition.SortConditionFormatter;
import com.xunlu.api.common.restful.exception.RestResponseEntityExceptionHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import java.text.SimpleDateFormat;
import java.util.List;

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
     * 注册时期日期统一格式的:jackson2HttpMessageConverter
     *      datetime: `yyyy-MM-dd HH-mm-ss`
     *      date: `yyyy-MM-dd HH-mm-ss`
     *      time: `HH-mm-ss`
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
            registry.addFormatter(new SortConditionFormatter());
        }

        /**
         * 定义时间格式转换器
         * @return
         */
        @Bean
        public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            converter.setObjectMapper(mapper);
            return converter;
        }

        /**
         * 添加转换器
         * @param converters
         */
        @Override
        public void configureMessageConverters(
                List<HttpMessageConverter<?>> converters) {
            //将我们定义的时间格式转换器添加到转换器列表中,
            // 这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
            converters.add(jackson2HttpMessageConverter());
        }
    }
}
