package ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Mapper(componentModel = "spring", imports = DboOperation.class)
public abstract class PaymentMapper implements CommonMapper<Payment> {

    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String USER_GUID = "userGuid";
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
    public static final String OTHER_ACC_NAME = "otherAccName";

    private static final Map<String, Function<Payment, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 62;

    static {
        Map<String, Function<Payment, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, Payment::getEpkId);
        criteriaMap.put(DIGITAL_ID, Payment::getDigitalId);
        criteriaMap.put(USER_GUID, Payment::getUserGuid);
        criteriaMap.put(DOC_NUMBER, Payment::getDocNumber);
        criteriaMap.put(DOC_DATE, Payment::getDocDate);
        criteriaMap.put(RECEIVER_INN, Payment::getReceiverInn);
        criteriaMap.put(DESTINATION, Payment::getDestination);
        criteriaMap.put(PAYER_INN, Payment::getPayerInn);
        criteriaMap.put(PRIVATE_IP_ADDRESS, Payment::getPrivateIpAddress);
        criteriaMap.put(FIRST_SIGN_TIME, Payment::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, Payment::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, Payment::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, Payment::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, Payment::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, Payment::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, Payment::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, Payment::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, Payment::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, Payment::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, Payment::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, Payment::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, Payment::getFirstSignSource);
        criteriaMap.put(SECOND_SIGN_TIME, Payment::getSecondSignTime);
        criteriaMap.put(SECOND_SIGN_IP, Payment::getSecondSignIp);
        criteriaMap.put(SECOND_SIGN_LOGIN, Payment::getSecondSignLogin);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE, Payment::getSecondSignCryptoprofile);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, Payment::getSecondSignCryptoprofileType);
        criteriaMap.put(SECOND_SIGN_CHANNEL, Payment::getSecondSignChannel);
        criteriaMap.put(SECOND_SIGN_TOKEN, Payment::getSecondSignToken);
        criteriaMap.put(SECOND_SIGN_TYPE, Payment::getSecondSignType);
        criteriaMap.put(SECOND_SIGN_IMSI, Payment::getSecondSignImsi);
        criteriaMap.put(SECOND_SIGN_CERT_ID, Payment::getSecondSignCertId);
        criteriaMap.put(SECOND_SIGN_PHONE, Payment::getSecondSignPhone);
        criteriaMap.put(SECOND_SIGN_EMAIL, Payment::getSecondSignEmail);
        criteriaMap.put(SECOND_SIGN_SOURCE, Payment::getSecondSignSource);
        criteriaMap.put(THIRD_SIGN_TIME, Payment::getThirdSignTime);
        criteriaMap.put(THIRD_SIGN_IP, Payment::getThirdSignIp);
        criteriaMap.put(THIRD_SIGN_LOGIN, Payment::getThirdSignLogin);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE, Payment::getThirdSignCryptoprofile);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, Payment::getThirdSignCryptoprofileType);
        criteriaMap.put(THIRD_SIGN_CHANNEL, Payment::getThirdSignChannel);
        criteriaMap.put(THIRD_SIGN_TOKEN, Payment::getThirdSignToken);
        criteriaMap.put(THIRD_SIGN_TYPE, Payment::getThirdSignType);
        criteriaMap.put(THIRD_SIGN_IMSI, Payment::getThirdSignImsi);
        criteriaMap.put(THIRD_SIGN_CERT_ID, Payment::getThirdSignCertId);
        criteriaMap.put(THIRD_SIGN_PHONE, Payment::getThirdSignPhone);
        criteriaMap.put(THIRD_SIGN_EMAIL, Payment::getThirdSignEmail);
        criteriaMap.put(THIRD_SIGN_SOURCE, Payment::getThirdSignSource);
        criteriaMap.put(SENDER_SIGN_TIME, Payment::getSenderSignTime);
        criteriaMap.put(SENDER_IP, Payment::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, Payment::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, Payment::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, Payment::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, Payment::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, Payment::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, Payment::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, Payment::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, Payment::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, Payment::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, Payment::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, Payment::getSenderSource);
        criteriaMap.put(OTHER_ACC_NAME, Payment::getOtherAccName);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.id");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(USER_GUID, "Уникальный идентификатор пользователя");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(DOC_DATE, "Дата платежного документа");
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
        descriptionMap.put(OTHER_ACC_NAME, "Наименование получателя");
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(source = "timeStamp", target = "eventTime")
    @Mapping(source = "orgGuid", target = "epkId")
    @Mapping(source = "document.id", target = "docId")
    @Mapping(source = "document.number", target = "docNumber")
    @Mapping(source = "document.date", target = "docDate")
    @Mapping(source = "document.amount", target = "amount")
    @Mapping(source = "document.currency", target = "currency")
    @Mapping(source = "document.executionSpeed", target = "executionSpeed")
    @Mapping(source = "document.otherAccBankType", target = "otherAccBankType")
    @Mapping(source = "document.otherAccOwnershipType", target = "otherAccOwnershipType")
    @Mapping(source = "document.transferMediumType", target = "transferMediumType")
    @Mapping(source = "document.destination", target = "destination")
    @Mapping(source = "document.payer.accountNumber", target = "accountNumber")
    @Mapping(source = "document.payer.inn", target = "payerInn")
    @Mapping(source = "document.receiver.otherAccName", target = "otherAccName")
    @Mapping(source = "document.receiver.balAccNumber", target = "balAccNumber")
    @Mapping(source = "document.receiver.otherBicCode", target = "otherBicCode")
    @Mapping(source = "document.receiver.otherAccType", target = "otherAccType")
    @Mapping(source = "document.receiver.inn", target = "receiverInn")
    @Mapping(target = "userGuid", ignore = true)
    @Mapping(target = "tbCode", ignore = true)
    @Mapping(target = "httpAccept", ignore = true)
    @Mapping(target = "httpReferer", ignore = true)
    @Mapping(target = "httpAcceptChars", ignore = true)
    @Mapping(target = "httpAcceptEncoding", ignore = true)
    @Mapping(target = "httpAcceptLanguage", ignore = true)
    @Mapping(target = "ipAddress", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    @Mapping(target = "devicePrint", ignore = true)
    @Mapping(target = "mobSdkData", ignore = true)
    @Mapping(target = "channelIndicator", ignore = true)
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
    @Mapping(target = "firstSignSource", ignore = true)
    @Mapping(target = "privateIpAddress", ignore = true)
    @Mapping(target = "senderSignTime", ignore = true)
    @Mapping(target = "senderIp", ignore = true)
    @Mapping(target = "senderLogin", ignore = true)
    @Mapping(target = "senderCryptoprofile", ignore = true)
    @Mapping(target = "senderCryptoprofileType", ignore = true)
    @Mapping(target = "senderSignChannel", ignore = true)
    @Mapping(target = "senderToken", ignore = true)
    @Mapping(target = "senderSignType", ignore = true)
    @Mapping(target = "senderSignImsi", ignore = true)
    @Mapping(target = "senderCertId", ignore = true)
    @Mapping(target = "senderPhone", ignore = true)
    @Mapping(target = "senderEmail", ignore = true)
    @Mapping(target = "senderSource", ignore = true)
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
    @Mapping(target = "secondSignSource", ignore = true)
    @Mapping(target = "thirdSignTime", ignore = true)
    @Mapping(target = "thirdSignIp", ignore = true)
    @Mapping(target = "thirdSignLogin", ignore = true)
    @Mapping(target = "thirdSignCryptoprofile", ignore = true)
    @Mapping(target = "thirdSignCryptoprofileType", ignore = true)
    @Mapping(target = "thirdSignChannel", ignore = true)
    @Mapping(target = "thirdSignToken", ignore = true)
    @Mapping(target = "thirdSignType", ignore = true)
    @Mapping(target = "thirdSignImsi", ignore = true)
    @Mapping(target = "thirdSignCertId", ignore = true)
    @Mapping(target = "thirdSignPhone", ignore = true)
    @Mapping(target = "thirdSignEmail", ignore = true)
    @Mapping(target = "thirdSignSource", ignore = true)
    @Mapping(target = "clientDefinedChannelIndicator", ignore = true)
    public abstract void updateFromDto(PaymentOperation dto, @MappingTarget Payment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", expression = "java(generateRequestId())")
    @Mapping(source = "timeStamp", target = "eventTime")
    @Mapping(source = "orgGuid", target = "epkId")
    @Mapping(source = "document.id", target = "docId")
    @Mapping(source = "document.number", target = "docNumber")
    @Mapping(source = "document.date", target = "docDate")
    @Mapping(source = "document.amount", target = "amount")
    @Mapping(source = "document.currency", target = "currency")
    @Mapping(source = "document.executionSpeed", target = "executionSpeed")
    @Mapping(source = "document.otherAccBankType", target = "otherAccBankType")
    @Mapping(source = "document.otherAccOwnershipType", target = "otherAccOwnershipType")
    @Mapping(source = "document.transferMediumType", target = "transferMediumType")
    @Mapping(source = "document.destination", target = "destination")
    @Mapping(source = "document.payer.accountNumber", target = "accountNumber")
    @Mapping(source = "document.payer.inn", target = "payerInn")
    @Mapping(source = "document.receiver.otherAccName", target = "otherAccName")
    @Mapping(source = "document.receiver.balAccNumber", target = "balAccNumber")
    @Mapping(source = "document.receiver.otherBicCode", target = "otherBicCode")
    @Mapping(source = "document.receiver.otherAccType", target = "otherAccType")
    @Mapping(source = "document.receiver.inn", target = "receiverInn")
    @Mapping(target = "userGuid", ignore = true)
    @Mapping(target = "tbCode", ignore = true)
    @Mapping(target = "httpAccept", ignore = true)
    @Mapping(target = "httpReferer", ignore = true)
    @Mapping(target = "httpAcceptChars", ignore = true)
    @Mapping(target = "httpAcceptEncoding", ignore = true)
    @Mapping(target = "httpAcceptLanguage", ignore = true)
    @Mapping(target = "ipAddress", ignore = true)
    @Mapping(target = "userAgent", ignore = true)
    @Mapping(target = "devicePrint", ignore = true)
    @Mapping(target = "mobSdkData", ignore = true)
    @Mapping(target = "channelIndicator", ignore = true)
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
    @Mapping(target = "firstSignSource", ignore = true)
    @Mapping(target = "privateIpAddress", ignore = true)
    @Mapping(target = "senderSignTime", ignore = true)
    @Mapping(target = "senderIp", ignore = true)
    @Mapping(target = "senderLogin", ignore = true)
    @Mapping(target = "senderCryptoprofile", ignore = true)
    @Mapping(target = "senderCryptoprofileType", ignore = true)
    @Mapping(target = "senderSignChannel", ignore = true)
    @Mapping(target = "senderToken", ignore = true)
    @Mapping(target = "senderSignType", ignore = true)
    @Mapping(target = "senderSignImsi", ignore = true)
    @Mapping(target = "senderCertId", ignore = true)
    @Mapping(target = "senderPhone", ignore = true)
    @Mapping(target = "senderEmail", ignore = true)
    @Mapping(target = "senderSource", ignore = true)
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
    @Mapping(target = "secondSignSource", ignore = true)
    @Mapping(target = "thirdSignTime", ignore = true)
    @Mapping(target = "thirdSignIp", ignore = true)
    @Mapping(target = "thirdSignLogin", ignore = true)
    @Mapping(target = "thirdSignCryptoprofile", ignore = true)
    @Mapping(target = "thirdSignCryptoprofileType", ignore = true)
    @Mapping(target = "thirdSignChannel", ignore = true)
    @Mapping(target = "thirdSignToken", ignore = true)
    @Mapping(target = "thirdSignType", ignore = true)
    @Mapping(target = "thirdSignImsi", ignore = true)
    @Mapping(target = "thirdSignCertId", ignore = true)
    @Mapping(target = "thirdSignPhone", ignore = true)
    @Mapping(target = "thirdSignEmail", ignore = true)
    @Mapping(target = "thirdSignSource", ignore = true)
    @Mapping(target = "clientDefinedChannelIndicator", ignore = true)
    public abstract Payment toEntity(PaymentOperation dto);

    @Mapping(target = "timeStamp", source = "eventTime")
    @Mapping(target = "orgGuid", source = "epkId")
    @Mapping(target = "document.id", source = "docId")
    @Mapping(target = "document.number", source = "docNumber")
    @Mapping(target = "document.date", source = "docDate")
    @Mapping(target = "document.amount", source = "amount")
    @Mapping(target = "document.currency", source = "currency")
    @Mapping(target = "document.executionSpeed", source = "executionSpeed")
    @Mapping(target = "document.otherAccBankType", source = "otherAccBankType")
    @Mapping(target = "document.otherAccOwnershipType", source = "otherAccOwnershipType")
    @Mapping(target = "document.transferMediumType", source = "transferMediumType")
    @Mapping(target = "document.destination", source = "destination")
    @Mapping(target = "document.payer.accountNumber", source = "accountNumber")
    @Mapping(target = "document.payer.inn", source = "payerInn")
    @Mapping(target = "document.receiver.otherAccName", source = "otherAccName")
    @Mapping(target = "document.receiver.balAccNumber", source = "balAccNumber")
    @Mapping(target = "document.receiver.otherBicCode", source = "otherBicCode")
    @Mapping(target = "document.receiver.otherAccType", source = "otherAccType")
    @Mapping(target = "document.receiver.inn", source = "receiverInn")
    @Mapping(target = "signs", ignore = true)
    @Mapping(target = "mappedSigns", ignore = true)
    public abstract PaymentOperation toDto(Payment entity);

    @AfterMapping
    protected void fillSigns(PaymentOperation dto, @MappingTarget Payment entity) {
        if (dto.getMappedSigns() != null && !dto.getMappedSigns().isEmpty()) {
            entity.setHttpAccept(dto.getMappedSigns().get(0).getHttpAccept());
            entity.setHttpReferer(dto.getMappedSigns().get(0).getHttpReferer());
            entity.setHttpAcceptChars(dto.getMappedSigns().get(0).getHttpAcceptChars());
            entity.setHttpAcceptEncoding(dto.getMappedSigns().get(0).getHttpAcceptEncoding());
            entity.setHttpAcceptLanguage(dto.getMappedSigns().get(0).getHttpAcceptLanguage());
            entity.setIpAddress(dto.getMappedSigns().get(0).getIpAddress());
            entity.setPrivateIpAddress(dto.getMappedSigns().get(0).getPrivateIpAddress());
            entity.setUserAgent(dto.getMappedSigns().get(0).getUserAgent());
            entity.setDevicePrint(dto.getMappedSigns().get(0).getDevicePrint());
            entity.setMobSdkData(dto.getMappedSigns().get(0).getMobSdkData());
            entity.setChannelIndicator(dto.getMappedSigns().get(0).getChannelIndicator());
            entity.setUserGuid(dto.getMappedSigns().get(0).getUserGuid() != null ?
                    dto.getMappedSigns().get(0).getUserGuid().toString() : null);
            entity.setTbCode(dto.getMappedSigns().get(0).getTbCode());
            entity.setClientDefinedChannelIndicator(dto.getMappedSigns().get(0).getClientDefinedChannelIndicator());

            entity.setFirstSignTime(dto.getMappedSigns().get(0).getSignTime());
            entity.setFirstSignIp(dto.getMappedSigns().get(0).getIpAddress());
            entity.setFirstSignLogin(dto.getMappedSigns().get(0).getSignLogin());
            entity.setFirstSignCryptoprofile(dto.getMappedSigns().get(0).getSignCryptoprofile());
            entity.setFirstSignCryptoprofileType(dto.getMappedSigns().get(0).getSignCryptoprofileType());
            entity.setFirstSignChannel(dto.getMappedSigns().get(0).getSignChannel());
            entity.setFirstSignToken(dto.getMappedSigns().get(0).getSignToken());
            entity.setFirstSignType(dto.getMappedSigns().get(0).getSignType());
            entity.setFirstSignImsi(dto.getMappedSigns().get(0).getSignImsi());
            entity.setFirstSignCertId(dto.getMappedSigns().get(0).getSignCertId());
            entity.setFirstSignPhone(dto.getMappedSigns().get(0).getSignPhone());
            entity.setFirstSignEmail(dto.getMappedSigns().get(0).getSignEmail());
            entity.setFirstSignSource(dto.getMappedSigns().get(0).getSignSource());

            int senderIndex = dto.getMappedSigns().size() - 1;
            entity.setSenderSignTime(dto.getMappedSigns().get(senderIndex).getSignTime());
            entity.setSenderIp(dto.getMappedSigns().get(senderIndex).getIpAddress());
            entity.setSenderLogin(dto.getMappedSigns().get(senderIndex).getSignLogin());
            entity.setSenderCryptoprofile(dto.getMappedSigns().get(senderIndex).getSignCryptoprofile());
            entity.setSenderCryptoprofileType(dto.getMappedSigns().get(senderIndex).getSignCryptoprofileType());
            entity.setSenderSignChannel(dto.getMappedSigns().get(senderIndex).getSignChannel());
            entity.setSenderToken(dto.getMappedSigns().get(senderIndex).getSignToken());
            entity.setSenderSignType(dto.getMappedSigns().get(senderIndex).getSignType());
            entity.setSenderSignImsi(dto.getMappedSigns().get(senderIndex).getSignImsi());
            entity.setSenderCertId(dto.getMappedSigns().get(senderIndex).getSignCertId());
            entity.setSenderPhone(dto.getMappedSigns().get(senderIndex).getSignPhone());
            entity.setSenderEmail(dto.getMappedSigns().get(senderIndex).getSignEmail());
            entity.setSenderSource(dto.getMappedSigns().get(senderIndex).getSignSource());

            if (dto.getMappedSigns().size() > 2) {
                entity.setSecondSignTime(dto.getMappedSigns().get(1).getSignTime());
                entity.setSecondSignIp(dto.getMappedSigns().get(1).getIpAddress());
                entity.setSecondSignLogin(dto.getMappedSigns().get(1).getSignLogin());
                entity.setSecondSignCryptoprofile(dto.getMappedSigns().get(1).getSignCryptoprofile());
                entity.setSecondSignCryptoprofileType(dto.getMappedSigns().get(1).getSignCryptoprofileType());
                entity.setSecondSignChannel(dto.getMappedSigns().get(1).getSignChannel());
                entity.setSecondSignToken(dto.getMappedSigns().get(1).getSignToken());
                entity.setSecondSignType(dto.getMappedSigns().get(1).getSignType());
                entity.setSecondSignImsi(dto.getMappedSigns().get(1).getSignImsi());
                entity.setSecondSignCertId(dto.getMappedSigns().get(1).getSignCertId());
                entity.setSecondSignPhone(dto.getMappedSigns().get(1).getSignPhone());
                entity.setSecondSignEmail(dto.getMappedSigns().get(1).getSignEmail());
                entity.setSecondSignSource(dto.getMappedSigns().get(1).getSignSource());
            }

            if (dto.getMappedSigns().size() > 3) {
                entity.setThirdSignTime(dto.getMappedSigns().get(2).getSignTime());
                entity.setThirdSignIp(dto.getMappedSigns().get(2).getIpAddress());
                entity.setThirdSignLogin(dto.getMappedSigns().get(2).getSignLogin());
                entity.setThirdSignCryptoprofile(dto.getMappedSigns().get(2).getSignCryptoprofile());
                entity.setThirdSignCryptoprofileType(dto.getMappedSigns().get(2).getSignCryptoprofileType());
                entity.setThirdSignChannel(dto.getMappedSigns().get(2).getSignChannel());
                entity.setThirdSignToken(dto.getMappedSigns().get(2).getSignToken());
                entity.setThirdSignType(dto.getMappedSigns().get(2).getSignType());
                entity.setThirdSignImsi(dto.getMappedSigns().get(2).getSignImsi());
                entity.setThirdSignCertId(dto.getMappedSigns().get(2).getSignCertId());
                entity.setThirdSignPhone(dto.getMappedSigns().get(2).getSignPhone());
                entity.setThirdSignEmail(dto.getMappedSigns().get(2).getSignEmail());
                entity.setThirdSignSource(dto.getMappedSigns().get(2).getSignSource());
            }
        }
    }

    @Mapping(source = "eventTime", target = "messageHeader.timeStamp")
    @Mapping(source = "docId", target = "identificationData.clientTransactionId")
    @Mapping(source = "tbCode", target = "identificationData.orgName")
    @Mapping(source = "senderLogin", target = "identificationData.userLoginName")
    @Mapping(source = "requestId", target = "identificationData.requestId")
    @Mapping(target = "identificationData.userName", constant = "")
    @Mapping(target = "identificationData.dboOperation", expression = "java(DboOperation.PAYMENT_DT_0401060)")
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
    @Mapping(target = "eventDataList.eventDataHeader.eventType", expression = "java(DboOperation.PAYMENT_DT_0401060.getEventType())")
    @Mapping(target = "eventDataList.eventDataHeader.eventDescription", expression = "java(DboOperation.PAYMENT_DT_0401060.getEventDescription())")
    @Mapping(target = "eventDataList.eventDataHeader.clientDefinedEventType", expression =
            "java(payment.getChannelIndicator() != null && payment.getClientDefinedChannelIndicator() != null ? DboOperation.PAYMENT_DT_0401060.getClientDefinedEventType(payment.getChannelIndicator(), payment.getClientDefinedChannelIndicator()) : null)")
    @Mapping(source = "amount", target = "eventDataList.transactionData.amount.sum")
    @Mapping(target = "eventDataList.transactionData.amount.currency", expression = "java(payment.getCurrency() == null ? \"RUB\" : payment.getCurrency())")
    @Mapping(source = "executionSpeed", target = "eventDataList.transactionData.executionSpeed")
    @Mapping(source = "otherAccBankType", target = "eventDataList.transactionData.otherAccountBankType")
    @Mapping(source = "accountNumber", target = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(source = "otherAccName", target = "eventDataList.transactionData.otherAccountData.accountName")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountNumber", expression = "java(payment.getBalAccNumber() == null ? \"\" : payment.getBalAccNumber())")
    @Mapping(source = "otherBicCode", target = "eventDataList.transactionData.otherAccountData.routingCode")
    @Mapping(source = "otherAccOwnershipType", target = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType")
    @Mapping(source = "otherAccType", target = "eventDataList.transactionData.otherAccountData.otherAccountType")
    @Mapping(source = "transferMediumType", target = "eventDataList.transactionData.otherAccountData.transferMediumType")
    public abstract AnalyzeRequest toAnalyzeRequest(Payment payment);

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, Payment entity) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, entity, CRITERIA_MAP, DESCRIPTION_MAP);
    }

    // Требование ФП ИС прибавлять 3 часа для приведения времени к МСК. По согласованию данные атрибуты приходят в 0 тайм зоне
    @AfterMapping
    protected void add3HoursToTime(@MappingTarget AnalyzeRequest analyzeRequest) {
        if (Objects.nonNull(analyzeRequest.getMessageHeader().getTimeStamp())) {
            analyzeRequest.getMessageHeader().setTimeStamp(analyzeRequest.getMessageHeader().getTimeStamp().plusHours(3));
        }
        if (Objects.nonNull(analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence())) {
            analyzeRequest.getEventDataList().getEventDataHeader().setTimeOfOccurrence(analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence().plusHours(3));
        }
        analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().stream()
                .filter(attribute -> attribute.getName().equals(DESCRIPTION_MAP.get(FIRST_SIGN_TIME)) ||
                        attribute.getName().equals(DESCRIPTION_MAP.get(SECOND_SIGN_TIME)) ||
                        attribute.getName().equals(DESCRIPTION_MAP.get(THIRD_SIGN_TIME)) ||
                        attribute.getName().equals(DESCRIPTION_MAP.get(SENDER_SIGN_TIME)))
                .forEach(attribute -> attribute.setValue(LocalDateTime.parse(attribute.getValue()).plusHours(3).toString()));
    }

}
