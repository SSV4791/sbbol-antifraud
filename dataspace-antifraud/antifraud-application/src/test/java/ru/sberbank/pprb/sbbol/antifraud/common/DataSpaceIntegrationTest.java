package ru.sberbank.pprb.sbbol.antifraud.common;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.sberbank.pprb.sbbol.antifraud.AntiFraudRunner;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import sbp.com.sbt.dataspace.core.local.runner.junit5.JUnit5DataSpaceCoreLocalRunnerExtension;

import java.util.Collections;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = { AntiFraudRunner.class }
)
@ExtendWith(JUnit5DataSpaceCoreLocalRunnerExtension.class)
@DirtiesContext
@ActiveProfiles("test")
public abstract class DataSpaceIntegrationTest {

    protected static final String URL_ROOT = "http://localhost:8080";

    @Autowired
    protected DataspaceCoreSearchClient searchClient;

    protected static RequestId saveOrUpdateData(JsonRpcRestClient createRpcClient, Operation record) throws Throwable {
        return createRpcClient.invoke(
                "saveOrUpdateData",
                Collections.singletonMap("dataparams", record),
                RequestId.class
        );
    }

    protected static AnalyzeResponse sendData(JsonRpcRestClient searchRpcClient, SendRequest request) throws Throwable {
        return searchRpcClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                AnalyzeResponse.class
        );
    }

}
