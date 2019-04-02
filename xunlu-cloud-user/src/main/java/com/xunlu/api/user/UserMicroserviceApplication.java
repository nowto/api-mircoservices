package com.xunlu.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

/**
 * @author liweibo
 */
@SpringBootApplication
public class UserMicroserviceApplication {
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

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

}
