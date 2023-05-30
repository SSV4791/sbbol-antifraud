package ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mapper(componentModel = "spring", imports = DboOperation.class)
public abstract class FastPaymentMapper implements CommonMapper<FastPayment> {

    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String USER_GUID = "userGuid";
    public static final String DOC_NUMBER = "docNumber";
    public static final String DOC_DATE = "docDate";
    public static final String DESTINATION = "destination";
    public static final String ID_OPERATION_OPKC = "idOperationOPKC";
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
    public static final String OTHER_ACC_NAME = "otherAccName";

    private static final Map<String, Function<FastPayment, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 53;

    static {
        Map<String, Function<FastPayment, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, FastPayment::getEpkId);
        criteriaMap.put(DIGITAL_ID, FastPayment::getDigitalId);
        criteriaMap.put(USER_GUID, FastPayment::getUserGuid);
        criteriaMap.put(DOC_NUMBER, FastPayment::getDocNumber);
        criteriaMap.put(DOC_DATE, FastPayment::getDocDate);
        criteriaMap.put(DESTINATION, FastPayment::getDestination);
        criteriaMap.put(ID_OPERATION_OPKC, FastPayment::getIdOperationOPKC);
        criteriaMap.put(PAYER_FINANCIAL_NAME, FastPayment::getPayerFinancialName);
        criteriaMap.put(PAYER_OSB_NUM, FastPayment::getPayerOsbNum);
        criteriaMap.put(PAYER_VSP_NUM, FastPayment::getPayerVspNum);
        criteriaMap.put(PAYER_ACC_BALANCE, FastPayment::getPayerAccBalance);
        criteriaMap.put(PAYER_ACC_CREATE_DATE, FastPayment::getPayerAccCreateDate);
        criteriaMap.put(PAYER_BIC, FastPayment::getPayerBic);
        criteriaMap.put(PAYER_DOCUMENT_NUMBER, FastPayment::getPayerDocumentNumber);
        criteriaMap.put(PAYER_DOCUMENT_TYPE, FastPayment::getPayerDocumentType);
        criteriaMap.put(PAYER_SEGMENT, FastPayment::getPayerSegment);
        criteriaMap.put(PAYER_INN, FastPayment::getPayerInn);
        criteriaMap.put(RECEIVER_INN, FastPayment::getReceiverInn);
        criteriaMap.put(RECEIVER_BANK_NAME, FastPayment::getReceiverBankName);
        criteriaMap.put(RECEIVER_BANK_COUNTRY_CODE, FastPayment::getReceiverBankCountryCode);
        criteriaMap.put(RECEIVER_BANK_CORR_ACC, FastPayment::getReceiverBankCorrAcc);
        criteriaMap.put(RECEIVER_BANK_ID, FastPayment::getReceiverBankId);
        criteriaMap.put(RECEIVER_DOCUMENT, FastPayment::getReceiverDocument);
        criteriaMap.put(RECEIVER_DOCUMENT_TYPE, FastPayment::getReceiverDocumentType);
        criteriaMap.put(RECEIVER_PHONE_NUMBER, FastPayment::getReceiverPhoneNumber);
        criteriaMap.put(PRIVATE_IP_ADDRESS, FastPayment::getPrivateIpAddress);
        criteriaMap.put(FIRST_SIGN_TIME, FastPayment::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, FastPayment::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, FastPayment::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, FastPayment::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, FastPayment::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, FastPayment::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, FastPayment::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, FastPayment::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, FastPayment::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, FastPayment::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, FastPayment::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, FastPayment::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, FastPayment::getFirstSignSource);
        criteriaMap.put(SENDER_SIGN_TIME, FastPayment::getSenderSignTime);
        criteriaMap.put(SENDER_IP, FastPayment::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, FastPayment::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, FastPayment::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, FastPayment::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, FastPayment::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, FastPayment::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, FastPayment::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, FastPayment::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, FastPayment::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, FastPayment::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, FastPayment::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, FastPayment::getSenderSource);
        criteriaMap.put(OTHER_ACC_NAME, FastPayment::getOtherAccName);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.ID");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(USER_GUID, "Уникальный идентификатор пользователя");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(DOC_DATE, "Дата платежного документа");
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(ID_OPERATION_OPKC, "Идентификатор Операции ОПКЦ СБП");
        descriptionMap.put(PAYER_FINANCIAL_NAME, "Наименование клиента");
        descriptionMap.put(PAYER_OSB_NUM, "Номер ОСБ");
        descriptionMap.put(PAYER_VSP_NUM, "Номер ВСП");
        descriptionMap.put(PAYER_ACC_BALANCE, "Остаток на счете плательщика");
        descriptionMap.put(PAYER_ACC_CREATE_DATE, "Дата открытия счета плательщика");
        descriptionMap.put(PAYER_BIC, "БИК SWIFT плательщика");
        descriptionMap.put(PAYER_DOCUMENT_NUMBER, "Номер ДУЛ");
        descriptionMap.put(PAYER_DOCUMENT_TYPE, "Тип ДУЛ документа");
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
    @Mapping(source = "document.idOperationOPKC", target = "idOperationOPKC")
    @Mapping(source = "document.destination", target = "destination")
    @Mapping(source = "document.payer.accountNumber", target = "accountNumber")
    @Mapping(source = "document.payer.financialName", target = "payerFinancialName")
    @Mapping(source = "document.payer.osbNum", target = "payerOsbNum")
    @Mapping(source = "document.payer.vspNum", target = "payerVspNum")
    @Mapping(source = "document.payer.accBalance", target = "payerAccBalance")
    @Mapping(source = "document.payer.accCreateDate", target = "payerAccCreateDate")
    @Mapping(source = "document.payer.bic", target = "payerBic")
    @Mapping(source = "document.payer.documentNumber", target = "payerDocumentNumber")
    @Mapping(source = "document.payer.documentType", target = "payerDocumentType")
    @Mapping(source = "document.payer.segment", target = "payerSegment")
    @Mapping(source = "document.payer.inn", target = "payerInn")
    @Mapping(source = "document.receiver.otherAccName", target = "otherAccName")
    @Mapping(source = "document.receiver.otherBicCode", target = "otherBicCode")
    @Mapping(source = "document.receiver.inn", target = "receiverInn")
    @Mapping(source = "document.receiver.bankName", target = "receiverBankName")
    @Mapping(source = "document.receiver.bankCountryCode", target = "receiverBankCountryCode")
    @Mapping(source = "document.receiver.bankCorrAcc", target = "receiverBankCorrAcc")
    @Mapping(source = "document.receiver.bankId", target = "receiverBankId")
    @Mapping(source = "document.receiver.document", target = "receiverDocument")
    @Mapping(source = "document.receiver.documentType", target = "receiverDocumentType")
    @Mapping(source = "document.receiver.phoneNumber", target = "receiverPhoneNumber")
    @Mapping(source = "document.receiver.account", target = "receiverAccount")
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
    @Mapping(target = "clientDefinedChannelIndicator", ignore = true)
    public abstract void updateFromDto(FastPaymentOperation dto, @MappingTarget FastPayment entity);

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
    @Mapping(source = "document.idOperationOPKC", target = "idOperationOPKC")
    @Mapping(source = "document.destination", target = "destination")
    @Mapping(source = "document.payer.accountNumber", target = "accountNumber")
    @Mapping(source = "document.payer.financialName", target = "payerFinancialName")
    @Mapping(source = "document.payer.osbNum", target = "payerOsbNum")
    @Mapping(source = "document.payer.vspNum", target = "payerVspNum")
    @Mapping(source = "document.payer.accBalance", target = "payerAccBalance")
    @Mapping(source = "document.payer.accCreateDate", target = "payerAccCreateDate")
    @Mapping(source = "document.payer.bic", target = "payerBic")
    @Mapping(source = "document.payer.documentNumber", target = "payerDocumentNumber")
    @Mapping(source = "document.payer.documentType", target = "payerDocumentType")
    @Mapping(source = "document.payer.segment", target = "payerSegment")
    @Mapping(source = "document.payer.inn", target = "payerInn")
    @Mapping(source = "document.receiver.otherAccName", target = "otherAccName")
    @Mapping(source = "document.receiver.otherBicCode", target = "otherBicCode")
    @Mapping(source = "document.receiver.inn", target = "receiverInn")
    @Mapping(source = "document.receiver.bankName", target = "receiverBankName")
    @Mapping(source = "document.receiver.bankCountryCode", target = "receiverBankCountryCode")
    @Mapping(source = "document.receiver.bankCorrAcc", target = "receiverBankCorrAcc")
    @Mapping(source = "document.receiver.bankId", target = "receiverBankId")
    @Mapping(source = "document.receiver.document", target = "receiverDocument")
    @Mapping(source = "document.receiver.documentType", target = "receiverDocumentType")
    @Mapping(source = "document.receiver.phoneNumber", target = "receiverPhoneNumber")
    @Mapping(source = "document.receiver.account", target = "receiverAccount")
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
    @Mapping(target = "clientDefinedChannelIndicator", ignore = true)
    public abstract FastPayment toEntity(FastPaymentOperation dto);

    @Mapping(target = "timeStamp", source = "eventTime")
    @Mapping(target = "orgGuid", source = "epkId")
    @Mapping(target = "document.id", source = "docId")
    @Mapping(target = "document.number", source = "docNumber")
    @Mapping(target = "document.date", source = "docDate")
    @Mapping(target = "document.amount", source = "amount")
    @Mapping(target = "document.currency", source = "currency")
    @Mapping(target = "document.idOperationOPKC", source = "idOperationOPKC")
    @Mapping(target = "document.destination", source = "destination")
    @Mapping(target = "document.payer.accountNumber", source = "accountNumber")
    @Mapping(target = "document.payer.financialName", source = "payerFinancialName")
    @Mapping(target = "document.payer.osbNum", source = "payerOsbNum")
    @Mapping(target = "document.payer.vspNum", source = "payerVspNum")
    @Mapping(target = "document.payer.accBalance", source = "payerAccBalance")
    @Mapping(target = "document.payer.accCreateDate", source = "payerAccCreateDate")
    @Mapping(target = "document.payer.bic", source = "payerBic")
    @Mapping(target = "document.payer.documentNumber", source = "payerDocumentNumber")
    @Mapping(target = "document.payer.documentType", source = "payerDocumentType")
    @Mapping(target = "document.payer.segment", source = "payerSegment")
    @Mapping(target = "document.payer.inn", source = "payerInn")
    @Mapping(target = "document.receiver.otherAccName", source = "otherAccName")
    @Mapping(target = "document.receiver.otherBicCode", source = "otherBicCode")
    @Mapping(target = "document.receiver.inn", source = "receiverInn")
    @Mapping(target = "document.receiver.bankName", source = "receiverBankName")
    @Mapping(target = "document.receiver.bankCountryCode", source = "receiverBankCountryCode")
    @Mapping(target = "document.receiver.bankCorrAcc", source = "receiverBankCorrAcc")
    @Mapping(target = "document.receiver.bankId", source = "receiverBankId")
    @Mapping(target = "document.receiver.document", source = "receiverDocument")
    @Mapping(target = "document.receiver.documentType", source = "receiverDocumentType")
    @Mapping(target = "document.receiver.phoneNumber", source = "receiverPhoneNumber")
    @Mapping(target = "document.receiver.account", source = "receiverAccount")
    @Mapping(target = "signs", ignore = true)
    @Mapping(target = "mappedSigns", ignore = true)
    public abstract FastPaymentOperation toDto(FastPayment entity);

    @AfterMapping
    protected void fillSigns(FastPaymentOperation dto, @MappingTarget FastPayment entity) {
        if (dto.getMappedSigns() != null && !dto.getMappedSigns().isEmpty()) {
            entity.setHttpReferer(dto.getMappedSigns().get(0).getHttpReferer());
            entity.setHttpAccept(dto.getMappedSigns().get(0).getHttpAccept());
            entity.setHttpAcceptEncoding(dto.getMappedSigns().get(0).getHttpAcceptEncoding());
            entity.setHttpAcceptChars(dto.getMappedSigns().get(0).getHttpAcceptChars());
            entity.setIpAddress(dto.getMappedSigns().get(0).getIpAddress());
            entity.setHttpAcceptLanguage(dto.getMappedSigns().get(0).getHttpAcceptLanguage());
            entity.setUserAgent(dto.getMappedSigns().get(0).getUserAgent());
            entity.setPrivateIpAddress(dto.getMappedSigns().get(0).getPrivateIpAddress());
            entity.setMobSdkData(dto.getMappedSigns().get(0).getMobSdkData());
            entity.setDevicePrint(dto.getMappedSigns().get(0).getDevicePrint());
            entity.setChannelIndicator(dto.getMappedSigns().get(0).getChannelIndicator());
            entity.setTbCode(dto.getMappedSigns().get(0).getTbCode());
            entity.setUserGuid(dto.getMappedSigns().get(0).getUserGuid() != null ?
                    dto.getMappedSigns().get(0).getUserGuid().toString() : null);
            entity.setClientDefinedChannelIndicator(dto.getMappedSigns().get(0).getClientDefinedChannelIndicator());

            entity.setFirstSignIp(dto.getMappedSigns().get(0).getIpAddress());
            entity.setFirstSignTime(dto.getMappedSigns().get(0).getSignTime());
            entity.setFirstSignCryptoprofile(dto.getMappedSigns().get(0).getSignCryptoprofile());
            entity.setFirstSignLogin(dto.getMappedSigns().get(0).getSignLogin());
            entity.setFirstSignChannel(dto.getMappedSigns().get(0).getSignChannel());
            entity.setFirstSignCryptoprofileType(dto.getMappedSigns().get(0).getSignCryptoprofileType());
            entity.setFirstSignType(dto.getMappedSigns().get(0).getSignType());
            entity.setFirstSignToken(dto.getMappedSigns().get(0).getSignToken());
            entity.setFirstSignCertId(dto.getMappedSigns().get(0).getSignCertId());
            entity.setFirstSignImsi(dto.getMappedSigns().get(0).getSignImsi());
            entity.setFirstSignEmail(dto.getMappedSigns().get(0).getSignEmail());
            entity.setFirstSignPhone(dto.getMappedSigns().get(0).getSignPhone());
            entity.setFirstSignSource(dto.getMappedSigns().get(0).getSignSource());

            int senderIndex = dto.getMappedSigns().size() - 1;
            entity.setSenderIp(dto.getMappedSigns().get(senderIndex).getIpAddress());
            entity.setSenderSignTime(dto.getMappedSigns().get(senderIndex).getSignTime());
            entity.setSenderCryptoprofile(dto.getMappedSigns().get(senderIndex).getSignCryptoprofile());
            entity.setSenderLogin(dto.getMappedSigns().get(senderIndex).getSignLogin());
            entity.setSenderSignChannel(dto.getMappedSigns().get(senderIndex).getSignChannel());
            entity.setSenderCryptoprofileType(dto.getMappedSigns().get(senderIndex).getSignCryptoprofileType());
            entity.setSenderSignType(dto.getMappedSigns().get(senderIndex).getSignType());
            entity.setSenderToken(dto.getMappedSigns().get(senderIndex).getSignToken());
            entity.setSenderCertId(dto.getMappedSigns().get(senderIndex).getSignCertId());
            entity.setSenderSignImsi(dto.getMappedSigns().get(senderIndex).getSignImsi());
            entity.setSenderEmail(dto.getMappedSigns().get(senderIndex).getSignEmail());
            entity.setSenderPhone(dto.getMappedSigns().get(senderIndex).getSignPhone());
            entity.setSenderSource(dto.getMappedSigns().get(senderIndex).getSignSource());
        }
    }

    @Mapping(source = "eventTime", target = "messageHeader.timeStamp")
    @Mapping(source = "docId", target = "identificationData.clientTransactionId")
    @Mapping(source = "tbCode", target = "identificationData.orgName")
    @Mapping(source = "senderLogin", target = "identificationData.userLoginName")
    @Mapping(source = "requestId", target = "identificationData.requestId")
    @Mapping(target = "identificationData.userName", constant = "")
    @Mapping(target = "identificationData.dboOperation", expression = "java(DboOperation.SBP_B2C.name())")
    @Mapping(source = "devicePrint", target = "deviceRequest.devicePrint")
    @Mapping(source = "mobSdkData", target = "deviceRequest.mobSdkData")
    @Mapping(source = "httpAccept", target = "deviceRequest.httpAccept")
    @Mapping(source = "httpAcceptChars", target = "deviceRequest.httpAcceptChars")
    @Mapping(source = "httpAcceptEncoding", target = "deviceRequest.httpAcceptEncoding")
    @Mapping(source = "httpAcceptLanguage", target = "deviceRequest.httpAcceptLanguage")
    @Mapping(source = "httpReferer", target = "deviceRequest.httpReferrer")
    @Mapping(source = "ipAddress", target = "deviceRequest.ipAddress")
    @Mapping(source = "userAgent", target = "deviceRequest.userAgent")
    @Mapping(source = "timeOfOccurrence", target = "eventDataList.eventData.timeOfOccurrence")
    @Mapping(target = "eventDataList.eventData.eventType", expression = "java(DboOperation.SBP_B2C.getEventType())")
    @Mapping(target = "eventDataList.eventData.eventDescription", expression = "java(DboOperation.SBP_B2C.getEventDescription())")
    @Mapping(target = "eventDataList.eventData.clientDefinedEventType", expression = "java(DboOperation.SBP_B2C.getClientDefinedEventType().name())")
    @Mapping(source = "amount", target = "eventDataList.transactionData.amount.amount")
    @Mapping(source = "currency", target = "eventDataList.transactionData.amount.currency")
    @Mapping(source = "accountNumber", target = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(source = "otherAccName", target = "eventDataList.transactionData.otherAccountData.accountName")
    @Mapping(source = "receiverAccount", target = "eventDataList.transactionData.otherAccountData.accountNumber")
    @Mapping(source = "otherBicCode", target = "eventDataList.transactionData.otherAccountData.routingCode")
    public abstract AnalyzeRequest toAnalyzeRequest(FastPayment entity);

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, FastPayment entity) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, entity, CRITERIA_MAP, DESCRIPTION_MAP);
    }

    // Требование ФП ИС прибавлять 3 часа для приведения времени к МСК. По согласованию данные атрибуты приходят в 0 тайм зоне
    @AfterMapping
    protected void add3HoursToTime(@MappingTarget AnalyzeRequest analyzeRequest) {
        addHoursToTime(analyzeRequest, DESCRIPTION_MAP, 3);
    }

}
