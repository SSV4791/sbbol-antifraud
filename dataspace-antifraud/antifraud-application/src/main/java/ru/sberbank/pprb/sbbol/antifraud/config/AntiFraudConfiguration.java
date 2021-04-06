package ru.sberbank.pprb.sbbol.antifraud.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import sbp.sbt.sdk.DataspaceCorePacketClient;

@Configuration
public class AntiFraudConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

    @Bean
    public DataspaceCorePacketClient dataspaceCorePacketClient(@Value("${core.url}") String coreUrl) {
        return new DataspaceCorePacketClient(coreUrl);
    }

    @Bean
    public DataspaceCoreSearchClient dataspaceCoreSearchClient(@Value("${core.url}") String coreUrl) {
        return new DataspaceCoreSearchClient(coreUrl);
    }
}
