package ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static ru.sberbank.pprb.sbbol.antifraud.api.DboOperation.SBP_B2C;

@Mapper(componentModel = "spring")
public abstract class FastPaymentMapper implements CommonMapper<SbpPaymentOperationGet> {

    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String DOC_NUMBER = "docNumber";
    public static final String DOC_DATE = "docDate";
    public static final String DESTINATION = "destination";
    public static final String PAYER_FINANCIAL_NAME = "payerFinancialName";
    public static final String PAYER_OSB_NUM = "payerOsbNum";
    public static final String PAYER_VSP_NUM = "payerVspNum";
    public static final String PAYER_ACC_BALANCE = "payerAccBalance";
    public static final String PAYER_ACC_CREATE_DATE = "payerAccCreateDate";
    public static final String PAYER_BIC = "payerBic";
    public static final String PAYER_DOCUMENT_NUMBER = "payerDocumentNumber";
    public static final String PAYER_DOCUMENT_TYPE = "payerDocumentType";
    public static final String PAYER_SEGMENT = "payerSegment";
    public static final String PAYER_INN = "payerInn";
    public static final String RECEIVER_INN = "receiverInn";
    public static final String RECEIVER_BANK_NAME = "receiverBankName";
    public static final String RECEIVER_BANK_COUNTRY_CODE = "receiverBankCountryCode";
    public static final String RECEIVER_BANK_CORR_ACC = "receiverBankCorrAcc";
    public static final String RECEIVER_BANK_ID = "receiverBankId";
    public static final String RECEIVER_DOCUMENT = "receiverDocument";
    public static final String RECEIVER_DOCUMENT_TYPE = "receiverDocumentType";
    public static final String RECEIVER_PHONE_NUMBER = "receiverPhoneNumber";
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

    private static final Map<String, Function<SbpPaymentOperationGet, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 50;

    static {
        Map<String, Function<SbpPaymentOperationGet, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, SbpPaymentOperationGet::getEpkId);
        criteriaMap.put(DIGITAL_ID, SbpPaymentOperationGet::getDigitalId);
        criteriaMap.put(DOC_NUMBER, SbpPaymentOperationGet::getDocNumber);
        criteriaMap.put(DOC_DATE, SbpPaymentOperationGet::getDocDate);
        criteriaMap.put(DESTINATION, SbpPaymentOperationGet::getDestination);
        criteriaMap.put(PAYER_FINANCIAL_NAME, SbpPaymentOperationGet::getPayerFinancialName);
        criteriaMap.put(PAYER_OSB_NUM, SbpPaymentOperationGet::getPayerOsbNum);
        criteriaMap.put(PAYER_VSP_NUM, SbpPaymentOperationGet::getPayerVspNum);
        criteriaMap.put(PAYER_ACC_BALANCE, SbpPaymentOperationGet::getPayerAccBalance);
        criteriaMap.put(PAYER_ACC_CREATE_DATE, SbpPaymentOperationGet::getPayerAccCreateDate);
        criteriaMap.put(PAYER_BIC, SbpPaymentOperationGet::getPayerBic);
        criteriaMap.put(PAYER_DOCUMENT_NUMBER, SbpPaymentOperationGet::getPayerDocumentNumber);
        criteriaMap.put(PAYER_DOCUMENT_TYPE, SbpPaymentOperationGet::getPayerDocumentType);
        criteriaMap.put(PAYER_SEGMENT, SbpPaymentOperationGet::getPayerSegment);
        criteriaMap.put(PAYER_INN, SbpPaymentOperationGet::getPayerInn);
        criteriaMap.put(RECEIVER_INN, SbpPaymentOperationGet::getReceiverInn);
        criteriaMap.put(RECEIVER_BANK_NAME, SbpPaymentOperationGet::getReceiverBankName);
        criteriaMap.put(RECEIVER_BANK_COUNTRY_CODE, SbpPaymentOperationGet::getReceiverBankCountryCode);
        criteriaMap.put(RECEIVER_BANK_CORR_ACC, SbpPaymentOperationGet::getReceiverBankCorrAcc);
        criteriaMap.put(RECEIVER_BANK_ID, SbpPaymentOperationGet::getReceiverBankId);
        criteriaMap.put(RECEIVER_DOCUMENT, SbpPaymentOperationGet::getReceiverDocument);
        criteriaMap.put(RECEIVER_DOCUMENT_TYPE, SbpPaymentOperationGet::getReceiverDocumentType);
        criteriaMap.put(RECEIVER_PHONE_NUMBER, SbpPaymentOperationGet::getReceiverPhoneNumber);
        criteriaMap.put(PRIVATE_IP_ADDRESS, SbpPaymentOperationGet::getPrivateIpAddress);
        criteriaMap.put(FIRST_SIGN_TIME, SbpPaymentOperationGet::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, SbpPaymentOperationGet::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, SbpPaymentOperationGet::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, SbpPaymentOperationGet::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, SbpPaymentOperationGet::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, SbpPaymentOperationGet::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, SbpPaymentOperationGet::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, SbpPaymentOperationGet::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, SbpPaymentOperationGet::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, SbpPaymentOperationGet::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, SbpPaymentOperationGet::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, SbpPaymentOperationGet::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, SbpPaymentOperationGet::getFirstSignSource);
        criteriaMap.put(SENDER_SIGN_TIME, SbpPaymentOperationGet::getSenderSignTime);
        criteriaMap.put(SENDER_IP, SbpPaymentOperationGet::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, SbpPaymentOperationGet::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, SbpPaymentOperationGet::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, SbpPaymentOperationGet::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, SbpPaymentOperationGet::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, SbpPaymentOperationGet::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, SbpPaymentOperationGet::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, SbpPaymentOperationGet::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, SbpPaymentOperationGet::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, SbpPaymentOperationGet::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, SbpPaymentOperationGet::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, SbpPaymentOperationGet::getSenderSource);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.id");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(DOC_DATE, "Дата документа");
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(PAYER_FINANCIAL_NAME, "Полное наименование организации");
        descriptionMap.put(PAYER_OSB_NUM, "Номер ОСБ");
        descriptionMap.put(PAYER_VSP_NUM, "Номер ВСП");
        descriptionMap.put(PAYER_ACC_BALANCE, "Остаток на счете плательщика");
        descriptionMap.put(PAYER_ACC_CREATE_DATE, "Дата открытия счета плательщика");
        descriptionMap.put(PAYER_BIC, "БИК SWIFT плательщика");
        descriptionMap.put(PAYER_DOCUMENT_NUMBER, "Номер ДУЛ");
        descriptionMap.put(PAYER_DOCUMENT_TYPE, "Тип ДУЛ");
        descriptionMap.put(PAYER_SEGMENT, "Сегмент клиента ЮЛ");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
        descriptionMap.put(RECEIVER_INN, "ИНН получателя");
        descriptionMap.put(RECEIVER_BANK_NAME, "Наименование Банка получателя");
        descriptionMap.put(RECEIVER_BANK_COUNTRY_CODE, "Код страны банка получателя");
        descriptionMap.put(RECEIVER_BANK_CORR_ACC, "Корсчет Банка получателя");
        descriptionMap.put(RECEIVER_BANK_ID, "Идентификатор Банка Получателя");
        descriptionMap.put(RECEIVER_DOCUMENT, "Документ Получателя");
        descriptionMap.put(RECEIVER_DOCUMENT_TYPE, "Тип документа получателя");
        descriptionMap.put(RECEIVER_PHONE_NUMBER, "Номер телефона получателя");
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
    @Mapping(source = "accountNumber", target = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(source = "otherAccName", target = "eventDataList.transactionData.otherAccountData.accountName")
    @Mapping(source = "receiverAccount", target = "eventDataList.transactionData.otherAccountData.accountNumber")
    @Mapping(source = "otherBicCode", target = "eventDataList.transactionData.otherAccountData.routingCode")
    public abstract AnalyzeRequest toAnalyzeRequest(SbpPaymentOperationGet dto);

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
        analyzeRequest.getIdentificationData().setDboOperation(SBP_B2C);
        analyzeRequest.getEventDataList().getEventDataHeader().setEventType(SBP_B2C.getEventType());
        analyzeRequest.getEventDataList().getEventDataHeader().setEventDescription(SBP_B2C.getEventDescription());
        analyzeRequest.getEventDataList().getEventDataHeader().setClientDefinedEventType(SBP_B2C.getClientDefinedEventType(null));
    }

    @AfterMapping
    public void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, SbpPaymentOperationGet operationGet) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, operationGet, CRITERIA_MAP, DESCRIPTION_MAP);
    }

}
