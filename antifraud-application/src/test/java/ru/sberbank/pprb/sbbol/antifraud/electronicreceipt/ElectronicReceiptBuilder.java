package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.Receipt;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptSign;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

class ElectronicReceiptBuilder {

    // operation
    private String orgGuid;
    private String digitalId;
    private String privateIpAddress;

    // document
    private UUID docId;
    private String docNumber;
    private LocalDate docDate;
    private Long amount;
    private String currency;
    private String destination;

    // payer
    private String tbCode;
    private String accountNumber;
    private String codeBic;
    private String payerName;
    private String payerInn;
    private String payerKpp;

    // receiver
    private String firstName;
    private String secondName;
    private String middleName;
    private LocalDate birthDay;
    private String dulType;
    private String dulSerieNumber;
    private String dulWhoIssue;
    private LocalDate dulDateIssue;
    private String dulCodeIssue;

    // receipt
    private LocalDate receiptDate;
    private String receiptTbCode;
    private String receiptOsbNumber;
    private String receiptVspNumber;
    private String receiptPlaceName;
    private String receiptPlaceAddress;

    // deviceRequest
    private String devicePrint;
    private String httpAccept;
    private String httpReferer;
    private String httpAcceptChars;
    private String httpAcceptEncoding;
    private String httpAcceptLanguage;
    private String ipAddress;
    private String userAgent;

    // sign
    private Integer signNumber;
    private String signIpAddress;
    private String signChannel;
    private LocalDateTime signTime;
    private String signLogin;
    private String signType;
    private String signCryptoprofile;
    private String signCryptoprofileType;
    private String signEmail;
    private String signToken;
    private String signCertId;
    private String signImsi;
    private String signPhone;
    private UUID userGuid;

    static ElectronicReceiptBuilder getInstance() {
        return new ElectronicReceiptBuilder();
    }

    ElectronicReceiptOperation build() {
        ElectronicReceiptOperation operation = new ElectronicReceiptOperation();
        operation.setOrgGuid(orgGuid != null ? orgGuid : UUID.randomUUID().toString());
        operation.setDigitalId(digitalId != null ? digitalId : RandomStringUtils.randomNumeric(8));
        operation.setPrivateIpAddress(privateIpAddress != null ? privateIpAddress : generateIpAddress());

        operation.setDocument(new ReceiptDocument());
        operation.getDocument().setId(docId != null ? docId : UUID.randomUUID());
        operation.getDocument().setNumber(docNumber != null ? docNumber : RandomStringUtils.randomNumeric(10));
        operation.getDocument().setDate(docDate != null ? docDate : LocalDate.now().minusDays(3));
        operation.getDocument().setAmount(amount != null ? amount : Math.abs(RandomUtils.nextLong()));
        operation.getDocument().setCurrency(currency != null ? currency : RandomStringUtils.randomAlphabetic(20));
        operation.getDocument().setDestination(destination != null ? destination : RandomStringUtils.randomAlphabetic(15));

        operation.getDocument().setPayer(new ReceiptPayer());
        operation.getDocument().getPayer().setTbCode(tbCode != null ? tbCode : RandomStringUtils.randomNumeric(10));
        operation.getDocument().getPayer().setAccountNumber(accountNumber != null ? accountNumber : RandomStringUtils.randomNumeric(20));
        operation.getDocument().getPayer().setCodeBic(codeBic != null ? codeBic : RandomStringUtils.randomNumeric(11));
        operation.getDocument().getPayer().setName(payerName != null ? payerName : RandomStringUtils.randomAlphabetic(15));
        operation.getDocument().getPayer().setInn(payerInn != null ? payerInn : RandomStringUtils.randomNumeric(12));
        operation.getDocument().getPayer().setKpp(payerKpp != null ? payerKpp : RandomStringUtils.randomNumeric(9));

        operation.getDocument().setReceiver(new ReceiptReceiver());
        operation.getDocument().getReceiver().setFirstName(firstName != null ? firstName : RandomStringUtils.randomAlphabetic(7));
        operation.getDocument().getReceiver().setSecondName(secondName != null ? secondName : RandomStringUtils.randomAlphabetic(7));
        operation.getDocument().getReceiver().setMiddleName(middleName != null ? middleName : RandomStringUtils.randomAlphabetic(7));
        operation.getDocument().getReceiver().setBirthDay(birthDay != null ? birthDay : LocalDate.now().minusYears(25));
        operation.getDocument().getReceiver().setDulType(dulType != null ? dulType : RandomStringUtils.randomAlphabetic(10));
        operation.getDocument().getReceiver().setDulSerieNumber(dulSerieNumber != null ? dulSerieNumber : RandomStringUtils.randomNumeric(12));
        operation.getDocument().getReceiver().setDulWhoIssue(dulWhoIssue != null ? dulWhoIssue : RandomStringUtils.randomAlphabetic(25));
        operation.getDocument().getReceiver().setDulDateIssue(dulDateIssue != null ? dulDateIssue : LocalDate.now().minusYears(4));
        operation.getDocument().getReceiver().setDulCodeIssue(dulCodeIssue != null ? dulCodeIssue : RandomStringUtils.randomNumeric(10));

        operation.getDocument().setReceipt(new Receipt());
        operation.getDocument().getReceipt().setReceiptDate(receiptDate != null ? receiptDate : LocalDate.now());
        operation.getDocument().getReceipt().setReceiptTbCode(tbCode != null ? tbCode : RandomStringUtils.randomNumeric(5));
        operation.getDocument().getReceipt().setReceiptOsbNumber(receiptOsbNumber != null ? receiptOsbNumber : RandomStringUtils.randomNumeric(10));
        operation.getDocument().getReceipt().setReceiptVspNumber(receiptVspNumber != null ? receiptVspNumber : RandomStringUtils.randomNumeric(10));
        operation.getDocument().getReceipt().setReceiptPlaceName(receiptPlaceName != null ? receiptPlaceName : RandomStringUtils.randomAlphabetic(25));
        operation.getDocument().getReceipt().setReceiptPlaceAddress(receiptPlaceAddress != null ? receiptPlaceAddress : RandomStringUtils.randomAlphabetic(25));

        operation.setDeviceRequest(new ReceiptDeviceRequest());
        operation.getDeviceRequest().setDevicePrint(devicePrint != null ? devicePrint : RandomStringUtils.randomAlphanumeric(20));
        operation.getDeviceRequest().setHttpAccept(httpAccept != null ? httpAccept : RandomStringUtils.randomAlphabetic(25));
        operation.getDeviceRequest().setHttpReferer(httpReferer != null ? httpReferer : RandomStringUtils.randomAlphabetic(25));
        operation.getDeviceRequest().setHttpAcceptChars(httpAcceptChars != null ? httpAcceptChars : RandomStringUtils.randomAlphabetic(25));
        operation.getDeviceRequest().setHttpAcceptEncoding(httpAcceptEncoding != null ? httpAcceptEncoding : RandomStringUtils.randomAlphabetic(25));
        operation.getDeviceRequest().setHttpAcceptLanguage(httpAcceptLanguage != null ? httpAcceptLanguage : RandomStringUtils.randomAlphabetic(25));
        operation.getDeviceRequest().setIpAddress(ipAddress != null ? ipAddress : RandomStringUtils.randomNumeric(50));
        operation.getDeviceRequest().setUserAgent(userAgent != null ? userAgent : RandomStringUtils.randomAlphabetic(25));

        operation.setSign(new ReceiptSign());
        operation.getSign().setSignNumber(signNumber != null ? signNumber : 1);
        operation.getSign().setSignIpAddress(signIpAddress != null ? signIpAddress : generateIpAddress());
        operation.getSign().setSignChannel(signChannel != null ? signChannel : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignTime(signTime != null ? signTime : LocalDateTime.parse(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(LocalDateTime.now())));
        operation.getSign().setSignLogin(signLogin != null ? signLogin : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignType(signType != null ? signType : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignCryptoprofile(signCryptoprofile != null ? signCryptoprofile : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignCryptoprofileType(signCryptoprofileType != null ? signCryptoprofileType : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignEmail(signEmail != null ? signEmail : RandomStringUtils.randomAlphabetic(8) + "@gmail.com");
        operation.getSign().setSignToken(signToken != null ? signToken : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignCertId(signCertId != null ? signCertId : RandomStringUtils.randomAlphabetic(25));
        operation.getSign().setSignImsi(signImsi != null ? signImsi : RandomStringUtils.randomAlphanumeric(20));
        operation.getSign().setSignPhone(signPhone != null ? signPhone : "+7" + RandomStringUtils.randomNumeric(10));
        operation.getSign().setUserGuid(userGuid != null ? userGuid : UUID.randomUUID());
        return operation;
    }

    private String generateIpAddress() {
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    ElectronicReceiptBuilder withDocId(UUID docId) {
        this.docId = docId;
        return this;
    }

    public ElectronicReceiptBuilder withSignNumber(Integer signNumber) {
        this.signNumber = signNumber;
        return this;
    }
}
