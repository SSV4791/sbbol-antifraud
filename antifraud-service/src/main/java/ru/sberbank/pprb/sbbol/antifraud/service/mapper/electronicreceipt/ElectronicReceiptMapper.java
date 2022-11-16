package ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mapper(componentModel = "spring", imports = {DboOperation.class, ChannelIndicator.class, ClientDefinedChannelIndicator.class})
public abstract class ElectronicReceiptMapper implements CommonMapper<ElectronicReceipt> {

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
    public static final String PAYER_KPP = "payerKpp";
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

    private static final Map<String, Function<ElectronicReceipt, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 62;

    static {
        Map<String, Function<ElectronicReceipt, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, ElectronicReceipt::getEpkId);
        criteriaMap.put(DIGITAL_ID, ElectronicReceipt::getDigitalId);
        criteriaMap.put(PRIVATE_IP_ADDRESS, ElectronicReceipt::getPrivateIpAddress);
        criteriaMap.put(USER_GUID, ElectronicReceipt::getUserGuid);
        criteriaMap.put(DOC_NUMBER, ElectronicReceipt::getDocNumber);
        criteriaMap.put(DOC_DATE, ElectronicReceipt::getDocDate);
        criteriaMap.put(CODE_BIC, ElectronicReceipt::getCodeBic);
        criteriaMap.put(DESTINATION, ElectronicReceipt::getDestination);
        criteriaMap.put(PAYER_NAME, ElectronicReceipt::getPayerName);
        criteriaMap.put(PAYER_INN, ElectronicReceipt::getPayerInn);
        criteriaMap.put(PAYER_KPP, ElectronicReceipt::getPayerKpp);
        criteriaMap.put(FIRST_NAME, ElectronicReceipt::getFirstName);
        criteriaMap.put(SECOND_NAME, ElectronicReceipt::getSecondName);
        criteriaMap.put(MIDDLE_NAME, ElectronicReceipt::getMiddleName);
        criteriaMap.put(BIRTH_DAY, ElectronicReceipt::getBirthDay);
        criteriaMap.put(DUL_TYPE, ElectronicReceipt::getDulType);
        criteriaMap.put(DUL_SERIE_NUMBER, ElectronicReceipt::getDulSerieNumber);
        criteriaMap.put(DUL_WHO_ISSUE, ElectronicReceipt::getDulWhoIssue);
        criteriaMap.put(DUL_DATE_ISSUE, ElectronicReceipt::getDulDateIssue);
        criteriaMap.put(DUL_CODE_ISSUE, ElectronicReceipt::getDulCodeIssue);
        criteriaMap.put(RECEIPT_DATE, ElectronicReceipt::getReceiptDate);
        criteriaMap.put(RECEIPT_TB_CODE, ElectronicReceipt::getReceiptTbCode);
        criteriaMap.put(RECEIPT_OSB_NUMBER, ElectronicReceipt::getReceiptOsbNumber);
        criteriaMap.put(RECEIPT_VSP_NUMBER, ElectronicReceipt::getReceiptVspNumber);
        criteriaMap.put(RECEIPT_PLACE_NAME, ElectronicReceipt::getReceiptPlaceName);
        criteriaMap.put(RECEIPT_PLACE_ADDRESS, ElectronicReceipt::getReceiptPlaceAddress);
        criteriaMap.put(FIRST_SIGN_TIME, ElectronicReceipt::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, ElectronicReceipt::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, ElectronicReceipt::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, ElectronicReceipt::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, ElectronicReceipt::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, ElectronicReceipt::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, ElectronicReceipt::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, ElectronicReceipt::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, ElectronicReceipt::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, ElectronicReceipt::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, ElectronicReceipt::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, ElectronicReceipt::getFirstSignEmail);
        criteriaMap.put(SECOND_SIGN_TIME, ElectronicReceipt::getSecondSignTime);
        criteriaMap.put(SECOND_SIGN_IP, ElectronicReceipt::getSecondSignIp);
        criteriaMap.put(SECOND_SIGN_LOGIN, ElectronicReceipt::getSecondSignLogin);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE, ElectronicReceipt::getSecondSignCryptoprofile);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, ElectronicReceipt::getSecondSignCryptoprofileType);
        criteriaMap.put(SECOND_SIGN_CHANNEL, ElectronicReceipt::getSecondSignChannel);
        criteriaMap.put(SECOND_SIGN_TOKEN, ElectronicReceipt::getSecondSignToken);
        criteriaMap.put(SECOND_SIGN_TYPE, ElectronicReceipt::getSecondSignType);
        criteriaMap.put(SECOND_SIGN_IMSI, ElectronicReceipt::getSecondSignImsi);
        criteriaMap.put(SECOND_SIGN_CERT_ID, ElectronicReceipt::getSecondSignCertId);
        criteriaMap.put(SECOND_SIGN_PHONE, ElectronicReceipt::getSecondSignPhone);
        criteriaMap.put(SECOND_SIGN_EMAIL, ElectronicReceipt::getSecondSignEmail);
        criteriaMap.put(SENDER_SIGN_TIME, ElectronicReceipt::getSenderSignTime);
        criteriaMap.put(SENDER_IP, ElectronicReceipt::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, ElectronicReceipt::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, ElectronicReceipt::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, ElectronicReceipt::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, ElectronicReceipt::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, ElectronicReceipt::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, ElectronicReceipt::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, ElectronicReceipt::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, ElectronicReceipt::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, ElectronicReceipt::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, ElectronicReceipt::getSenderEmail);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.id");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(PRIVATE_IP_ADDRESS, "Локальный IP адрес");
        descriptionMap.put(USER_GUID, "Уникальный идентификатор пользователя");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(DOC_DATE, "Дата платежного документа");
        descriptionMap.put(CODE_BIC, "БИК");
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(PAYER_NAME, "Наименование клиента");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
        descriptionMap.put(PAYER_KPP, "КПП");
        descriptionMap.put(FIRST_NAME, "Имя");
        descriptionMap.put(SECOND_NAME, "Фамилия");
        descriptionMap.put(MIDDLE_NAME, "Отчество");
        descriptionMap.put(BIRTH_DAY, "Дата рождения");
        descriptionMap.put(DUL_TYPE, "Тип ДУЛ");
        descriptionMap.put(DUL_SERIE_NUMBER, "Серия и номер ДУЛ");
        descriptionMap.put(DUL_WHO_ISSUE, "Кем выдан ДУЛ");
        descriptionMap.put(DUL_DATE_ISSUE, "Дата выдачи ДУЛ");
        descriptionMap.put(DUL_CODE_ISSUE, "Код подразделения");
        descriptionMap.put(RECEIPT_DATE, "Дата получения");
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(source = "sign.signTime", target = "eventTime")
    @Mapping(source = "orgGuid", target = "epkId")
    @Mapping(source = "sign.userGuid", target = "userGuid")
    @Mapping(source = "document.payer.tbCode", target = "tbCode")
    @Mapping(source = "deviceRequest.httpAccept", target = "httpAccept")
    @Mapping(source = "deviceRequest.httpReferer", target = "httpReferer")
    @Mapping(source = "deviceRequest.httpAcceptChars", target = "httpAcceptChars")
    @Mapping(source = "deviceRequest.httpAcceptEncoding", target = "httpAcceptEncoding")
    @Mapping(source = "deviceRequest.httpAcceptLanguage", target = "httpAcceptLanguage")
    @Mapping(source = "deviceRequest.ipAddress", target = "ipAddress")
    @Mapping(source = "deviceRequest.userAgent", target = "userAgent")
    @Mapping(source = "deviceRequest.devicePrint", target = "devicePrint")
    @Mapping(source = "document.id", target = "docId")
    @Mapping(source = "document.number", target = "docNumber")
    @Mapping(source = "document.date", target = "docDate")
    @Mapping(source = "document.amount", target = "amount")
    @Mapping(source = "document.currency", target = "currency")
    @Mapping(source = "document.payer.accountNumber", target = "accountNumber")
    @Mapping(source = "document.payer.codeBic", target = "codeBic")
    @Mapping(source = "document.destination", target = "destination")
    @Mapping(source = "document.payer.name", target = "payerName")
    @Mapping(source = "document.payer.inn", target = "payerInn")
    @Mapping(source = "document.payer.kpp", target = "payerKpp")
    @Mapping(source = "document.receiver.firstName", target = "firstName")
    @Mapping(source = "document.receiver.secondName", target = "secondName")
    @Mapping(source = "document.receiver.middleName", target = "middleName")
    @Mapping(source = "document.receiver.birthDay", target = "birthDay")
    @Mapping(source = "document.receiver.dulType", target = "dulType")
    @Mapping(source = "document.receiver.dulSerieNumber", target = "dulSerieNumber")
    @Mapping(source = "document.receiver.dulWhoIssue", target = "dulWhoIssue")
    @Mapping(source = "document.receiver.dulDateIssue", target = "dulDateIssue")
    @Mapping(source = "document.receiver.dulCodeIssue", target = "dulCodeIssue")
    @Mapping(source = "document.receipt.receiptDate", target = "receiptDate")
    @Mapping(source = "document.receipt.receiptTbCode", target = "receiptTbCode")
    @Mapping(source = "document.receipt.receiptOsbNumber", target = "receiptOsbNumber")
    @Mapping(source = "document.receipt.receiptVspNumber", target = "receiptVspNumber")
    @Mapping(source = "document.receipt.receiptPlaceName", target = "receiptPlaceName")
    @Mapping(source = "document.receipt.receiptPlaceAddress", target = "receiptPlaceAddress")
    @Mapping(source = "sign.signTime", target = "senderSignTime")
    @Mapping(source = "sign.signIpAddress", target = "senderIp")
    @Mapping(source = "sign.signLogin", target = "senderLogin")
    @Mapping(source = "sign.signCryptoprofile", target = "senderCryptoprofile")
    @Mapping(source = "sign.signCryptoprofileType", target = "senderCryptoprofileType")
    @Mapping(source = "sign.signChannel", target = "senderSignChannel")
    @Mapping(source = "sign.signToken", target = "senderToken")
    @Mapping(source = "sign.signType", target = "senderSignType")
    @Mapping(source = "sign.signImsi", target = "senderSignImsi")
    @Mapping(source = "sign.signCertId", target = "senderCertId")
    @Mapping(source = "sign.signPhone", target = "senderPhone")
    @Mapping(source = "sign.signEmail", target = "senderEmail")
    @Mapping(target = "timeOfOccurrence", ignore = true)
    @Mapping(target = "firstSignTime", ignore = true)
    @Mapping(target = "firstSignIp", ignore = true)
    @Mapping(target = "firstSignLogin", ignore = true)
    @Mapping(target = "firstSignCryptoprofile", ignore = true)
    @Mapping(target = "firstSignCryptoprofileType", ignore = true)
    @Mapping(target = "firstSignChannel", ignore = true)
    @Mapping(target = "firstSignToken", ignore = true)
    @Mapping(target = "firstSignType", ignore = true)
    @Mapping(target = "firstSignImsi", ignore = true)
    @Mapping(target = "firstSignCertId", ignore = true)
    @Mapping(target = "firstSignPhone", ignore = true)
    @Mapping(target = "firstSignEmail", ignore = true)
    @Mapping(target = "secondSignTime", ignore = true)
    @Mapping(target = "secondSignIp", ignore = true)
    @Mapping(target = "secondSignLogin", ignore = true)
    @Mapping(target = "secondSignCryptoprofile", ignore = true)
    @Mapping(target = "secondSignCryptoprofileType", ignore = true)
    @Mapping(target = "secondSignChannel", ignore = true)
    @Mapping(target = "secondSignToken", ignore = true)
    @Mapping(target = "secondSignType", ignore = true)
    @Mapping(target = "secondSignImsi", ignore = true)
    @Mapping(target = "secondSignCertId", ignore = true)
    @Mapping(target = "secondSignPhone", ignore = true)
    @Mapping(target = "secondSignEmail", ignore = true)
    public abstract void updateFromDto(ElectronicReceiptOperation dto, @MappingTarget ElectronicReceipt entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", expression = "java(generateRequestId())")
    @Mapping(source = "sign.signTime", target = "eventTime")
    @Mapping(source = "orgGuid", target = "epkId")
    @Mapping(source = "sign.userGuid", target = "userGuid")
    @Mapping(source = "document.payer.tbCode", target = "tbCode")
    @Mapping(source = "deviceRequest.httpAccept", target = "httpAccept")
    @Mapping(source = "deviceRequest.httpReferer", target = "httpReferer")
    @Mapping(source = "deviceRequest.httpAcceptChars", target = "httpAcceptChars")
    @Mapping(source = "deviceRequest.httpAcceptEncoding", target = "httpAcceptEncoding")
    @Mapping(source = "deviceRequest.httpAcceptLanguage", target = "httpAcceptLanguage")
    @Mapping(source = "deviceRequest.ipAddress", target = "ipAddress")
    @Mapping(source = "deviceRequest.userAgent", target = "userAgent")
    @Mapping(source = "deviceRequest.devicePrint", target = "devicePrint")
    @Mapping(source = "document.id", target = "docId")
    @Mapping(source = "document.number", target = "docNumber")
    @Mapping(source = "document.date", target = "docDate")
    @Mapping(source = "document.amount", target = "amount")
    @Mapping(source = "document.currency", target = "currency")
    @Mapping(source = "document.payer.accountNumber", target = "accountNumber")
    @Mapping(source = "document.payer.codeBic", target = "codeBic")
    @Mapping(source = "document.destination", target = "destination")
    @Mapping(source = "document.payer.name", target = "payerName")
    @Mapping(source = "document.payer.inn", target = "payerInn")
    @Mapping(source = "document.payer.kpp", target = "payerKpp")
    @Mapping(source = "document.receiver.firstName", target = "firstName")
    @Mapping(source = "document.receiver.secondName", target = "secondName")
    @Mapping(source = "document.receiver.middleName", target = "middleName")
    @Mapping(source = "document.receiver.birthDay", target = "birthDay")
    @Mapping(source = "document.receiver.dulType", target = "dulType")
    @Mapping(source = "document.receiver.dulSerieNumber", target = "dulSerieNumber")
    @Mapping(source = "document.receiver.dulWhoIssue", target = "dulWhoIssue")
    @Mapping(source = "document.receiver.dulDateIssue", target = "dulDateIssue")
    @Mapping(source = "document.receiver.dulCodeIssue", target = "dulCodeIssue")
    @Mapping(source = "document.receipt.receiptDate", target = "receiptDate")
    @Mapping(source = "document.receipt.receiptTbCode", target = "receiptTbCode")
    @Mapping(source = "document.receipt.receiptOsbNumber", target = "receiptOsbNumber")
    @Mapping(source = "document.receipt.receiptVspNumber", target = "receiptVspNumber")
    @Mapping(source = "document.receipt.receiptPlaceName", target = "receiptPlaceName")
    @Mapping(source = "document.receipt.receiptPlaceAddress", target = "receiptPlaceAddress")
    @Mapping(source = "sign.signTime", target = "senderSignTime")
    @Mapping(source = "sign.signIpAddress", target = "senderIp")
    @Mapping(source = "sign.signLogin", target = "senderLogin")
    @Mapping(source = "sign.signCryptoprofile", target = "senderCryptoprofile")
    @Mapping(source = "sign.signCryptoprofileType", target = "senderCryptoprofileType")
    @Mapping(source = "sign.signChannel", target = "senderSignChannel")
    @Mapping(source = "sign.signToken", target = "senderToken")
    @Mapping(source = "sign.signType", target = "senderSignType")
    @Mapping(source = "sign.signImsi", target = "senderSignImsi")
    @Mapping(source = "sign.signCertId", target = "senderCertId")
    @Mapping(source = "sign.signPhone", target = "senderPhone")
    @Mapping(source = "sign.signEmail", target = "senderEmail")
    @Mapping(target = "timeOfOccurrence", ignore = true)
    @Mapping(target = "firstSignTime", ignore = true)
    @Mapping(target = "firstSignIp", ignore = true)
    @Mapping(target = "firstSignLogin", ignore = true)
    @Mapping(target = "firstSignCryptoprofile", ignore = true)
    @Mapping(target = "firstSignCryptoprofileType", ignore = true)
    @Mapping(target = "firstSignChannel", ignore = true)
    @Mapping(target = "firstSignToken", ignore = true)
    @Mapping(target = "firstSignType", ignore = true)
    @Mapping(target = "firstSignImsi", ignore = true)
    @Mapping(target = "firstSignCertId", ignore = true)
    @Mapping(target = "firstSignPhone", ignore = true)
    @Mapping(target = "firstSignEmail", ignore = true)
    @Mapping(target = "secondSignTime", ignore = true)
    @Mapping(target = "secondSignIp", ignore = true)
    @Mapping(target = "secondSignLogin", ignore = true)
    @Mapping(target = "secondSignCryptoprofile", ignore = true)
    @Mapping(target = "secondSignCryptoprofileType", ignore = true)
    @Mapping(target = "secondSignChannel", ignore = true)
    @Mapping(target = "secondSignToken", ignore = true)
    @Mapping(target = "secondSignType", ignore = true)
    @Mapping(target = "secondSignImsi", ignore = true)
    @Mapping(target = "secondSignCertId", ignore = true)
    @Mapping(target = "secondSignPhone", ignore = true)
    @Mapping(target = "secondSignEmail", ignore = true)
    public abstract ElectronicReceipt toEntity(ElectronicReceiptOperation dto);

    @Mapping(target = "orgGuid", source = "epkId")
    @Mapping(target = "sign.userGuid", source = "userGuid")
    @Mapping(target = "document.payer.tbCode", source = "tbCode")
    @Mapping(target = "deviceRequest.httpAccept", source = "httpAccept")
    @Mapping(target = "deviceRequest.httpReferer", source = "httpReferer")
    @Mapping(target = "deviceRequest.httpAcceptChars", source = "httpAcceptChars")
    @Mapping(target = "deviceRequest.httpAcceptEncoding", source = "httpAcceptEncoding")
    @Mapping(target = "deviceRequest.httpAcceptLanguage", source = "httpAcceptLanguage")
    @Mapping(target = "deviceRequest.ipAddress", source = "ipAddress")
    @Mapping(target = "deviceRequest.userAgent", source = "userAgent")
    @Mapping(target = "deviceRequest.devicePrint", source = "devicePrint")
    @Mapping(target = "document.id", source = "docId")
    @Mapping(target = "document.number", source = "docNumber")
    @Mapping(target = "document.date", source = "docDate")
    @Mapping(target = "document.amount", source = "amount")
    @Mapping(target = "document.currency", source = "currency")
    @Mapping(target = "document.payer.accountNumber", source = "accountNumber")
    @Mapping(target = "document.payer.codeBic", source = "codeBic")
    @Mapping(target = "document.destination", source = "destination")
    @Mapping(target = "document.payer.name", source = "payerName")
    @Mapping(target = "document.payer.inn", source = "payerInn")
    @Mapping(target = "document.payer.kpp", source = "payerKpp")
    @Mapping(target = "document.receiver.firstName", source = "firstName")
    @Mapping(target = "document.receiver.secondName", source = "secondName")
    @Mapping(target = "document.receiver.middleName", source = "middleName")
    @Mapping(target = "document.receiver.birthDay", source = "birthDay")
    @Mapping(target = "document.receiver.dulType", source = "dulType")
    @Mapping(target = "document.receiver.dulSerieNumber", source = "dulSerieNumber")
    @Mapping(target = "document.receiver.dulWhoIssue", source = "dulWhoIssue")
    @Mapping(target = "document.receiver.dulDateIssue", source = "dulDateIssue")
    @Mapping(target = "document.receiver.dulCodeIssue", source = "dulCodeIssue")
    @Mapping(target = "document.receipt.receiptDate", source = "receiptDate")
    @Mapping(target = "document.receipt.receiptTbCode", source = "receiptTbCode")
    @Mapping(target = "document.receipt.receiptOsbNumber", source = "receiptOsbNumber")
    @Mapping(target = "document.receipt.receiptVspNumber", source = "receiptVspNumber")
    @Mapping(target = "document.receipt.receiptPlaceName", source = "receiptPlaceName")
    @Mapping(target = "document.receipt.receiptPlaceAddress", source = "receiptPlaceAddress")
    @Mapping(target = "sign.signTime", source = "senderSignTime")
    @Mapping(target = "sign.signIpAddress", source = "senderIp")
    @Mapping(target = "sign.signLogin", source = "senderLogin")
    @Mapping(target = "sign.signCryptoprofile", source = "senderCryptoprofile")
    @Mapping(target = "sign.signCryptoprofileType", source = "senderCryptoprofileType")
    @Mapping(target = "sign.signChannel", source = "senderSignChannel")
    @Mapping(target = "sign.signToken", source = "senderToken")
    @Mapping(target = "sign.signType", source = "senderSignType")
    @Mapping(target = "sign.signImsi", source = "senderSignImsi")
    @Mapping(target = "sign.signCertId", source = "senderCertId")
    @Mapping(target = "sign.signPhone", source = "senderPhone")
    @Mapping(target = "sign.signEmail", source = "senderEmail")
    public abstract ElectronicReceiptOperation toDto(ElectronicReceipt entity);
    
    @AfterMapping
    protected void fillSigns(ElectronicReceiptOperation dto, @MappingTarget ElectronicReceipt entity) {
        if (dto.getSign().getSignNumber() == 1) {
            entity.setTimeOfOccurrence(dto.getSign().getSignTime());
            entity.setFirstSignTime(dto.getSign().getSignTime());
            entity.setFirstSignIp(dto.getSign().getSignIpAddress());
            entity.setFirstSignLogin(dto.getSign().getSignLogin());
            entity.setFirstSignCryptoprofile(dto.getSign().getSignCryptoprofile());
            entity.setFirstSignCryptoprofileType(dto.getSign().getSignCryptoprofileType());
            entity.setFirstSignChannel(dto.getSign().getSignChannel());
            entity.setFirstSignToken(dto.getSign().getSignToken());
            entity.setFirstSignType(dto.getSign().getSignType());
            entity.setFirstSignImsi(dto.getSign().getSignImsi());
            entity.setFirstSignCertId(dto.getSign().getSignCertId());
            entity.setFirstSignPhone(dto.getSign().getSignPhone());
            entity.setFirstSignEmail(dto.getSign().getSignEmail());
        } else if (dto.getSign().getSignNumber() == 2) {
            entity.setSecondSignTime(dto.getSign().getSignTime());
            entity.setSecondSignIp(dto.getSign().getSignIpAddress());
            entity.setSecondSignLogin(dto.getSign().getSignLogin());
            entity.setSecondSignCryptoprofile(dto.getSign().getSignCryptoprofile());
            entity.setSecondSignCryptoprofileType(dto.getSign().getSignCryptoprofileType());
            entity.setSecondSignChannel(dto.getSign().getSignChannel());
            entity.setSecondSignToken(dto.getSign().getSignToken());
            entity.setSecondSignType(dto.getSign().getSignType());
            entity.setSecondSignImsi(dto.getSign().getSignImsi());
            entity.setSecondSignCertId(dto.getSign().getSignCertId());
            entity.setSecondSignPhone(dto.getSign().getSignPhone());
            entity.setSecondSignEmail(dto.getSign().getSignEmail());
        }
    }

    @Mapping(source = "eventTime", target = "messageHeader.timeStamp")
    @Mapping(source = "docId", target = "identificationData.clientTransactionId")
    @Mapping(source = "tbCode", target = "identificationData.orgName")
    @Mapping(source = "requestId", target = "identificationData.requestId")
    @Mapping(source = "senderLogin", target = "identificationData.userLoginName")
    @Mapping(target = "identificationData.dboOperation", expression = "java(DboOperation.ELECTRONIC_CHEQUE)")
    @Mapping(source = "devicePrint", target = "deviceRequest.devicePrint")
    @Mapping(source = "httpAccept", target = "deviceRequest.httpAccept")
    @Mapping(source = "httpAcceptChars", target = "deviceRequest.httpAcceptChars")
    @Mapping(source = "httpAcceptEncoding", target = "deviceRequest.httpAcceptEncoding")
    @Mapping(source = "httpAcceptLanguage", target = "deviceRequest.httpAcceptLanguage")
    @Mapping(source = "httpReferer", target = "deviceRequest.httpReferrer")
    @Mapping(source = "ipAddress", target = "deviceRequest.ipAddress")
    @Mapping(source = "userAgent", target = "deviceRequest.userAgent")
    @Mapping(source = "timeOfOccurrence", target = "eventDataList.eventDataHeader.timeOfOccurrence")
    @Mapping(target = "eventDataList.eventDataHeader.eventType", expression = "java(DboOperation.ELECTRONIC_CHEQUE.getEventType())")
    @Mapping(target = "eventDataList.eventDataHeader.eventDescription", expression = "java(DboOperation.ELECTRONIC_CHEQUE.getEventDescription())")
    @Mapping(target = "eventDataList.eventDataHeader.clientDefinedEventType", expression = "java(DboOperation.ELECTRONIC_CHEQUE.getClientDefinedEventType())")
    @Mapping(source = "amount", target = "eventDataList.transactionData.amount.sum")
    @Mapping(source = "currency", target = "eventDataList.transactionData.amount.currency")
    @Mapping(source = "accountNumber", target = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(target = "channelIndicator", expression = "java(ChannelIndicator.WEB)")
    @Mapping(target = "clientDefinedChannelIndicator", expression = "java(ClientDefinedChannelIndicator.PPRB_BROWSER)")
    public abstract AnalyzeRequest toAnalyzeRequest(ElectronicReceipt entity);

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, ElectronicReceipt entity) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, entity, CRITERIA_MAP, DESCRIPTION_MAP);
    }

}
