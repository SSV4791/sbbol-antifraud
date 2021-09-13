package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.junit.jupiter.api.BeforeEach;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import java.util.Random;
import java.util.UUID;

public abstract class PaymentIntegrationTest extends DataSpaceIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static UUID requestId;

    @BeforeEach
    protected void fillDatabase() throws Throwable {
        requestId = generatePayment(DOC_ID, DOC_NUMBER).getId();
        generatePayment(null, null);
    }

    protected RequestId generatePayment(UUID docId, Integer docNumber) throws Throwable {
        PaymentOperation payment = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        return saveOrUpdateData(payment);
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
