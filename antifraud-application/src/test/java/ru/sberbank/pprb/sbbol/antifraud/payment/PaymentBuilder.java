package ru.sberbank.pprb.sbbol.antifraud.payment;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentReceiver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentBuilder {

    private LocalDateTime timeStamp;
    private String orgGuid;
    private String digitalId;
    private LocalDateTime timeOfOccurrence;

    private UUID docId;
    private Integer docNumber;
    private LocalDate docDate;
    private Long amount;
    private String currency;
    private String executionSpeed;
    private String otherAccBankType;
    private String otherAccOwnershipType;
    private String transferMediumType;
    private String destination;

    private String accountNumber;
    private String payerInn;

    private String balAccNumber;
    private String otherAccName;
    private String otherBicCode;
    private String otherAccType;
    private String receiverInn;

    private List<String> signs;

    public static PaymentBuilder getInstance() {
        return new PaymentBuilder();
    }

    public PaymentOperation build() {
        PaymentOperation payment = new PaymentOperation();
        payment.setTimeStamp(timeStamp != null ? timeStamp : LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        payment.setOrgGuid(orgGuid != null ? orgGuid : UUID.randomUUID().toString());
        payment.setDigitalId(digitalId != null ? digitalId : RandomStringUtils.randomNumeric(5));
        payment.setTimeOfOccurrence(timeOfOccurrence != null ? timeOfOccurrence : LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        payment.setDocument(new PaymentDocument());
        payment.getDocument().setId(docId != null ? docId : UUID.randomUUID());
        payment.getDocument().setNumber(docNumber != null ? docNumber : Math.abs(RandomUtils.nextInt()));
        payment.getDocument().setDate(docDate != null ? docDate : LocalDate.now());
        payment.getDocument().setAmount(amount != null ? amount : Math.abs(RandomUtils.nextLong()));
        payment.getDocument().setCurrency(currency != null ? currency : "RUB");
        payment.getDocument().setExecutionSpeed(executionSpeed != null ? executionSpeed : RandomStringUtils.randomAlphabetic(30));
        payment.getDocument().setOtherAccBankType(otherAccBankType != null ? otherAccBankType : RandomStringUtils.randomAlphabetic(20));
        payment.getDocument().setOtherAccOwnershipType(otherAccOwnershipType != null ? otherAccOwnershipType : RandomStringUtils.randomAlphabetic(15));
        payment.getDocument().setTransferMediumType(transferMediumType != null ? transferMediumType : RandomStringUtils.randomAlphabetic(30));
        payment.getDocument().setDestination(destination != null ? destination : RandomStringUtils.randomAlphabetic(25));

        payment.getDocument().setPayer(new PaymentPayer());
        payment.getDocument().getPayer().setAccountNumber(accountNumber != null ? accountNumber : RandomStringUtils.randomNumeric(20));
        payment.getDocument().getPayer().setInn(payerInn != null ? payerInn : RandomStringUtils.randomNumeric(12));

        payment.getDocument().setReceiver(new PaymentReceiver());
        payment.getDocument().getReceiver().setBalAccNumber(balAccNumber != null ? balAccNumber : RandomStringUtils.randomNumeric(20));
        payment.getDocument().getReceiver().setOtherAccName(otherAccName != null ? otherAccName : RandomStringUtils.randomAlphabetic(25));
        payment.getDocument().getReceiver().setOtherBicCode(otherBicCode != null ? otherBicCode : RandomStringUtils.randomNumeric(11));
        payment.getDocument().getReceiver().setOtherAccType(otherAccType != null ? otherAccType : RandomStringUtils.randomAlphabetic(20));
        payment.getDocument().getReceiver().setInn(receiverInn != null ? receiverInn : RandomStringUtils.randomNumeric(12));

        payment.setSigns(signs != null ? signs : createSings());
        return payment;
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
                "\"channelIndicator\": \"MOBILE\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522015f\", " +
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
                "\"clientDefinedChannelIndicator\": \"PPRB_MOBSBBOL\"" +
                "}";
        String sign3 = "{" +
                "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                "\"ipAddress\": \"78.245.9.89\", " +
                "\"privateIpAddress\": \"172.16.0.0\", " +
                "\"tbCode\": \"546738\", " +
                "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                "\"mobSdkData\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3D\", " +
                "\"channelIndicator\": \"BRANCH\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522016f\", " +
                "\"signTime\": \"2020-03-23T16:00:05\", " +
                "\"signLogin\": \"petrov11\", " +
                "\"signCryptoprofile\": \"Петров Петр Петрович\", " +
                "\"signCryptoprofileType\": \"OneTimePassword\", " +
                "\"signToken\": \"signToken\", " +
                "\"signType\": \"Единственная подпись\", " +
                "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                "\"signCertId\": \"signCertId\", " +
                "\"signPhone\": \"916 243-67-34\", " +
                "\"signEmail\": \"pe@glavbaza36.ru\", " +
                "\"signChannel\": \"TOKEN\", " +
                "\"signSource\": \"SMS\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_OFFICE\"" +
                "}";
        String sign4 = "{" +
                "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                "\"ipAddress\": \"78.245.9.90\", " +
                "\"privateIpAddress\": \"172.16.0.0\", " +
                "\"tbCode\": \"546738\", " +
                "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                "\"mobSdkData\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3D\", " +
                "\"channelIndicator\": \"OTHER\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522016f\", " +
                "\"signTime\": \"2020-03-23T16:32:05\", " +
                "\"signLogin\": \"sidorov22\", " +
                "\"signCryptoprofile\": \"Сидоров ПМ\", " +
                "\"signCryptoprofileType\": \"OneTimePassword\", " +
                "\"signToken\": \"signToken\", " +
                "\"signType\": \"Единственная подпись\", " +
                "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                "\"signCertId\": \"signCertId\", " +
                "\"signPhone\": \"903 243-60-30\", " +
                "\"signEmail\": \"pe@glavbaza36.ru\", " +
                "\"signChannel\": \"TOKEN\", " +
                "\"signSource\": \"SMS\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_UPG_CORP\"" +
                "}";
        List<String> signs = new ArrayList<>(4);
        signs.add(sign3);
        signs.add(sign1);
        signs.add(sign2);
        signs.add(sign4);
        return signs;
    }

    public PaymentBuilder withTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public PaymentBuilder withOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
        return this;
    }

    public PaymentBuilder withDigitalId(String digitalId) {
        this.digitalId = digitalId;
        return this;
    }


    public PaymentBuilder withTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
        return this;
    }

    public PaymentBuilder withDocId(UUID docId) {
        this.docId = docId;
        return this;
    }

    public PaymentBuilder withDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
        return this;
    }

    public PaymentBuilder withDocDate(LocalDate docDate) {
        this.docDate = docDate;
        return this;
    }

    public PaymentBuilder withAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public PaymentBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public PaymentBuilder withExecutionSpeed(String executionSpeed) {
        this.executionSpeed = executionSpeed;
        return this;
    }

    public PaymentBuilder withOtherAccBankType(String otherAccBankType) {
        this.otherAccBankType = otherAccBankType;
        return this;
    }

    public PaymentBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public PaymentBuilder withBalAccNumber(String balAccNumber) {
        this.balAccNumber = balAccNumber;
        return this;
    }

    public PaymentBuilder withOtherAccName(String otherAccName) {
        this.otherAccName = otherAccName;
        return this;
    }

    public PaymentBuilder withOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
        return this;
    }

    public PaymentBuilder withOtherAccOwnershipType(String otherAccOwnershipType) {
        this.otherAccOwnershipType = otherAccOwnershipType;
        return this;
    }

    public PaymentBuilder withOtherAccType(String otherAccType) {
        this.otherAccType = otherAccType;
        return this;
    }

    public PaymentBuilder withTransferMediumType(String transferMediumType) {
        this.transferMediumType = transferMediumType;
        return this;
    }

    public PaymentBuilder withReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
        return this;
    }

    public PaymentBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public PaymentBuilder withPayerInn(String payerInn) {
        this.payerInn = payerInn;
        return this;
    }

    public PaymentBuilder withSigns(List<String> signs) {
        this.signs = signs;
        return this;
    }

}
