package ru.sberbank.pprb.sbbol.antifraud.common;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.sberbank.pprb.sbbol.antifraud.AntiFraudRunner;
import ru.sberbank.pprb.sbbol.antifraud.analyze.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.analyze.SendRequest;
import sbp.com.sbt.dataspace.core.local.runner.junit5.JUnit5DataSpaceCoreLocalRunnerExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = { AntiFraudRunner.class }
)
@ExtendWith(JUnit5DataSpaceCoreLocalRunnerExtension.class)
@DirtiesContext
@ActiveProfiles("test")
public abstract class DataSpaceIntegrationTest {

    private static final String URL_ROOT = "http://localhost:8080";

    private static JsonRpcRestClient createRpcClient;
    protected static JsonRpcRestClient searchRpcClient;
    @Autowired
    protected DataspaceCoreSearchClient searchClient;

    @BeforeAll
    static void setup() throws MalformedURLException {
        createRpcClient = new JsonRpcRestClient(new URL(URL_ROOT + "/savedata"), Collections.emptyMap());
        searchRpcClient = new JsonRpcRestClient(new URL(URL_ROOT + "/analyzeoperation"), Collections.emptyMap());
    }

    protected static RequestId saveOrUpdateData(Operation record) throws Throwable {
        return createRpcClient.invoke(
                "saveOrUpdateData",
                Collections.singletonMap("dataparams", record),
                RequestId.class
        );
    }

    protected static AnalyzeResponse sendData(SendRequest request) throws Throwable {
        return searchRpcClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                AnalyzeResponse.class
        );
    }

}
