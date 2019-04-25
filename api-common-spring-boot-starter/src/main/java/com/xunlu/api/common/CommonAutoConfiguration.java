package com.xunlu.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.xunlu.api.common.codeenum.CodeEnumTypeHandler;
import com.xunlu.api.common.codeenum.StringToBaseCodeEnumConverterFactory;
import com.xunlu.api.common.restful.condition.SortConditionFormatter;
import com.xunlu.api.common.restful.exception.RestResponseEntityExceptionHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
            registry.addFormatter(new SortConditionFormatter());
        }
    }

    /**
     * 配置Jackson
     *
     * 注册时期日期统一格式的:jackson2HttpMessageConverter
     *      datetime: `yyyy-MM-dd HH-mm-ss`
     *      date: `yyyy-MM-dd HH-mm-ss`
     *      time: `HH-mm-ss`
     * @return
     */
    @Configuration
    @ConditionalOnClass(ObjectMapper.class)
    @AutoConfigureBefore(JacksonAutoConfiguration.class)
    @EnableConfigurationProperties(CommonProperties.class)
    public static class JacksonConfiguration {

        private final CommonProperties properties;

        public JacksonConfiguration(CommonProperties properties) {
            this.properties = properties;
        }

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
            return builder -> {
                DateTimeFormatter dateTimePattren = DateTimeFormatter.ofPattern(properties.getDateTimePattern());
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(properties.getDatePattern());
                DateTimeFormatter timePattern = DateTimeFormatter.ofPattern(properties.getTimePattern());

                builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimePattren));
                builder.serializerByType(LocalDate.class, new LocalDateSerializer(datePattern));
                builder.serializerByType(LocalTime.class, new LocalTimeSerializer(timePattern));

                builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimePattren));
                builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(datePattern));
                builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(timePattern));


                builder.simpleDateFormat(properties.getDateTimePattern());
            };
        }
    }
}
