package ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.ElectronicReceiptOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static ru.sberbank.pprb.sbbol.antifraud.api.DboOperation.ELECTRONIC_CHEQUE;

@Mapper(componentModel = "spring")
public abstract class ElectronicReceiptMapper implements CommonMapper<ElectronicReceiptOperationGet> {

    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String PRIVATE_IP_ADDRESS = "privateIpAddress";
    public static final String USER_GUID = "userGuid";
    public static final String DOC_NUMBER = "docNumber";
    public static final String DOC_DATE = "docDate";
    public static final String CODE_BIC = "codeBic";
    public static final String DESTINATION = "destination";
    public static final String PAYER_NAME = "payerName";
    public static final String PAYER_INN = "payerInn";
    public static final String FIRST_NAME = "firstName";
    public static final String SECOND_NAME = "secondName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String BIRTH_DAY = "birthDay";
    public static final String DUL_TYPE = "dulType";
    public static final String DUL_SERIE_NUMBER = "dulSerieNumber";
    public static final String DUL_WHO_ISSUE = "dulWhoIssue";
    public static final String DUL_DATE_ISSUE = "dulDateIssue";
    public static final String DUL_CODE_ISSUE = "dulCodeIssue";
    public static final String RECEIPT_DATE = "receiptDate";
    public static final String RECEIPT_TB_CODE = "receiptTbCode";
    public static final String RECEIPT_OSB_NUMBER = "receiptOsbNumber";
    public static final String RECEIPT_VSP_NUMBER = "receiptVspNumber";
    public static final String RECEIPT_PLACE_NAME = "receiptPlaceName";
    public static final String RECEIPT_PLACE_ADDRESS = "receiptPlaceAddress";
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

    private static final Map<String, Function<ElectronicReceiptOperationGet, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 61;

    static {
        Map<String, Function<ElectronicReceiptOperationGet, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, ElectronicReceiptOperationGet::getEpkId);
        criteriaMap.put(DIGITAL_ID, ElectronicReceiptOperationGet::getDigitalId);
        criteriaMap.put(PRIVATE_IP_ADDRESS, ElectronicReceiptOperationGet::getPrivateIpAddress);
        criteriaMap.put(USER_GUID, ElectronicReceiptOperationGet::getUserGuid);
        criteriaMap.put(DOC_NUMBER, ElectronicReceiptOperationGet::getDocNumber);
        criteriaMap.put(DOC_DATE, ElectronicReceiptOperationGet::getDocDate);
        criteriaMap.put(CODE_BIC, ElectronicReceiptOperationGet::getCodeBic);
        criteriaMap.put(DESTINATION, ElectronicReceiptOperationGet::getDestination);
        criteriaMap.put(PAYER_NAME, ElectronicReceiptOperationGet::getPayerName);
        criteriaMap.put(PAYER_INN, ElectronicReceiptOperationGet::getPayerInn);
        criteriaMap.put(FIRST_NAME, ElectronicReceiptOperationGet::getFirstName);
        criteriaMap.put(SECOND_NAME, ElectronicReceiptOperationGet::getSecondName);
        criteriaMap.put(MIDDLE_NAME, ElectronicReceiptOperationGet::getMiddleName);
        criteriaMap.put(BIRTH_DAY, ElectronicReceiptOperationGet::getBirthDay);
        criteriaMap.put(DUL_TYPE, ElectronicReceiptOperationGet::getDulType);
        criteriaMap.put(DUL_SERIE_NUMBER, ElectronicReceiptOperationGet::getDulSerieNumber);
        criteriaMap.put(DUL_WHO_ISSUE, ElectronicReceiptOperationGet::getDulWhoIssue);
        criteriaMap.put(DUL_DATE_ISSUE, ElectronicReceiptOperationGet::getDulDateIssue);
        criteriaMap.put(DUL_CODE_ISSUE, ElectronicReceiptOperationGet::getDulCodeIssue);
        criteriaMap.put(RECEIPT_DATE, ElectronicReceiptOperationGet::getReceiptDate);
        criteriaMap.put(RECEIPT_TB_CODE, ElectronicReceiptOperationGet::getReceiptTbCode);
        criteriaMap.put(RECEIPT_OSB_NUMBER, ElectronicReceiptOperationGet::getReceiptOsbNumber);
        criteriaMap.put(RECEIPT_VSP_NUMBER, ElectronicReceiptOperationGet::getReceiptVspNumber);
        criteriaMap.put(RECEIPT_PLACE_NAME, ElectronicReceiptOperationGet::getReceiptPlaceName);
        criteriaMap.put(RECEIPT_PLACE_ADDRESS, ElectronicReceiptOperationGet::getReceiptPlaceAddress);
        criteriaMap.put(FIRST_SIGN_TIME, ElectronicReceiptOperationGet::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, ElectronicReceiptOperationGet::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, ElectronicReceiptOperationGet::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, ElectronicReceiptOperationGet::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, ElectronicReceiptOperationGet::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, ElectronicReceiptOperationGet::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, ElectronicReceiptOperationGet::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, ElectronicReceiptOperationGet::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, ElectronicReceiptOperationGet::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, ElectronicReceiptOperationGet::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, ElectronicReceiptOperationGet::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, ElectronicReceiptOperationGet::getFirstSignEmail);
        criteriaMap.put(SECOND_SIGN_TIME, ElectronicReceiptOperationGet::getSecondSignTime);
        criteriaMap.put(SECOND_SIGN_IP, ElectronicReceiptOperationGet::getSecondSignIp);
        criteriaMap.put(SECOND_SIGN_LOGIN, ElectronicReceiptOperationGet::getSecondSignLogin);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE, ElectronicReceiptOperationGet::getSecondSignCryptoprofile);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, ElectronicReceiptOperationGet::getSecondSignCryptoprofileType);
        criteriaMap.put(SECOND_SIGN_CHANNEL, ElectronicReceiptOperationGet::getSecondSignChannel);
        criteriaMap.put(SECOND_SIGN_TOKEN, ElectronicReceiptOperationGet::getSecondSignToken);
        criteriaMap.put(SECOND_SIGN_TYPE, ElectronicReceiptOperationGet::getSecondSignType);
        criteriaMap.put(SECOND_SIGN_IMSI, ElectronicReceiptOperationGet::getSecondSignImsi);
        criteriaMap.put(SECOND_SIGN_CERT_ID, ElectronicReceiptOperationGet::getSecondSignCertId);
        criteriaMap.put(SECOND_SIGN_PHONE, ElectronicReceiptOperationGet::getSecondSignPhone);
        criteriaMap.put(SECOND_SIGN_EMAIL, ElectronicReceiptOperationGet::getSecondSignEmail);
        criteriaMap.put(SENDER_SIGN_TIME, ElectronicReceiptOperationGet::getSenderSignTime);
        criteriaMap.put(SENDER_IP, ElectronicReceiptOperationGet::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, ElectronicReceiptOperationGet::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, ElectronicReceiptOperationGet::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, ElectronicReceiptOperationGet::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, ElectronicReceiptOperationGet::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, ElectronicReceiptOperationGet::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, ElectronicReceiptOperationGet::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, ElectronicReceiptOperationGet::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, ElectronicReceiptOperationGet::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, ElectronicReceiptOperationGet::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, ElectronicReceiptOperationGet::getSenderEmail);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.id");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(PRIVATE_IP_ADDRESS, "Локальный IP сервера");
        descriptionMap.put(USER_GUID, "Уникальный идентификатор пользователя");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(DOC_DATE, "Дата документа");
        descriptionMap.put(CODE_BIC, "БИК банка");
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(PAYER_NAME, "Наименование отправителя");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
        descriptionMap.put(FIRST_NAME, "Имя");
        descriptionMap.put(SECOND_NAME, "Фамилия");
        descriptionMap.put(MIDDLE_NAME, "Отчество");
        descriptionMap.put(BIRTH_DAY, "Дата рождения");
        descriptionMap.put(DUL_TYPE, "Тип документа");
        descriptionMap.put(DUL_SERIE_NUMBER, "Серия и номер");
        descriptionMap.put(DUL_WHO_ISSUE, "Кем выдан");
        descriptionMap.put(DUL_DATE_ISSUE, "Дата выдачи");
        descriptionMap.put(DUL_CODE_ISSUE, "Код подразделения");
        descriptionMap.put(RECEIPT_DATE, "Дата получения ДС");
        descriptionMap.put(RECEIPT_TB_CODE, "Код ТБ");
        descriptionMap.put(RECEIPT_OSB_NUMBER, "Номер ОСБ");
        descriptionMap.put(RECEIPT_VSP_NUMBER, "Номер ВСП");
        descriptionMap.put(RECEIPT_PLACE_NAME, "Место выдачи, наименование");
        descriptionMap.put(RECEIPT_PLACE_ADDRESS, "Место выдачи, адрес");
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
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    public static final String CHANNEL_INDICATOR = "WEB";
    public static final String CLIENT_DEFINED_CHANNEL_INDICATOR = "PPRB_BROWSER";

    @Mapping(source = "timeStamp", target = "messageHeader.timeStamp")
    @Mapping(source = "docId", target = "identificationData.clientTransactionId")
    @Mapping(source = "tbCode", target = "identificationData.orgName")
    @Mapping(source = "requestId", target = "identificationData.requestId")
    @Mapping(source = "devicePrint", target = "deviceRequest.devicePrint")
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
    @Mapping(source = "accountNumber", target = "eventDataList.transactionData.myAccountData.accountNumber")
    public abstract AnalyzeRequest toAnalyzeRequest(ElectronicReceiptOperationGet dto);

    @AfterMapping
    protected void fillConstants(@MappingTarget AnalyzeRequest analyzeRequest) {
        if (analyzeRequest.getIdentificationData() == null) {
            analyzeRequest.setIdentificationData(new IdentificationData());
        }
        if (analyzeRequest.getEventDataList() == null) {
            analyzeRequest.setEventDataList(new EventData());
        }
        if (analyzeRequest.getEventDataList().getEventDataHeader() == null) {
            analyzeRequest.getEventDataList().setEventDataHeader(new EventDataHeader());
        }
        analyzeRequest.getIdentificationData().setDboOperation(ELECTRONIC_CHEQUE);
        analyzeRequest.setChannelIndicator(CHANNEL_INDICATOR);
        analyzeRequest.getEventDataList().getEventDataHeader().setEventType(ELECTRONIC_CHEQUE.getEventType());
        analyzeRequest.getEventDataList().getEventDataHeader().setEventDescription(ELECTRONIC_CHEQUE.getEventDescription());
        analyzeRequest.getEventDataList().getEventDataHeader().setClientDefinedEventType(ELECTRONIC_CHEQUE.getClientDefinedEventType(null));
        analyzeRequest.setClientDefinedChannelIndicator(CLIENT_DEFINED_CHANNEL_INDICATOR);
    }

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, ElectronicReceiptOperationGet operationGet) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, operationGet, CRITERIA_MAP, DESCRIPTION_MAP);
    }

}
