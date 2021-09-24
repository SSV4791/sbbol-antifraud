package ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt;

import ru.sberbank.pprb.sbbol.antifraud.graph.get.ElectronicReceiptOperationGet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

class ElectronicReceiptOperationGetImpl implements ElectronicReceiptOperationGet {

    private String objectId;
    private String requestId;
    private LocalDateTime timeStamp;
    private String epkId;
    private String digitalId;
    private String privateIpAddress;
    private String docId;
    private Integer docNumber;
    private LocalDate docDate;
    private Long amount;
    private String currency;
    private String destination;
    private String tbCode;
    private String accountNumber;
    private String codeBic;
    private String payerName;
    private String payerInn;
    private String firstName;
    private String secondName;
    private String middleName;
    private LocalDate birthDay;
    private String dulType;
    private String dulSerieNumber;
    private String dulWhoIssue;
    private LocalDate dulDateIssue;
    private String dulCodeIssue;
    private LocalDate receiptDate;
    private String receiptTbCode;
    private String receiptOsbNumber;
    private String receiptVspNumber;
    private String receiptPlaceName;
    private String receiptPlaceAddress;
    private String devicePrint;
    private LocalDateTime timeOfOccurrence;
    private String httpAccept;
    private String httpReferer;
    private String httpAcceptChars;
    private String httpAcceptEncoding;
    private String httpAcceptLanguage;
    private String ipAddress;
    private String userAgent;
    private String userGuid;
    private String firstSignIp;
    private String firstSignChannel;
    private LocalDateTime firstSignTime;
    private String firstSignLogin;
    private String firstSignType;
    private String firstSignCryptoprofile;
    private String firstSignCryptoprofileType;
    private String firstSignEmail;
    private String firstSignToken;
    private String firstSignCertId;
    private String firstSignImsi;
    private String firstSignPhone;
    private String secondSignIp;
    private String secondSignChannel;
    private LocalDateTime secondSignTime;
    private String secondSignLogin;
    private String secondSignType;
    private String secondSignCryptoprofile;
    private String secondSignCryptoprofileType;
    private String secondSignEmail;
    private String secondSignToken;
    private String secondSignCertId;
    private String secondSignImsi;
    private String secondSignPhone;
    private String senderIp;
    private String senderSignChannel;
    private LocalDateTime senderSignTime;
    private String senderLogin;
    private String senderSignType;
    private String senderCryptoprofile;
    private String senderCryptoprofileType;
    private String senderEmail;
    private String senderToken;
    private String senderCertId;
    private String senderSignImsi;
    private String senderPhone;
    private String type;
    private Date lastChangeDate;
    private Long chgCnt;

    @Override
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String getEpkId() {
        return epkId;
    }

    public void setEpkId(String epkId) {
        this.epkId = epkId;
    }

    @Override
    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    @Override
    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    @Override
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public Integer getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
    }

    @Override
    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    @Override
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getCodeBic() {
        return codeBic;
    }

    public void setCodeBic(String codeBic) {
        this.codeBic = codeBic;
    }

    @Override
    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    @Override
    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String getDulType() {
        return dulType;
    }

    public void setDulType(String dulType) {
        this.dulType = dulType;
    }

    @Override
    public String getDulSerieNumber() {
        return dulSerieNumber;
    }

    public void setDulSerieNumber(String dulSerieNumber) {
        this.dulSerieNumber = dulSerieNumber;
    }

    @Override
    public String getDulWhoIssue() {
        return dulWhoIssue;
    }

    public void setDulWhoIssue(String dulWhoIssue) {
        this.dulWhoIssue = dulWhoIssue;
    }

    @Override
    public LocalDate getDulDateIssue() {
        return dulDateIssue;
    }

    public void setDulDateIssue(LocalDate dulDateIssue) {
        this.dulDateIssue = dulDateIssue;
    }

    @Override
    public String getDulCodeIssue() {
        return dulCodeIssue;
    }

    public void setDulCodeIssue(String dulCodeIssue) {
        this.dulCodeIssue = dulCodeIssue;
    }

    @Override
    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Override
    public String getReceiptTbCode() {
        return receiptTbCode;
    }

    public void setReceiptTbCode(String receiptTbCode) {
        this.receiptTbCode = receiptTbCode;
    }

    @Override
    public String getReceiptOsbNumber() {
        return receiptOsbNumber;
    }

    public void setReceiptOsbNumber(String receiptOsbNumber) {
        this.receiptOsbNumber = receiptOsbNumber;
    }

    @Override
    public String getReceiptVspNumber() {
        return receiptVspNumber;
    }

    public void setReceiptVspNumber(String receiptVspNumber) {
        this.receiptVspNumber = receiptVspNumber;
    }

    @Override
    public String getReceiptPlaceName() {
        return receiptPlaceName;
    }

    public void setReceiptPlaceName(String receiptPlaceName) {
        this.receiptPlaceName = receiptPlaceName;
    }

    @Override
    public String getReceiptPlaceAddress() {
        return receiptPlaceAddress;
    }

    public void setReceiptPlaceAddress(String receiptPlaceAddress) {
        this.receiptPlaceAddress = receiptPlaceAddress;
    }

    @Override
    public String getDevicePrint() {
        return devicePrint;
    }

    public void setDevicePrint(String devicePrint) {
        this.devicePrint = devicePrint;
    }

    @Override
    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    @Override
    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    @Override
    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    @Override
    public String getHttpAcceptChars() {
        return httpAcceptChars;
    }

    public void setHttpAcceptChars(String httpAcceptChars) {
        this.httpAcceptChars = httpAcceptChars;
    }

    @Override
    public String getHttpAcceptEncoding() {
        return httpAcceptEncoding;
    }

    public void setHttpAcceptEncoding(String httpAcceptEncoding) {
        this.httpAcceptEncoding = httpAcceptEncoding;
    }

    @Override
    public String getHttpAcceptLanguage() {
        return httpAcceptLanguage;
    }

    public void setHttpAcceptLanguage(String httpAcceptLanguage) {
        this.httpAcceptLanguage = httpAcceptLanguage;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    @Override
    public String getFirstSignIp() {
        return firstSignIp;
    }

    public void setFirstSignIp(String firstSignIp) {
        this.firstSignIp = firstSignIp;
    }

    @Override
    public String getFirstSignChannel() {
        return firstSignChannel;
    }

    public void setFirstSignChannel(String firstSignChannel) {
        this.firstSignChannel = firstSignChannel;
    }

    @Override
    public LocalDateTime getFirstSignTime() {
        return firstSignTime;
    }

    public void setFirstSignTime(LocalDateTime firstSignTime) {
        this.firstSignTime = firstSignTime;
    }

    @Override
    public String getFirstSignLogin() {
        return firstSignLogin;
    }

    public void setFirstSignLogin(String firstSignLogin) {
        this.firstSignLogin = firstSignLogin;
    }

    @Override
    public String getFirstSignType() {
        return firstSignType;
    }

    public void setFirstSignType(String firstSignType) {
        this.firstSignType = firstSignType;
    }

    @Override
    public String getFirstSignCryptoprofile() {
        return firstSignCryptoprofile;
    }

    public void setFirstSignCryptoprofile(String firstSignCryptoprofile) {
        this.firstSignCryptoprofile = firstSignCryptoprofile;
    }

    @Override
    public String getFirstSignCryptoprofileType() {
        return firstSignCryptoprofileType;
    }

    public void setFirstSignCryptoprofileType(String firstSignCryptoprofileType) {
        this.firstSignCryptoprofileType = firstSignCryptoprofileType;
    }

    @Override
    public String getFirstSignEmail() {
        return firstSignEmail;
    }

    public void setFirstSignEmail(String firstSignEmail) {
        this.firstSignEmail = firstSignEmail;
    }

    @Override
    public String getFirstSignToken() {
        return firstSignToken;
    }

    public void setFirstSignToken(String firstSignToken) {
        this.firstSignToken = firstSignToken;
    }

    @Override
    public String getFirstSignCertId() {
        return firstSignCertId;
    }

    public void setFirstSignCertId(String firstSignCertId) {
        this.firstSignCertId = firstSignCertId;
    }

    @Override
    public String getFirstSignImsi() {
        return firstSignImsi;
    }

    public void setFirstSignImsi(String firstSignImsi) {
        this.firstSignImsi = firstSignImsi;
    }

    @Override
    public String getFirstSignPhone() {
        return firstSignPhone;
    }

    public void setFirstSignPhone(String firstSignPhone) {
        this.firstSignPhone = firstSignPhone;
    }

    @Override
    public String getSecondSignIp() {
        return secondSignIp;
    }

    public void setSecondSignIp(String secondSignIp) {
        this.secondSignIp = secondSignIp;
    }

    @Override
    public String getSecondSignChannel() {
        return secondSignChannel;
    }

    public void setSecondSignChannel(String secondSignChannel) {
        this.secondSignChannel = secondSignChannel;
    }

    @Override
    public LocalDateTime getSecondSignTime() {
        return secondSignTime;
    }

    public void setSecondSignTime(LocalDateTime secondSignTime) {
        this.secondSignTime = secondSignTime;
    }

    @Override
    public String getSecondSignLogin() {
        return secondSignLogin;
    }

    public void setSecondSignLogin(String secondSignLogin) {
        this.secondSignLogin = secondSignLogin;
    }

    @Override
    public String getSecondSignType() {
        return secondSignType;
    }

    public void setSecondSignType(String secondSignType) {
        this.secondSignType = secondSignType;
    }

    @Override
    public String getSecondSignCryptoprofile() {
        return secondSignCryptoprofile;
    }

    public void setSecondSignCryptoprofile(String secondSignCryptoprofile) {
        this.secondSignCryptoprofile = secondSignCryptoprofile;
    }

    @Override
    public String getSecondSignCryptoprofileType() {
        return secondSignCryptoprofileType;
    }

    public void setSecondSignCryptoprofileType(String secondSignCryptoprofileType) {
        this.secondSignCryptoprofileType = secondSignCryptoprofileType;
    }

    @Override
    public String getSecondSignEmail() {
        return secondSignEmail;
    }

    public void setSecondSignEmail(String secondSignEmail) {
        this.secondSignEmail = secondSignEmail;
    }

    @Override
    public String getSecondSignToken() {
        return secondSignToken;
    }

    public void setSecondSignToken(String secondSignToken) {
        this.secondSignToken = secondSignToken;
    }

    @Override
    public String getSecondSignCertId() {
        return secondSignCertId;
    }

    public void setSecondSignCertId(String secondSignCertId) {
        this.secondSignCertId = secondSignCertId;
    }

    @Override
    public String getSecondSignImsi() {
        return secondSignImsi;
    }

    public void setSecondSignImsi(String secondSignImsi) {
        this.secondSignImsi = secondSignImsi;
    }

    @Override
    public String getSecondSignPhone() {
        return secondSignPhone;
    }

    public void setSecondSignPhone(String secondSignPhone) {
        this.secondSignPhone = secondSignPhone;
    }

    @Override
    public String getSenderIp() {
        return senderIp;
    }

    public void setSenderIp(String senderIp) {
        this.senderIp = senderIp;
    }

    @Override
    public String getSenderSignChannel() {
        return senderSignChannel;
    }

    public void setSenderSignChannel(String senderSignChannel) {
        this.senderSignChannel = senderSignChannel;
    }

    @Override
    public LocalDateTime getSenderSignTime() {
        return senderSignTime;
    }

    public void setSenderSignTime(LocalDateTime senderSignTime) {
        this.senderSignTime = senderSignTime;
    }

    @Override
    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    @Override
    public String getSenderSignType() {
        return senderSignType;
    }

    public void setSenderSignType(String senderSignType) {
        this.senderSignType = senderSignType;
    }

    @Override
    public String getSenderCryptoprofile() {
        return senderCryptoprofile;
    }

    public void setSenderCryptoprofile(String senderCryptoprofile) {
        this.senderCryptoprofile = senderCryptoprofile;
    }

    @Override
    public String getSenderCryptoprofileType() {
        return senderCryptoprofileType;
    }

    public void setSenderCryptoprofileType(String senderCryptoprofileType) {
        this.senderCryptoprofileType = senderCryptoprofileType;
    }

    @Override
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Override
    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    @Override
    public String getSenderCertId() {
        return senderCertId;
    }

    public void setSenderCertId(String senderCertId) {
        this.senderCertId = senderCertId;
    }

    @Override
    public String getSenderSignImsi() {
        return senderSignImsi;
    }

    public void setSenderSignImsi(String senderSignImsi) {
        this.senderSignImsi = senderSignImsi;
    }

    @Override
    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    @Override
    public Long getChgCnt() {
        return chgCnt;
    }

    public void setChgCnt(Long chgCnt) {
        this.chgCnt = chgCnt;
    }

    @Override
    public Long $getAggregateVersion() {
        return null;
    }

    @Override
    public boolean $isBrokenLink() {
        return false;
    }

    @Override
    public boolean $isIncorrectCasted() {
        return false;
    }

    @Override
    public <T> T $getCalculated(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Long getSysLastHistVersion() {
        return null;
    }

}
