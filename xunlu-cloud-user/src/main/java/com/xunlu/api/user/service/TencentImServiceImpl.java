package com.xunlu.api.user.service;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.tls.tls_sigature.tls_sigature;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author liweibo
 */
@Service
public class TencentImServiceImpl implements TencentIMService {
    private static final int APP_ID = 1400015178;

    private static final String ADMIN_USER_IDENTIFIER = "xladmin";

    private static final String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n"+
            "MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQgh988WPFvFXssMGvXEHxX\n"+
            "hDCtNf5JbUgaU2OJORI/T9qhRANCAAQk61XYwH9ZhtQyF1JLNS0im/XJ0BiY5nyc\n"+
            "mFdPVC+wueqUciEw3el2RRl9pstGJgdFDOeQOfCmP4i9gJBBQgzA\n"+
            "-----END PRIVATE KEY-----";

    private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"+
            "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEJOtV2MB/WYbUMhdSSzUtIpv1ydAYmOZ8\n"+
            "nJhXT1QvsLnqlHIhMN3pdkUZfabLRiYHRQznkDnwpj+IvYCQQUIMwA==\n"+
            "-----END PUBLIC KEY-----";

    private RestTemplate restTemplate;

    public TencentImServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
                    //云通信api的响应头没有设Content-Type,导致restTemplate.postForObject转型失败,故添加此过滤器
                    ClientHttpResponse response = execution.execute(request, body);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return response;
                }).build();
    }

    @Override
    public String makeUserSig(String identifier) {
        tls_sigature.GenTLSSignatureResult userSig = tls_sigature.genSig(APP_ID, identifier, PRIVATE_KEY);
        return userSig.urlSig;
    }

    @Override
    public boolean checkUserSig(String userSig, String identifier) {
        try {
            tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature
                    .CheckTLSSignatureEx(userSig, APP_ID, identifier, PUBLIC_KEY);
            return checkResult.verifyResult;
        } catch (Exception e) {
            return false;
        }
    }

    private static final int IDENTIFIER_MAX_LENGTH = 40;
    @Override
    public String makeIdentifier(String prefix, Integer id) {
        String digest = DigestUtils.md5DigestAsHex((prefix+":"+id).getBytes(StandardCharsets.UTF_8));
        if(digest.length() > IDENTIFIER_MAX_LENGTH) {
            digest = digest.substring(0, IDENTIFIER_MAX_LENGTH);
        }
        return digest;
    }

    @Override
    public boolean accountImport(String identifier, String nick, String faceUrl) {
        ApiResponseBody responseBody = restTemplate.postForObject(
                buildUri("https://console.tim.qq.com/v4/im_open_login_svc/account_import"),
                new ImportRequestBody(identifier, nick, faceUrl),
                ApiResponseBody.class);
        return ApiResponseBody.isSuccess(responseBody);
    }



    @Override
    public boolean multiAccountImport(List<String> accounts) {
        ApiResponseBody responseBody = restTemplate.postForObject(
                buildUri("https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import"),
                new MultiImportRequestBody(accounts),
                ApiResponseBody.class);
        return ApiResponseBody.isSuccess(responseBody);
    }

    private URI buildUri(String path) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(path);
        builder.queryParam("usersig", makeUserSig(ADMIN_USER_IDENTIFIER));
        builder.queryParam("identifier", ADMIN_USER_IDENTIFIER);
        builder.queryParam("sdkappid", APP_ID);
        builder.queryParam("random", nextRandomUnsignedInt());
        // 固定值 json
        builder.queryParam("contenttype", "json");
        return builder.build(true).toUri();
    }

    private int nextRandomUnsignedInt() {
        Random random = new Random(System.currentTimeMillis());
        return Math.abs(random.nextInt());
    }

    private static class ImportRequestBody {

        private String identifier;
        private String nick;
        private String faceUrl;

        public ImportRequestBody(String identifier, String nick, String faceUrl) {
            this.identifier = identifier;
            this.nick = nick;
            this.faceUrl = faceUrl;
        }

        @JsonGetter("Identifier")
        public String getIdentifier() {
            return identifier;
        }

        @JsonGetter("Nick")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getNick() {
            return nick;
        }

        @JsonGetter("FaceUrl")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getFaceUrl() {
            return faceUrl;
        }
    }

    private static class MultiImportRequestBody {
        private List<String> accounts;

        public MultiImportRequestBody(List<String> accounts) {
            this.accounts = accounts;
        }

        @JsonGetter("Accounts")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public List<String> getAccounts() {
            return accounts;
        }
    }

    private static class ApiResponseBody {
        public static final String ACTION_STATUS_SUCCESS = "OK";
        public static final int ERROR_CODE_SUCCESS = 0;

        private String actionStatus;
        private String errorInfo;
        private int errorCode;

        public ApiResponseBody() {
        }

        public static final boolean isSuccess(ApiResponseBody body) {
            return body != null && body.errorCode == ERROR_CODE_SUCCESS
                    && Objects.equals(body.actionStatus, ACTION_STATUS_SUCCESS);
        }


        @JsonSetter("ActionStatus")
        public void setActionStatus(String actionStatus) {
            this.actionStatus = actionStatus;
        }

        @JsonSetter("ErrorInfo")
        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }

        @JsonSetter("ErrorCode")
        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
