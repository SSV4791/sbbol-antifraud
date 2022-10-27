package ru.sberbank.pprb.sbbol.antifraud.common;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClientWithReporting;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.sberbank.pprb.sbbol.antifraud.AntiFraudRunner;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;

import java.util.Collections;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AntiFraudRunner.class}
)
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(initializers = {HibernatePluginCleanerInitializer.class})
@Import(TestReplicationConfiguration.class)
public abstract class AbstractIntegrationTest {

    protected static final String HOST = "http://localhost:";

    @LocalServerPort
    protected int port;

    protected static RequestId saveOrUpdateData(JsonRpcRestClientWithReporting createRpcClient, Operation record) throws Throwable {
        return createRpcClient.invoke(
                "saveOrUpdateData",
                Collections.singletonMap("dataparams", record),
                RequestId.class
        );
    }

    protected static AnalyzeResponse sendData(JsonRpcRestClientWithReporting searchRpcClient, SendToAnalyzeRequest request) throws Throwable {
        return searchRpcClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                AnalyzeResponse.class
        );
    }

}
