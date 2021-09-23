package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class PaymentIntegrationTest extends DataSpaceIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static UUID requestId;

    private static JsonRpcRestClient createRpcClientV1;
    private static JsonRpcRestClient searchRpcClientV1;
    private static JsonRpcRestClient rpcRestClientV2;

    @BeforeAll
    static void setup() throws MalformedURLException {
        createRpcClientV1 = new JsonRpcRestClient(new URL(URL_ROOT + "/v1/savedata"), Collections.emptyMap());
        searchRpcClientV1 = new JsonRpcRestClient(new URL(URL_ROOT + "/v1/analyzeoperation"), Collections.emptyMap());
        rpcRestClientV2 = new JsonRpcRestClient(new URL(URL_ROOT + "/v2/payment"), Collections.emptyMap());
    }

    @BeforeEach
    protected void fillDatabase() throws Throwable {
        requestId = generatePayment(DOC_ID, DOC_NUMBER).getId();
        generatePayment(null, null);
    }

    protected static Stream<JsonRpcRestClient> createRpcClientProvider() {
        return Stream.of(createRpcClientV1, rpcRestClientV2);
    }

    protected static Stream<JsonRpcRestClient> searchRpcClientProvider() {
        return Stream.of(searchRpcClientV1, rpcRestClientV2);
    }

    protected RequestId generatePayment(UUID docId, Integer docNumber) throws Throwable {
        PaymentOperation payment = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        return saveOrUpdateData(createRpcClientV1, payment);
    }

    protected PaymentOperation createRandomPayment() {
        return PaymentBuilder.getInstance()
                .build();
    }

    protected PaymentOperationGet searchPayment(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<PaymentOperationGet> collection = searchClient.searchPaymentOperation(pWith -> pWith
                .withRequestId()
                .withTimeStamp()
                .withEpkId()
                .withDigitalId()
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
                .withMobSdkData()
                .withChannelIndicator()
                .withTimeOfOccurrence()
                .withDocId()
                .withDocNumber()
                .withDocDate()
                .withAmount()
                .withCurrency()
                .withExecutionSpeed()
                .withOtherAccBankType()
                .withAccountNumber()
                .withOtherAccName()
                .withBalAccNumber()
                .withOtherBicCode()
                .withOtherAccOwnershipType()
                .withOtherAccType()
                .withTransferMediumType()
                .withReceiverInn()
                .withDestination()
                .withPayerInn()
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
                .withFirstSignSource()
                .withPrivateIpAddress()
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
                .withSenderSource()
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
                .withSecondSignSource()
                .withThirdSignTime()
                .withThirdSignIp()
                .withThirdSignLogin()
                .withThirdSignCryptoprofile()
                .withThirdSignCryptoprofileType()
                .withThirdSignChannel()
                .withThirdSignToken()
                .withThirdSignType()
                .withThirdSignImsi()
                .withThirdSignCertId()
                .withThirdSignPhone()
                .withThirdSignEmail()
                .withThirdSignSource()
                .withClientDefinedChannelIndicator()
                .setWhere(where -> where.docIdEq(docId.toString()))
                .setLimit(1));
        return collection.isEmpty() ? null : collection.get(0);
    }

}
