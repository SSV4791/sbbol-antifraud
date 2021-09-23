package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.ElectronicReceiptOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;

abstract class ElectronicReceiptIntegrationTest extends DataSpaceIntegrationTest {

    private static JsonRpcRestClient rpcRestClient;
    @Autowired
    private DataspaceCoreSearchClient searchClient;

    @BeforeAll
    static void setup() throws MalformedURLException {
        rpcRestClient = new JsonRpcRestClient(new URL("http://localhost:8080/v2/electronicreceipt"), Collections.emptyMap());
    }

    protected static RequestId saveOrUpdateData(ElectronicReceiptOperation operation) throws Throwable {
        return rpcRestClient.invoke(
                "saveOrUpdateData",
                Collections.singletonMap("dataparams", operation),
                RequestId.class
        );
    }

    protected static AnalyzeResponse sendData(SendToAnalyzeRequest request) throws Throwable {
        return rpcRestClient.invoke(
                "analyzeOperation",
                Collections.singletonMap("analyzeparams", request),
                AnalyzeResponse.class
        );
    }

    protected ElectronicReceiptOperationGet searchElectronicReceipt(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<ElectronicReceiptOperationGet> collection = searchClient.searchElectronicReceiptOperation(erWith ->
                erWith
                        .withRequestId()
                        .withTimeStamp()
                        .withEpkId()
                        .withDigitalId()
                        .withPrivateIpAddress()
                        .withUserGuid()
                        .withTbCode()
                        .withHttpAccept()
                        .withHttpReferer()
                        .withHttpAcceptChars()
                        .withHttpAcceptEncoding()
                        .withHttpAcceptLanguage()
                        .withIpAddress()
                        .withUserAgent()
                        .withDevicePrint()
                        .withTimeOfOccurrence()
                        .withDocId()
                        .withDocNumber()
                        .withDocDate()
                        .withAmount()
                        .withCurrency()
                        .withAccountNumber()
                        .withCodeBic()
                        .withDestination()
                        .withPayerName()
                        .withPayerInn()
                        .withFirstName()
                        .withSecondName()
                        .withMiddleName()
                        .withBirthDay()
                        .withDulType()
                        .withDulSerieNumber()
                        .withDulWhoIssue()
                        .withDulDateIssue()
                        .withDulCodeIssue()
                        .withReceiptDate()
                        .withReceiptTbCode()
                        .withReceiptOsbNumber()
                        .withReceiptVspNumber()
                        .withReceiptPlaceName()
                        .withReceiptPlaceAddress()
                        .withFirstSignTime()
                        .withFirstSignIp()
                        .withFirstSignLogin()
                        .withFirstSignCryptoprofile()
                        .withFirstSignCryptoprofileType()
                        .withFirstSignChannel()
                        .withFirstSignToken()
                        .withFirstSignType()
                        .withFirstSignImsi()
                        .withFirstSignCertId()
                        .withFirstSignPhone()
                        .withFirstSignEmail()
                        .withSenderSignTime()
                        .withSenderIp()
                        .withSenderLogin()
                        .withSenderCryptoprofile()
                        .withSenderCryptoprofileType()
                        .withSenderSignChannel()
                        .withSenderToken()
                        .withSenderSignType()
                        .withSenderSignImsi()
                        .withSenderCertId()
                        .withSenderPhone()
                        .withSenderEmail()
                        .withSecondSignTime()
                        .withSecondSignIp()
                        .withSecondSignLogin()
                        .withSecondSignCryptoprofile()
                        .withSecondSignCryptoprofileType()
                        .withSecondSignChannel()
                        .withSecondSignToken()
                        .withSecondSignType()
                        .withSecondSignImsi()
                        .withSecondSignCertId()
                        .withSecondSignPhone()
                        .withSecondSignEmail()
                        .setWhere(where -> where.docIdEq(docId.toString()))
                        .setLimit(1)
        );
        return collection.isEmpty() ? null : collection.get(0);
    }

}
