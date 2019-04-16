package com.xunlu.api.user.security;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于[MobTech-Mob移动开发者平台]提供的验证码服务, SmsClient的实现.
 * MobTech官网:<a href="http://mob.com/product/sms">MobTech验证码服务</a>
 * @author liweibo
 */
public class MobSmsClient implements SmsClient {
    protected static final String VERIFY_URL = "https://webapi.sms.mob.com/sms/verify";
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 468;

    public static final Map<Integer, String> CODE2MESSAGE_MAP;

    static {
        Map<Integer, String> codemessagemap = new HashMap<>();
        codemessagemap.put(200, "验证成功");
        codemessagemap.put(405, "AppKey为空");
        codemessagemap.put(406, "AppKey无效");
        codemessagemap.put(456, "国家代码或手机号码为空");
        codemessagemap.put(457, "手机号码格式错误");
        codemessagemap.put(466, "请求校验的验证码为空");
        codemessagemap.put(467, "请求校验验证码频繁（5分钟内同一个appkey的同一个号码最多只能校验三次）");
        codemessagemap.put(468, "验证码错误");
        codemessagemap.put(474, "没有打开服务端验证开关");
        CODE2MESSAGE_MAP = Collections.unmodifiableMap(codemessagemap);
    }

    private String appKey;

    private RestTemplate restTemplate;

    /**
     * 创建一个MobSmsClient
     * @param appKey 调用mob api使用的appKey
     */
    public MobSmsClient(String appKey) {
        assert appKey != null;
        this.appKey = appKey;

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.restTemplate = restTemplateBuilder.additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
            //的响应头的Content-Type不是'application/json',导致restTemplate.postForObject转型失败,故添加此过滤器
            ClientHttpResponse response = execution.execute(request, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            return response;
        }).build();
    }

    /**
     * 设置用于请求api的RestTemplate
     * @param restTemplate
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 获取用于请求的RestTemplate
     * @return
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * 验证手机验证码是否正确.
     * 参见:<a href="http://wiki.mob.com/%e9%aa%8c%e8%af%81%e7%a0%81%e6%9c%8d%e5%8a%a1%e5%99%a8%e6%a0%a1%e9%aa%8c%e6%8e%a5%e5%8f%a3/">验证码服务端校验接口</a>
     * @param zone 区号. 例如中国为 86
     * @param phone 手机号
     * @param code 验证
     * @return code 验证通过返回true, 否则false
     * @throws SmsClientException
     */
    @Override
    public boolean verify(String zone, String phone, String code) throws SmsClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appkey", appKey);
        params.add("phone", phone);
        params.add("zone", zone);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> postEntity = new HttpEntity<>(params, headers);
        JsonNode a = restTemplate.postForObject(VERIFY_URL, postEntity, JsonNode.class);
        int status = a.get("status").asInt();
        if (status == SUCCESS_CODE) {
            return true;
        }
        if (status == FAIL_CODE) {
            return false;
        }
        throw new SmsClientException(CODE2MESSAGE_MAP.get(status));
    }
}
