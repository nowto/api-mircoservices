package com.xunlu.api.user;

import com.xunlu.api.user.infrastructure.StringToBaseCodeEnumConverterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringBoot 启动类
 * @author liweibo
 */
@SpringBootApplication
public class UserMicroserviceApplication {
	@EnableWebMvc
	@Configuration
	public static class WebConfig implements WebMvcConfigurer {
		@Override
		public void addFormatters(FormatterRegistry registry) {
			registry.addConverterFactory(new StringToBaseCodeEnumConverterFactory<>());
		}
	}

	@Bean
	public RestTemplate basicRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	/**
	 * 有的api的响应头没有设Content-Type,导致restTemplate.postForObject转型失败,需使用该RestTemplate
	 * @param restTemplateBuilder
	 * @return
	 */
	@Bean
	public RestTemplate jsonRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
					ClientHttpResponse response = execution.execute(request, body);
					response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
					return response;
				}).build();
	}

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

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

}
