package ru.sberbank.pprb.sbbol.antifraud.sbp;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SbpPaymentBuilder {

    private LocalDateTime timeStamp;
    private String orgGuid;
    private String digitalId;
    private LocalDateTime timeOfOccurrence;

    private UUID docId;
    private Integer docNumber;
    private LocalDate docDate;
    private Long amount;
    private String currency;
    private String idOperationOPKC;
    private String destination;

    private String accountNumber;
    private String payerFinancialName;
    private String payerOsbNum;
    private String payerVspNum;
    private String payerAccBalance;
    private LocalDate payerAccCreateDate;
    private String payerBic;
    private String payerDocumentNumber;
    private String payerDocumentType;
    private String payerSegment;
    private String payerInn;

    private String otherAccName;
    private String otherBicCode;
    private String receiverInn;
    private String receiverBankName;
    private String receiverBankCountryCode;
    private String receiverBankCorrAcc;
    private String receiverBankId;
    private String receiverDocument;
    private String receiverDocumentType;
    private String receiverPhoneNumber;
    private String receiverAccount;

    private List<String> signs;
    
    public static SbpPaymentBuilder getInstance() {
        return new SbpPaymentBuilder();
    }
    
    public SbpPaymentOperation build() {
        SbpPaymentOperation operation = new SbpPaymentOperation();
        operation.setTimeStamp(timeStamp != null ? timeStamp : LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        operation.setOrgGuid(orgGuid != null ? orgGuid : UUID.randomUUID().toString());
        operation.setDigitalId(digitalId != null ? digitalId : RandomStringUtils.randomNumeric(5));
        operation.setTimeOfOccurrence(timeOfOccurrence != null ? timeOfOccurrence : LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        operation.setDocument(new SbpDocument());
        operation.getDocument().setId(docId != null ? docId : UUID.randomUUID());
        operation.getDocument().setNumber(docNumber != null ? docNumber : Math.abs(RandomUtils.nextInt()));
        operation.getDocument().setDate(docDate != null ? docDate : LocalDate.now());
        operation.getDocument().setAmount(amount != null ? amount : Math.abs(RandomUtils.nextLong()));
        operation.getDocument().setCurrency(currency != null ? currency : "RUB");
        operation.getDocument().setIdOperationOPKC(idOperationOPKC != null ? idOperationOPKC : RandomStringUtils.randomAlphanumeric(25));
        operation.getDocument().setDestination(destination != null ? destination : RandomStringUtils.randomAlphabetic(25));

        operation.getDocument().setPayer(new SbpPayer());
        operation.getDocument().getPayer().setAccountNumber(accountNumber != null ? accountNumber : RandomStringUtils.randomNumeric(20));
        operation.getDocument().getPayer().setFinancialName(payerFinancialName != null ? payerFinancialName : RandomStringUtils.randomAlphabetic(25));
        operation.getDocument().getPayer().setOsbNum(payerOsbNum != null ? payerOsbNum : RandomStringUtils.randomNumeric(8));
        operation.getDocument().getPayer().setVspNum(payerVspNum != null ? payerVspNum : RandomStringUtils.randomNumeric(8));
        operation.getDocument().getPayer().setAccBalance(payerAccBalance != null ? payerAccBalance : RandomStringUtils.randomNumeric(8));
        operation.getDocument().getPayer().setAccCreateDate(payerAccCreateDate != null ? payerAccCreateDate : LocalDate.now());
        operation.getDocument().getPayer().setBic(payerBic != null ? payerBic : RandomStringUtils.randomNumeric(6));
        operation.getDocument().getPayer().setDocumentNumber(payerDocumentNumber != null ? payerDocumentNumber : RandomStringUtils.randomNumeric(8));
        operation.getDocument().getPayer().setDocumentType(payerDocumentType != null ? payerDocumentType : RandomStringUtils.randomAlphabetic(6));
        operation.getDocument().getPayer().setSegment(payerSegment != null ? payerSegment : RandomStringUtils.randomAlphabetic(6));
        operation.getDocument().getPayer().setInn(payerInn != null ? payerInn : RandomStringUtils.randomNumeric(12));

        operation.getDocument().setReceiver(new SbpReceiver());
        operation.getDocument().getReceiver().setOtherAccName(otherAccName != null ? otherAccName : RandomStringUtils.randomAlphabetic(25));
        operation.getDocument().getReceiver().setOtherBicCode(otherBicCode != null ? otherBicCode : RandomStringUtils.randomNumeric(11));
        operation.getDocument().getReceiver().setInn(receiverInn != null ? receiverInn : RandomStringUtils.randomNumeric(12));
        operation.getDocument().getReceiver().setBankName(receiverBankName != null ? receiverBankName : RandomStringUtils.randomAlphabetic(15));
        operation.getDocument().getReceiver().setBankCountryCode(receiverBankCountryCode != null ? receiverBankCountryCode : RandomStringUtils.randomAlphabetic(4));
        operation.getDocument().getReceiver().setBankCorrAcc(receiverBankCorrAcc != null ? receiverBankCorrAcc : RandomStringUtils.randomNumeric(10));
        operation.getDocument().getReceiver().setBankId(receiverBankId != null ? receiverBankId : RandomStringUtils.randomNumeric(10));
        operation.getDocument().getReceiver().setDocument(receiverDocument != null ? receiverDocument : RandomStringUtils.randomAlphabetic(10));
        operation.getDocument().getReceiver().setDocumentType(receiverDocumentType != null ? receiverDocumentType : RandomStringUtils.randomAlphabetic(10));
        operation.getDocument().getReceiver().setPhoneNumber(receiverPhoneNumber != null ? receiverPhoneNumber : RandomStringUtils.randomNumeric(13));
        operation.getDocument().getReceiver().setAccount(receiverAccount != null ? receiverAccount : RandomStringUtils.randomNumeric(20));

        operation.setSigns(signs != null ? signs : createSings());
        return operation;
    }

    private List<String> createSings() {
        String sign1 = "{" +
                "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                "\"ipAddress\": \"78.245.9.87\", " +
                "\"privateIpAddress\": \"172.16.0.0\", " +
                "\"tbCode\": \"546738\", " +
                "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                "\"mobSdkData\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3D\", " +
                "\"channelIndicator\": \"WEB\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                "\"signTime\": \"2020-03-23T15:01:15\", " +
                "\"signLogin\": \"novikova01\", " +
                "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                "\"signCryptoprofileType\": \"OneTimePassword\", " +
                "\"signToken\": \"signToken\", " +
                "\"signType\": \"Единственная подпись\", " +
                "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                "\"signCertId\": \"signCertId\", " +
                "\"signPhone\": \"915 168-67-32\", " +
                "\"signEmail\": \"no@glavbaza36.ru\", " +
                "\"signChannel\": \"TOKEN\", " +
                "\"signSource\": \"SMS\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                "}";
        String sign2 = "{" +
                "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                "\"ipAddress\": \"78.245.9.88\", " +
                "\"privateIpAddress\": \"172.16.0.0\", " +
                "\"tbCode\": \"546738\", " +
                "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                "\"mobSdkData\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3D\", " +
                "\"channelIndicator\": \"WEB\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                "\"signTime\": \"2020-03-23T15:28:25\", " +
                "\"signLogin\": \"ivanov05\", " +
                "\"signCryptoprofile\": \"Иванов Иван Иванович\", " +
                "\"signCryptoprofileType\": \"OneTimePassword\", " +
                "\"signToken\": \"signToken\", " +
                "\"signType\": \"Единственная подпись\", " +
                "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                "\"signCertId\": \"signCertId\", " +
                "\"signPhone\": \"903 158-55-12\", " +
                "\"signEmail\": \"iv@glavbaza36.ru\", " +
                "\"signChannel\": \"TOKEN\", " +
                "\"signSource\": \"SMS\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                "}";
        List<String> signs = new ArrayList<>(2);
        signs.add(sign2);
        signs.add(sign1);
        return signs;
    }

    public SbpPaymentBuilder withTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public SbpPaymentBuilder withOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
        return this;
    }

    public SbpPaymentBuilder withDigitalId(String digitalId) {
        this.digitalId = digitalId;
        return this;
    }

    public SbpPaymentBuilder withTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
        return this;
    }

    public SbpPaymentBuilder withDocId(UUID docId) {
        this.docId = docId;
        return this;
    }

    public SbpPaymentBuilder withDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
        return this;
    }

    public SbpPaymentBuilder withDocDate(LocalDate docDate) {
        this.docDate = docDate;
        return this;
    }

    public SbpPaymentBuilder withAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public SbpPaymentBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public SbpPaymentBuilder withIdOperationOPKC(String idOperationOPKC) {
        this.idOperationOPKC = idOperationOPKC;
        return this;
    }

    public SbpPaymentBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public SbpPaymentBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public SbpPaymentBuilder withPayerFinancialName(String payerFinancialName) {
        this.payerFinancialName = payerFinancialName;
        return this;
    }

    public SbpPaymentBuilder withPayerOsbNum(String payerOsbNum) {
        this.payerOsbNum = payerOsbNum;
        return this;
    }

    public SbpPaymentBuilder withPayerVspNum(String payerVspNum) {
        this.payerVspNum = payerVspNum;
        return this;
    }

    public SbpPaymentBuilder withPayerAccBalance(String payerAccBalance) {
        this.payerAccBalance = payerAccBalance;
        return this;
    }

    public SbpPaymentBuilder withPayerAccCreateDate(LocalDate payerAccCreateDate) {
        this.payerAccCreateDate = payerAccCreateDate;
        return this;
    }

    public SbpPaymentBuilder withPayerBic(String payerBic) {
        this.payerBic = payerBic;
        return this;
    }

    public SbpPaymentBuilder withPayerDocumentNumber(String payerDocumentNumber) {
        this.payerDocumentNumber = payerDocumentNumber;
        return this;
    }

    public SbpPaymentBuilder withPayerDocumentType(String payerDocumentType) {
        this.payerDocumentType = payerDocumentType;
        return this;
    }

    public SbpPaymentBuilder withPayerSegment(String payerSegment) {
        this.payerSegment = payerSegment;
        return this;
    }

    public SbpPaymentBuilder withPayerInn(String payerInn) {
        this.payerInn = payerInn;
        return this;
    }

    public SbpPaymentBuilder withOtherAccName(String otherAccName) {
        this.otherAccName = otherAccName;
        return this;
    }

    public SbpPaymentBuilder withOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
        return this;
    }

    public SbpPaymentBuilder withReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
        return this;
    }

    public SbpPaymentBuilder withReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
        return this;
    }

    public SbpPaymentBuilder withReceiverBankCountryCode(String receiverBankCountryCode) {
        this.receiverBankCountryCode = receiverBankCountryCode;
        return this;
    }

    public SbpPaymentBuilder withReceiverBankCorrAcc(String receiverBankCorrAcc) {
        this.receiverBankCorrAcc = receiverBankCorrAcc;
        return this;
    }

    public SbpPaymentBuilder withReceiverBankId(String receiverBankId) {
        this.receiverBankId = receiverBankId;
        return this;
    }

    public SbpPaymentBuilder withReceiverDocument(String receiverDocument) {
        this.receiverDocument = receiverDocument;
        return this;
    }

    public SbpPaymentBuilder withReceiverDocumentType(String receiverDocumentType) {
        this.receiverDocumentType = receiverDocumentType;
        return this;
    }

    public SbpPaymentBuilder withReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
        return this;
    }

    public SbpPaymentBuilder withReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
        return this;
    }

    public SbpPaymentBuilder withSigns(List<String> signs) {
        this.signs = signs;
        return this;
    }
}
