package ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static ru.sberbank.pprb.sbbol.antifraud.api.DboOperation.PAYMENT_DT_0401060;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper implements CommonMapper<PaymentOperationGet> {

    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String DOC_NUMBER = "docNumber";
    public static final String DOC_DATE = "docDate";
    public static final String RECEIVER_INN = "receiverInn";
    public static final String DESTINATION = "destination";
    public static final String PAYER_INN = "payerInn";
    public static final String PRIVATE_IP_ADDRESS = "privateIpAddress";
    public static final String FIRST_SIGN_TIME = "firstSignTime";
    public static final String FIRST_SIGN_IP = "firstSignIp";
    public static final String FIRST_SIGN_LOGIN = "firstSignLogin";
    public static final String FIRST_SIGN_CRYPTOPROFILE = "firstSignCryptoprofile";
    public static final String FIRST_SIGN_CRYPTOPROFILE_TYPE = "firstSignCryptoprofileType";
    public static final String FIRST_SIGN_CHANNEL = "firstSignChannel";
    public static final String FIRST_SIGN_TOKEN = "firstSignToken";
    public static final String FIRST_SIGN_TYPE = "firstSignType";
    public static final String FIRST_SIGN_IMSI = "firstSignImsi";
    public static final String FIRST_SIGN_CERT_ID = "firstSignCertId";
    public static final String FIRST_SIGN_PHONE = "firstSignPhone";
    public static final String FIRST_SIGN_EMAIL = "firstSignEmail";
    public static final String FIRST_SIGN_SOURCE = "firstSignSource";
    public static final String SECOND_SIGN_TIME = "secondSignTime";
    public static final String SECOND_SIGN_IP = "secondSignIp";
    public static final String SECOND_SIGN_LOGIN = "secondSignLogin";
    public static final String SECOND_SIGN_CRYPTOPROFILE = "secondSignCryptoprofile";
    public static final String SECOND_SIGN_CRYPTOPROFILE_TYPE = "secondSignCryptoprofileType";
    public static final String SECOND_SIGN_CHANNEL = "secondSignChannel";
    public static final String SECOND_SIGN_TOKEN = "secondSignToken";
    public static final String SECOND_SIGN_TYPE = "secondSignType";
    public static final String SECOND_SIGN_IMSI = "secondSignImsi";
    public static final String SECOND_SIGN_CERT_ID = "secondSignCertId";
    public static final String SECOND_SIGN_PHONE = "secondSignPhone";
    public static final String SECOND_SIGN_EMAIL = "secondSignEmail";
    public static final String SECOND_SIGN_SOURCE = "secondSignSource";
    public static final String THIRD_SIGN_TIME = "thirdSignTime";
    public static final String THIRD_SIGN_IP = "thirdSignIp";
    public static final String THIRD_SIGN_LOGIN = "thirdSignLogin";
    public static final String THIRD_SIGN_CRYPTOPROFILE = "thirdSignCryptoprofile";
    public static final String THIRD_SIGN_CRYPTOPROFILE_TYPE = "thirdSignCryptoprofileType";
    public static final String THIRD_SIGN_CHANNEL = "thirdSignChannel";
    public static final String THIRD_SIGN_TOKEN = "thirdSignToken";
    public static final String THIRD_SIGN_TYPE = "thirdSignType";
    public static final String THIRD_SIGN_IMSI = "thirdSignImsi";
    public static final String THIRD_SIGN_CERT_ID = "thirdSignCertId";
    public static final String THIRD_SIGN_PHONE = "thirdSignPhone";
    public static final String THIRD_SIGN_EMAIL = "thirdSignEmail";
    public static final String THIRD_SIGN_SOURCE = "thirdSignSource";
    public static final String SENDER_SIGN_TIME = "senderSignTime";
    public static final String SENDER_IP = "senderIp";
    public static final String SENDER_LOGIN = "senderLogin";
    public static final String SENDER_CRYPTOPROFILE = "senderCryptoprofile";
    public static final String SENDER_CRYPTOPROFILE_TYPE = "senderCryptoprofileType";
    public static final String SENDER_SIGN_CHANNEL = "senderSignChannel";
    public static final String SENDER_TOKEN = "senderToken";
    public static final String SENDER_SIGN_TYPE = "senderSignType";
    public static final String SENDER_SIGN_IMSI = "senderSignImsi";
    public static final String SENDER_CERT_ID = "senderCertId";
    public static final String SENDER_PHONE = "senderPhone";
    public static final String SENDER_EMAIL = "senderEmail";
    public static final String SENDER_SOURCE = "senderSource";

    private static final Map<String, Function<PaymentOperationGet, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 60;

    static {
        Map<String, Function<PaymentOperationGet, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, PaymentOperationGet::getEpkId);
        criteriaMap.put(DIGITAL_ID, PaymentOperationGet::getDigitalId);
        criteriaMap.put(DOC_NUMBER, PaymentOperationGet::getDocNumber);
        criteriaMap.put(DOC_DATE, PaymentOperationGet::getDocDate);
        criteriaMap.put(RECEIVER_INN, PaymentOperationGet::getReceiverInn);
        criteriaMap.put(DESTINATION, PaymentOperationGet::getDestination);
        criteriaMap.put(PAYER_INN, PaymentOperationGet::getPayerInn);
        criteriaMap.put(PRIVATE_IP_ADDRESS, PaymentOperationGet::getPrivateIpAddress);
        criteriaMap.put(FIRST_SIGN_TIME, PaymentOperationGet::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, PaymentOperationGet::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, PaymentOperationGet::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, PaymentOperationGet::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, PaymentOperationGet::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, PaymentOperationGet::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, PaymentOperationGet::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, PaymentOperationGet::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, PaymentOperationGet::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, PaymentOperationGet::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, PaymentOperationGet::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, PaymentOperationGet::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, PaymentOperationGet::getFirstSignSource);
        criteriaMap.put(SECOND_SIGN_TIME, PaymentOperationGet::getSecondSignTime);
        criteriaMap.put(SECOND_SIGN_IP, PaymentOperationGet::getSecondSignIp);
        criteriaMap.put(SECOND_SIGN_LOGIN, PaymentOperationGet::getSecondSignLogin);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE, PaymentOperationGet::getSecondSignCryptoprofile);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, PaymentOperationGet::getSecondSignCryptoprofileType);
        criteriaMap.put(SECOND_SIGN_CHANNEL, PaymentOperationGet::getSecondSignChannel);
        criteriaMap.put(SECOND_SIGN_TOKEN, PaymentOperationGet::getSecondSignToken);
        criteriaMap.put(SECOND_SIGN_TYPE, PaymentOperationGet::getSecondSignType);
        criteriaMap.put(SECOND_SIGN_IMSI, PaymentOperationGet::getSecondSignImsi);
        criteriaMap.put(SECOND_SIGN_CERT_ID, PaymentOperationGet::getSecondSignCertId);
        criteriaMap.put(SECOND_SIGN_PHONE, PaymentOperationGet::getSecondSignPhone);
        criteriaMap.put(SECOND_SIGN_EMAIL, PaymentOperationGet::getSecondSignEmail);
        criteriaMap.put(SECOND_SIGN_SOURCE, PaymentOperationGet::getSecondSignSource);
        criteriaMap.put(THIRD_SIGN_TIME, PaymentOperationGet::getThirdSignTime);
        criteriaMap.put(THIRD_SIGN_IP, PaymentOperationGet::getThirdSignIp);
        criteriaMap.put(THIRD_SIGN_LOGIN, PaymentOperationGet::getThirdSignLogin);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE, PaymentOperationGet::getThirdSignCryptoprofile);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, PaymentOperationGet::getThirdSignCryptoprofileType);
        criteriaMap.put(THIRD_SIGN_CHANNEL, PaymentOperationGet::getThirdSignChannel);
        criteriaMap.put(THIRD_SIGN_TOKEN, PaymentOperationGet::getThirdSignToken);
        criteriaMap.put(THIRD_SIGN_TYPE, PaymentOperationGet::getThirdSignType);
        criteriaMap.put(THIRD_SIGN_IMSI, PaymentOperationGet::getThirdSignImsi);
        criteriaMap.put(THIRD_SIGN_CERT_ID, PaymentOperationGet::getThirdSignCertId);
        criteriaMap.put(THIRD_SIGN_PHONE, PaymentOperationGet::getThirdSignPhone);
        criteriaMap.put(THIRD_SIGN_EMAIL, PaymentOperationGet::getThirdSignEmail);
        criteriaMap.put(THIRD_SIGN_SOURCE, PaymentOperationGet::getThirdSignSource);
        criteriaMap.put(SENDER_SIGN_TIME, PaymentOperationGet::getSenderSignTime);
        criteriaMap.put(SENDER_IP, PaymentOperationGet::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, PaymentOperationGet::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, PaymentOperationGet::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, PaymentOperationGet::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, PaymentOperationGet::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, PaymentOperationGet::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, PaymentOperationGet::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, PaymentOperationGet::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, PaymentOperationGet::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, PaymentOperationGet::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, PaymentOperationGet::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, PaymentOperationGet::getSenderSource);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.id");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(DOC_DATE, "Дата документа");
        descriptionMap.put(RECEIVER_INN, "ИНН получателя");
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
        descriptionMap.put(PRIVATE_IP_ADDRESS, "Локальный IP адрес");
        descriptionMap.put(FIRST_SIGN_TIME, "1-я подпись Время подписи");
        descriptionMap.put(FIRST_SIGN_IP, "1-я подпись IP адрес");
        descriptionMap.put(FIRST_SIGN_LOGIN, "1-я подпись Логин");
        descriptionMap.put(FIRST_SIGN_CRYPTOPROFILE, "1-я подпись Наименование криптопрофиля");
        descriptionMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, "1-я подпись Тип криптопрофиля");
        descriptionMap.put(FIRST_SIGN_CHANNEL, "1-я подпись Канал подписи");
        descriptionMap.put(FIRST_SIGN_TOKEN, "1-я подпись Данные Токена");
        descriptionMap.put(FIRST_SIGN_TYPE, "1-я подпись Тип подписи");
        descriptionMap.put(FIRST_SIGN_IMSI, "1-я подпись IMSI");
        descriptionMap.put(FIRST_SIGN_CERT_ID, "1-я подпись Идентификатор сертификата");
        descriptionMap.put(FIRST_SIGN_PHONE, "1-я подпись Номер телефона");
        descriptionMap.put(FIRST_SIGN_EMAIL, "1-я подпись Адрес электронной почты");
        descriptionMap.put(FIRST_SIGN_SOURCE, "1-я подпись Канал");
        descriptionMap.put(SECOND_SIGN_TIME, "2-я подпись Время подписи");
        descriptionMap.put(SECOND_SIGN_IP, "2-я подпись IP адрес");
        descriptionMap.put(SECOND_SIGN_LOGIN, "2-я подпись Логин");
        descriptionMap.put(SECOND_SIGN_CRYPTOPROFILE, "2-я подпись Наименование криптопрофиля");
        descriptionMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, "2-я подпись Тип криптопрофиля");
        descriptionMap.put(SECOND_SIGN_CHANNEL, "2-я подпись Канал подписи");
        descriptionMap.put(SECOND_SIGN_TOKEN, "2-я подпись Данные Токена");
        descriptionMap.put(SECOND_SIGN_TYPE, "2-я подпись Тип подписи");
        descriptionMap.put(SECOND_SIGN_IMSI, "2-я подпись IMSI");
        descriptionMap.put(SECOND_SIGN_CERT_ID, "2-я подпись Идентификатор сертификата");
        descriptionMap.put(SECOND_SIGN_PHONE, "2-я подпись Номер телефона");
        descriptionMap.put(SECOND_SIGN_EMAIL, "2-я подпись Адрес электронной почты");
        descriptionMap.put(SECOND_SIGN_SOURCE, "2-я подпись Канал");
        descriptionMap.put(THIRD_SIGN_TIME, "3-я подпись Время подписи");
        descriptionMap.put(THIRD_SIGN_IP, "3-я подпись IP адрес");
        descriptionMap.put(THIRD_SIGN_LOGIN, "3-я подпись Логин");
        descriptionMap.put(THIRD_SIGN_CRYPTOPROFILE, "3-я подпись Наименование криптопрофиля");
        descriptionMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, "3-я подпись Тип криптопрофиля");
        descriptionMap.put(THIRD_SIGN_CHANNEL, "3-я подпись Канал подписи");
        descriptionMap.put(THIRD_SIGN_TOKEN, "3-я подпись Данные Токена");
        descriptionMap.put(THIRD_SIGN_TYPE, "3-я подпись Тип подписи");
        descriptionMap.put(THIRD_SIGN_IMSI, "3-я подпись IMSI");
        descriptionMap.put(THIRD_SIGN_CERT_ID, "3-я подпись Идентификатор сертификата");
        descriptionMap.put(THIRD_SIGN_PHONE, "3-я подпись Номер телефона");
        descriptionMap.put(THIRD_SIGN_EMAIL, "3-я подпись Адрес электронной почты");
        descriptionMap.put(THIRD_SIGN_SOURCE, "3-я подпись Канал");
        descriptionMap.put(SENDER_SIGN_TIME, "Отправивший Время подписи");
        descriptionMap.put(SENDER_IP, "Отправивший IP адрес");
        descriptionMap.put(SENDER_LOGIN, "Отправивший Логин");
        descriptionMap.put(SENDER_CRYPTOPROFILE, "Отправивший Наименование криптопрофиля");
        descriptionMap.put(SENDER_CRYPTOPROFILE_TYPE, "Отправивший Тип криптопрофиля");
        descriptionMap.put(SENDER_SIGN_CHANNEL, "Отправивший Канал подписи");
        descriptionMap.put(SENDER_TOKEN, "Отправивший Данные Токена");
        descriptionMap.put(SENDER_SIGN_TYPE, "Отправивший Тип подписи");
        descriptionMap.put(SENDER_SIGN_IMSI, "Отправивший IMSI");
        descriptionMap.put(SENDER_CERT_ID, "Отправивший Идентификатор сертификата");
        descriptionMap.put(SENDER_PHONE, "Отправивший Номер телефона");
        descriptionMap.put(SENDER_EMAIL, "Отправивший Адрес электронной почты");
        descriptionMap.put(SENDER_SOURCE, "Отправивший Канал");
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    @Mapping(source = "timeStamp", target = "messageHeader.timeStamp")
    @Mapping(source = "docId", target = "identificationData.clientTransactionId")
    @Mapping(source = "tbCode", target = "identificationData.orgName")
    @Mapping(source = "requestId", target = "identificationData.requestId")
    @Mapping(source = "devicePrint", target = "deviceRequest.devicePrint")
    @Mapping(source = "mobSdkData", target = "deviceRequest.mobSdkData")
    @Mapping(source = "httpAccept", target = "deviceRequest.httpAccept")
    @Mapping(source = "httpAcceptChars", target = "deviceRequest.httpAcceptChars")
    @Mapping(source = "httpAcceptEncoding", target = "deviceRequest.httpAcceptEncoding")
    @Mapping(source = "httpAcceptLanguage", target = "deviceRequest.httpAcceptLanguage")
    @Mapping(source = "httpReferer", target = "deviceRequest.httpReferrer")
    @Mapping(source = "ipAddress", target = "deviceRequest.ipAddress")
    @Mapping(source = "userAgent", target = "deviceRequest.userAgent")
    @Mapping(source = "timeOfOccurrence", target = "eventDataList.eventDataHeader.timeOfOccurrence")
    @Mapping(source = "amount", target = "eventDataList.transactionData.amount.sum")
    @Mapping(source = "currency", target = "eventDataList.transactionData.amount.currency")
    @Mapping(source = "executionSpeed", target = "eventDataList.transactionData.executionSpeed")
    @Mapping(source = "otherAccBankType", target = "eventDataList.transactionData.otherAccountBankType")
    @Mapping(source = "accountNumber", target = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(source = "otherAccName", target = "eventDataList.transactionData.otherAccountData.accountName")
    @Mapping(source = "balAccNumber", target = "eventDataList.transactionData.otherAccountData.accountNumber")
    @Mapping(source = "otherBicCode", target = "eventDataList.transactionData.otherAccountData.routingCode")
    @Mapping(source = "otherAccOwnershipType", target = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType")
    @Mapping(source = "otherAccType", target = "eventDataList.transactionData.otherAccountData.otherAccountType")
    @Mapping(source = "transferMediumType", target = "eventDataList.transactionData.otherAccountData.transferMediumType")
    public abstract AnalyzeRequest toAnalyzeRequest(PaymentOperationGet dto);

    @AfterMapping
    protected void fillConstants(@MappingTarget AnalyzeRequest analyzeRequest) {
        if (analyzeRequest.getEventDataList() == null) {
            analyzeRequest.setEventDataList(new EventData());
        }
        if (analyzeRequest.getEventDataList().getEventDataHeader() == null) {
            analyzeRequest.getEventDataList().setEventDataHeader(new EventDataHeader());
        }
        if (analyzeRequest.getIdentificationData() == null) {
            analyzeRequest.setIdentificationData(new IdentificationData());
        }
        analyzeRequest.getIdentificationData().setUserName("");
        analyzeRequest.getIdentificationData().setDboOperation(PAYMENT_DT_0401060);
        analyzeRequest.getEventDataList().getEventDataHeader().setEventType(PAYMENT_DT_0401060.getEventType());
        analyzeRequest.getEventDataList().getEventDataHeader().setEventDescription(PAYMENT_DT_0401060.getEventDescription());
        analyzeRequest.getEventDataList().getEventDataHeader().setClientDefinedEventType(PAYMENT_DT_0401060.getClientDefinedEventType(analyzeRequest.getChannelIndicator()));
    }

    @AfterMapping
    public void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, PaymentOperationGet operationGet) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, operationGet, CRITERIA_MAP, DESCRIPTION_MAP);
    }

}
