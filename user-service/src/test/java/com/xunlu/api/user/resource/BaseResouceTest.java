package com.xunlu.api.user.resource;

import com.xunlu.api.common.CommonAutoConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureWebClient
@AutoConfigureDataRedis
@ImportAutoConfiguration(CommonAutoConfiguration.class)
@Import(ResourceTestConfig.class)
public abstract class BaseResouceTest {

}
