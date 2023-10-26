package ru.sberbank.pprb.sbbol.antifraud.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClientWithReporting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.dcbqa.allureee.annotations.export.customfield.JiraAC;
import ru.sberbank.pprb.sbbol.antifraud.AntiFraudRunner;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithoutSavingRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendAfterSavingRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import uk.co.jemos.podam.api.AbstractClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AntiFraudRunner.class}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(initializers = {HibernatePluginCleanerInitializer.class})
@Import(TestReplicationConfiguration.class)
@JiraAC("ППРБ ЮЛ История операций События антифрода [CI03045533]")
public abstract class AbstractIntegrationTest {

    private final String context;

    private JsonRpcRestClientWithReporting jsonRpcRestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${fpis.endpoint}")
    private String endPoint;

    @LocalServerPort
    int port;

    private MockRestServiceServer mockServer;

    public AbstractIntegrationTest(String context) {
        this.context = context;
    }

    @BeforeAll
    void setupRestClientAndMockServer() throws MalformedURLException {
        this.jsonRpcRestClient = new JsonRpcRestClientWithReporting(new URL("http://localhost:" + port + context), objectMapper);
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void verifyAndResetMockServer() {
        mockServer.verify();
        mockServer.reset();
    }

    protected void addExcludedFields(PodamFactory podamFactory, Class<?> pojoClass, String... fieldNames) {
        for (String fieldName : fieldNames) {
            ((AbstractClassInfoStrategy) podamFactory.getClassStrategy()).addExcludedField(pojoClass, fieldName);
        }
    }

    protected ObjectMapper objectMapper() {
        return objectMapper;
    }

    protected String endPoint() {
        return endPoint;
    }

    protected MockRestServiceServer mockServer() {
        return mockServer;
    }

    protected PodamFactory podamFactory() {
        return new PodamFactoryImpl();
    }

    protected RequestId saveOrUpdate(Operation record) throws Throwable {
        return jsonRpcRestClient.invoke(
                "saveOrUpdateData",
                Collections.singletonMap("dataparams", record),
                RequestId.class
        );
    }

    protected AnalyzeResponse send(SendAfterSavingRq request) throws Throwable {
        return jsonRpcRestClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                AnalyzeResponse.class
        );
    }

    protected AnalyzeResponse send(AnalyzeWithoutSavingRequest request) throws Throwable {
        return jsonRpcRestClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                AnalyzeResponse.class
        );
    }

    protected FullAnalyzeResponse sendWithFullResponse(SendAfterSavingRq request) throws Throwable {
        return jsonRpcRestClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                FullAnalyzeResponse.class
        );
    }

    protected FullAnalyzeResponse sendWithFullResponse(AnalyzeWithoutSavingRequest request) throws Throwable {
        return jsonRpcRestClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                FullAnalyzeResponse.class
        );
    }

    protected AnalyzeResponse sendPaymentV3(AnalyzeWithoutSavingRequest request) throws Throwable {
        return jsonRpcRestClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("dataparams", request),
                AnalyzeResponse.class
        );
    }

}
