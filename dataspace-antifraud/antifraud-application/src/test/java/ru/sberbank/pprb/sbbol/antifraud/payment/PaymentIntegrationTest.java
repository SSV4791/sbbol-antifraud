package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.junit.jupiter.api.BeforeEach;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

public abstract class PaymentIntegrationTest extends DataSpaceIntegrationTest {

    protected static final String DOC_ID = "123e4567-e89b-12d3-a456-426614174000";
    protected static final Integer DOC_NUMBER = 888;

    protected static String requestId;

    @BeforeEach
    private void fillDatabase() throws Throwable {
        requestId = generatePayment(DOC_ID, DOC_NUMBER).getId();
        generatePayment(null, null);
    }

    protected RequestId generatePayment(String docId, Integer docNumber) throws Throwable {
        PaymentOperation payment = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        return saveOrUpdateData(payment);
    }

    protected PaymentOperationGet searchPayment(String docId) throws SdkJsonRpcClientException {
        GraphCollection<PaymentOperationGet> collection = searchClient.searchPaymentOperation(pWith -> pWith
                .withRequestId()
                .withTimeStamp()
                .withOrgGuid()
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
                .withReceiverAccount()
                .withReceiverBicAccount()
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
                .setWhere(where -> where.docIdEq(docId))
                .setLimit(1));
        return collection.isEmpty() ? null : collection.get(0);
    }

}
