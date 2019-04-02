package com.xunlu.api.user.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MobSmsClientTest {
    private static final String TEST_APPKEY = "1bb07cf59c173";
    private static final String CORRECT_CODE = "1111";
    private static final String ERROR_CODE = "2222";
    private static final String PHONE = "17777777777";
    private static final String ZONE = "86";


    private RestTemplate restTemplateStub = new TestRestTemplate();
    private MobSmsClient mobSmsClient = new MobSmsClient(TEST_APPKEY);
    {
        mobSmsClient.setRestTemplate(restTemplateStub);
    }
    private ObjectMapper om = new ObjectMapper();

    @Test
    public void testVerify() throws SmsClient.SmsClientException {
        boolean ret = mobSmsClient.verify(ZONE, PHONE, CORRECT_CODE);
        Assert.assertTrue(ret);

        ret = mobSmsClient.verify(ZONE, PHONE, ERROR_CODE);
        Assert.assertFalse(ret);
    }

    // mock restTemplate.postForObject(VERIFY_URL, postEntity, JsonNode.class);
    public class TestRestTemplate extends RestTemplate {
        public TestRestTemplate() {
        }

        public TestRestTemplate(ClientHttpRequestFactory requestFactory) {
            super(requestFactory);
        }

        public TestRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
            super(messageConverters);
        }

        @Override
        public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
            if (MobSmsClient.VERIFY_URL.equals(url) && request instanceof HttpEntity && JsonNode.class.equals(responseType) && uriVariables.length == 0) {
                HttpEntity<MultiValueMap<String, String>> requestEntity = (HttpEntity<MultiValueMap<String, String>>) request;
                if (MediaType.APPLICATION_FORM_URLENCODED.equals(requestEntity.getHeaders().getContentType())) {
                    MultiValueMap<String, String> map = requestEntity.getBody();
                    if (map.get("appkey").contains(TEST_APPKEY)
                        && map.get("phone").contains(PHONE)
                        && map.get("zone").contains(ZONE)) {
                        if (map.get("code").contains(CORRECT_CODE)) {
                            try {
                                return (T) om.readTree("{\"status\":200}");
                            } catch (IOException e) {
                                throw new RestClientException("{\"status\":200}");
                            }
                        } else {
                            try {
                                return (T) om.readTree("{\"status\":468}");
                            } catch (IOException e) {
                                throw new RestClientException("{\"status\":468}");
                            }
                        }
                    }
                }

            }
            return super.postForObject(url, request, responseType, uriVariables);
        }
    }
}