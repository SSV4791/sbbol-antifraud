package ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment;

import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SbpPaymentOperationGetImpl implements SbpPaymentOperationGet {

    private String objectId;
    private String requestId;
    private LocalDateTime timeStamp;
    private String epkId;
    private String digitalId;
    private String userGuid;
    private String tbCode;
    private String httpAccept;
    private String httpReferer;
    private String httpAcceptChars;
    private String httpAcceptEncoding;
    private String httpAcceptLanguage;
    private String ipAddress;
    private String userAgent;
    private String devicePrint;
    private String mobSdkData;
    private String channelIndicator;
    private String clientDefinedChannelIndicator;
    private LocalDateTime timeOfOccurrence;
    private String docId;
    private Integer docNumber;
    private LocalDate docDate;
    private Long amount;
    private String currency;
    private String accountNumber;
    private String otherAccName;
    private String otherBicCode;
    private String receiverInn;
    private String receiverBankName;
    private String receiverBankCountryCode;
    private String receiverBankCorrAcc;
    private String receiverBankId;
    private String idOperationOPKC;
    private String receiverDocument;
    private String receiverDocumentType;
    private String destination;
    private String receiverAccount;
    private String receiverPhoneNumber;
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
    private String privateIpAddress;
    private LocalDateTime firstSignTime;
    private String firstSignIp;
    private String firstSignLogin;
    private String firstSignCryptoprofile;
    private String firstSignCryptoprofileType;
    private String firstSignChannel;
    private String firstSignToken;
    private String firstSignType;
    private String firstSignImsi;
    private String firstSignCertId;
    private String firstSignPhone;
    private String firstSignEmail;
    private String firstSignSource;
    private LocalDateTime senderSignTime;
    private String senderIp;
    private String senderLogin;
    private String senderCryptoprofile;
    private String senderCryptoprofileType;
    private String senderSignChannel;
    private String senderToken;
    private String senderSignType;
    private String senderSignImsi;
    private String senderCertId;
    private String senderPhone;
    private String senderEmail;
    private String senderSource;
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
    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    @Override
    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
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
    public String getDevicePrint() {
        return devicePrint;
    }

    public void setDevicePrint(String devicePrint) {
        this.devicePrint = devicePrint;
    }

    @Override
    public String getMobSdkData() {
        return mobSdkData;
    }

    public void setMobSdkData(String mobSdkData) {
        this.mobSdkData = mobSdkData;
    }

    @Override
    public String getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(String channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    @Override
    public String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    @Override
    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
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
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getOtherAccName() {
        return otherAccName;
    }

    public void setOtherAccName(String otherAccName) {
        this.otherAccName = otherAccName;
    }

    @Override
    public String getOtherBicCode() {
        return otherBicCode;
    }

    public void setOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
    }

    @Override
    public String getReceiverInn() {
        return receiverInn;
    }

    public void setReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
    }

    @Override
    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    @Override
    public String getReceiverBankCountryCode() {
        return receiverBankCountryCode;
    }

    public void setReceiverBankCountryCode(String receiverBankCountryCode) {
        this.receiverBankCountryCode = receiverBankCountryCode;
    }

    @Override
    public String getReceiverBankCorrAcc() {
        return receiverBankCorrAcc;
    }

    public void setReceiverBankCorrAcc(String receiverBankCorrAcc) {
        this.receiverBankCorrAcc = receiverBankCorrAcc;
    }

    @Override
    public String getReceiverBankId() {
        return receiverBankId;
    }

    public void setReceiverBankId(String receiverBankId) {
        this.receiverBankId = receiverBankId;
    }

    @Override
    public String getIdOperationOPKC() {
        return idOperationOPKC;
    }

    public void setIdOperationOPKC(String idOperationOPKC) {
        this.idOperationOPKC = idOperationOPKC;
    }

    @Override
    public String getReceiverDocument() {
        return receiverDocument;
    }

    public void setReceiverDocument(String receiverDocument) {
        this.receiverDocument = receiverDocument;
    }

    @Override
    public String getReceiverDocumentType() {
        return receiverDocumentType;
    }

    public void setReceiverDocumentType(String receiverDocumentType) {
        this.receiverDocumentType = receiverDocumentType;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    @Override
    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    @Override
    public String getPayerFinancialName() {
        return payerFinancialName;
    }

    public void setPayerFinancialName(String payerFinancialName) {
        this.payerFinancialName = payerFinancialName;
    }

    @Override
    public String getPayerOsbNum() {
        return payerOsbNum;
    }

    public void setPayerOsbNum(String payerOsbNum) {
        this.payerOsbNum = payerOsbNum;
    }

    @Override
    public String getPayerVspNum() {
        return payerVspNum;
    }

    public void setPayerVspNum(String payerVspNum) {
        this.payerVspNum = payerVspNum;
    }

    @Override
    public String getPayerAccBalance() {
        return payerAccBalance;
    }

    public void setPayerAccBalance(String payerAccBalance) {
        this.payerAccBalance = payerAccBalance;
    }

    @Override
    public LocalDate getPayerAccCreateDate() {
        return payerAccCreateDate;
    }

    public void setPayerAccCreateDate(LocalDate payerAccCreateDate) {
        this.payerAccCreateDate = payerAccCreateDate;
    }

    @Override
    public String getPayerBic() {
        return payerBic;
    }

    public void setPayerBic(String payerBic) {
        this.payerBic = payerBic;
    }

    @Override
    public String getPayerDocumentNumber() {
        return payerDocumentNumber;
    }

    public void setPayerDocumentNumber(String payerDocumentNumber) {
        this.payerDocumentNumber = payerDocumentNumber;
    }

    @Override
    public String getPayerDocumentType() {
        return payerDocumentType;
    }

    public void setPayerDocumentType(String payerDocumentType) {
        this.payerDocumentType = payerDocumentType;
    }

    @Override
    public String getPayerSegment() {
        return payerSegment;
    }

    public void setPayerSegment(String payerSegment) {
        this.payerSegment = payerSegment;
    }

    @Override
    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    @Override
    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    @Override
    public LocalDateTime getFirstSignTime() {
        return firstSignTime;
    }

    public void setFirstSignTime(LocalDateTime firstSignTime) {
        this.firstSignTime = firstSignTime;
    }

    @Override
    public String getFirstSignIp() {
        return firstSignIp;
    }

    public void setFirstSignIp(String firstSignIp) {
        this.firstSignIp = firstSignIp;
    }

    @Override
    public String getFirstSignLogin() {
        return firstSignLogin;
    }

    public void setFirstSignLogin(String firstSignLogin) {
        this.firstSignLogin = firstSignLogin;
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
    public String getFirstSignChannel() {
        return firstSignChannel;
    }

    public void setFirstSignChannel(String firstSignChannel) {
        this.firstSignChannel = firstSignChannel;
    }

    @Override
    public String getFirstSignToken() {
        return firstSignToken;
    }

    public void setFirstSignToken(String firstSignToken) {
        this.firstSignToken = firstSignToken;
    }

    @Override
    public String getFirstSignType() {
        return firstSignType;
    }

    public void setFirstSignType(String firstSignType) {
        this.firstSignType = firstSignType;
    }

    @Override
    public String getFirstSignImsi() {
        return firstSignImsi;
    }

    public void setFirstSignImsi(String firstSignImsi) {
        this.firstSignImsi = firstSignImsi;
    }

    @Override
    public String getFirstSignCertId() {
        return firstSignCertId;
    }

    public void setFirstSignCertId(String firstSignCertId) {
        this.firstSignCertId = firstSignCertId;
    }

    @Override
    public String getFirstSignPhone() {
        return firstSignPhone;
    }

    public void setFirstSignPhone(String firstSignPhone) {
        this.firstSignPhone = firstSignPhone;
    }

    @Override
    public String getFirstSignEmail() {
        return firstSignEmail;
    }

    public void setFirstSignEmail(String firstSignEmail) {
        this.firstSignEmail = firstSignEmail;
    }

    @Override
    public String getFirstSignSource() {
        return firstSignSource;
    }

    public void setFirstSignSource(String firstSignSource) {
        this.firstSignSource = firstSignSource;
    }

    @Override
    public LocalDateTime getSenderSignTime() {
        return senderSignTime;
    }

    public void setSenderSignTime(LocalDateTime senderSignTime) {
        this.senderSignTime = senderSignTime;
    }

    @Override
    public String getSenderIp() {
        return senderIp;
    }

    public void setSenderIp(String senderIp) {
        this.senderIp = senderIp;
    }

    @Override
    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
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
    public String getSenderSignChannel() {
        return senderSignChannel;
    }

    public void setSenderSignChannel(String senderSignChannel) {
        this.senderSignChannel = senderSignChannel;
    }

    @Override
    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    @Override
    public String getSenderSignType() {
        return senderSignType;
    }

    public void setSenderSignType(String senderSignType) {
        this.senderSignType = senderSignType;
    }

    @Override
    public String getSenderSignImsi() {
        return senderSignImsi;
    }

    public void setSenderSignImsi(String senderSignImsi) {
        this.senderSignImsi = senderSignImsi;
    }

    @Override
    public String getSenderCertId() {
        return senderCertId;
    }

    public void setSenderCertId(String senderCertId) {
        this.senderCertId = senderCertId;
    }

    @Override
    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    @Override
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Override
    public String getSenderSource() {
        return senderSource;
    }

    public void setSenderSource(String senderSource) {
        this.senderSource = senderSource;
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

}
