package com.xunlu.api.user.resource;

import com.xunlu.api.common.restful.condition.Page;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.request.RequestParametersSnippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@TestConfiguration
public class ResourceTestConfig {
    @Bean
    public RestDocumentationResultHandler restDocumentation() {
        return MockMvcRestDocumentation.document(
                "{ClassName}/{methodName}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
    }

    @Bean
    public RequestParametersSnippet offsetPageParams() {
        return requestParameters(
                parameterWithName("limit").description("需要多少条(可选)").optional(),
                parameterWithName("offset").description("从第几条开始(0为初始值)(可选)").optional());
    }

    @Bean
    public ResponseHeadersSnippet totalCountHeader() {
        return responseHeaders(
                headerWithName(Page.TOTAL_COUNT_RESPONSE_HEADER_NAME).description("总条数数"));
    }
}
