package ru.sberbank.pprb.sbbol.antifraud.sbp;

import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.junit.jupiter.api.BeforeEach;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import java.util.UUID;

public abstract class SbpPaymentIntegrationTest extends DataSpaceIntegrationTest {

    protected static final UUID DOC_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    protected static final Integer DOC_NUMBER = 888;

    protected static UUID requestId;

    @BeforeEach
    protected void fillDatabase() throws Throwable {
        requestId = generateSbpPayment(DOC_ID, DOC_NUMBER).getId();
        generateSbpPayment(null, null);
    }

    protected RequestId generateSbpPayment(UUID docId, Integer docNumber) throws Throwable {
        SbpPaymentOperation operation = SbpPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        return saveOrUpdateData(operation);
    }

    protected SbpPaymentOperation createRandomSbpPayment() {
        return SbpPaymentBuilder.getInstance()
                .build();
    }

    protected SbpPaymentOperationGet searchSbpPayment(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<SbpPaymentOperationGet> collection = searchClient.searchSbpPaymentOperation(sbpWith ->
            sbpWith
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
                    .withAccountNumber()
                    .withOtherAccName()
                    .withOtherBicCode()
                    .withReceiverInn()
                    .withReceiverBankName()
                    .withReceiverBankCountryCode()
                    .withReceiverBankCorrAcc()
                    .withReceiverBankId()
                    .withReceiverPhoneNumber()
                    .withReceiverAccount()
                    .withIdOperationOPKC()
                    .withReceiverDocument()
                    .withReceiverDocumentType()
                    .withDestination()
                    .withPayerFinancialName()
                    .withPayerOsbNum()
                    .withPayerVspNum()
                    .withPayerAccBalance()
                    .withPayerAccCreateDate()
                    .withPayerBic()
                    .withPayerDocumentNumber()
                    .withPayerDocumentType()
                    .withPayerSegment()
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
                    .withClientDefinedChannelIndicator()
                    .setWhere(where -> where.docIdEq(docId.toString()))
                    .setLimit(1)
        );
        return collection.isEmpty() ? null : collection.get(0);
    }
}
