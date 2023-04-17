package ru.sberbank.pprb.sbbol.antifraud.service.mapper.ipt;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptClientDefinedAttributeList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.DeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Mapper(componentModel = "spring", imports = {UUID.class})
public abstract class IptMapper implements CommonMapper<IptClientDefinedAttributeList> {

    public static final String PAYER_NAME = "payerName";
    public static final String PAYER_INN = "payerInn";
    public static final String PAYER_BIC = "payerBic";
    public static final String PURPOSE = "purpose";
    public static final String CURRENCY_NAME = "currencyName";
    public static final String SENDER_INN = "senderInn";
    public static final String PAYEE_PHONE = "payeePhone";
    public static final String PAYEE_KPP = "payeeKpp";
    public static final String PAYEE_ACCOUNT_NUMBER = "payeeAccountNumber";
    public static final String PAYEE_MOBILE_PHONE = "payeeMobilePhone";
    public static final String OSB_CODE = "osbCode";
    public static final String VSP_CODE = "vspCode";
    public static final String DBO_CODE = "dboCode";
    public static final String DOC_NUMBER = "docNumber";
    public static final String CLIENT_NAME = "clientName";
    public static final String PAYEE_BANK_BIC = "payeeBankBic";
    public static final String PAYEE_BANK_NAME_CITY = "payeeBankNameCity";
    public static final String PAYER_CORR_BANK_NAME = "payerCorrBankName";
    public static final String PAYEE_ACCOUNT_NUMBER_LIST = "payeeAccountNumberList";
    public static final String PAYEE_BIC = "payeeBic";
    public static final String PAYER_BANK_NAME = "payerBankName";
    public static final String PAYER_BANK_CORR_ACC = "payerBankCorrAcc";
    public static final String TB_CODE = "tbCode";
    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String CUR_ORG_ID = "curOrgId";
    public static final String AMOUNT = "amount";
    public static final String DOC_DATE = "docDate";
    public static final String CURRENCY_ISO_CODE = "currencyIsoCode";
    public static final String VAT_AMOUNT = "vatAmount";
    public static final String VAT_TYPE = "vatType";
    public static final String VAT_VALUE = "vatValue";
    public static final String PAYEE_BANK_CITY = "payeeBankCity";
    public static final String PAYER_KPP = "payerKpp";
    public static final String PAYEE_BANK_CORR_ACC = "payeeBankCorrAcc";
    public static final String PAYEE_NAME = "payeeName";
    public static final String PAYEE_INN = "payeeInn";
    public static final String CHANNEL = "channel";
    public static final String DIGITAL_USER_ID = "digitalUserId";
    public static final String DOC_GUID = "docGuid";
    public static final String FIRST_SIGN_IP = "firstSignIp";
    public static final String FIRST_SIGN_CHANNEL = "firstSignChannel";
    public static final String FIRST_SIGN_TIME = "firstSignTime";
    public static final String FIRST_SIGN_CRYPTOPROFILE = "firstSignCryptoprofile";
    public static final String FIRST_SIGN_CRYPTOPROFILE_TYPE = "firstSignCryptoprofileType";
    public static final String FIRST_SIGN_TOKEN = "firstSignToken";
    public static final String FIRST_SIGN_TYPE = "firstSignType";
    public static final String FIRST_SIGN_IMSI = "firstSignImsi";
    public static final String FIRST_SIGN_CERT_ID = "firstSignCertId";
    public static final String FIRST_SIGN_PHONE = "firstSignPhone";
    public static final String FIRST_SIGN_EMAIL = "firstSignEmail";
    public static final String FIRST_SIGN_DIGITAL_USER_ID = "firstSignDigitalUserId";
    public static final String FIRST_SIGN_LOGIN = "firstSignLogin";
    public static final String SECOND_SIGN_IP = "secondSignIp";
    public static final String SECOND_SIGN_CHANNEL = "secondSignChannel";
    public static final String SECOND_SIGN_TIME = "secondSignTime";
    public static final String SECOND_SIGN_CRYPTOPROFILE = "secondSignCryptoprofile";
    public static final String SECOND_SIGN_CRYPTOPROFILE_TYPE = "secondSignCryptoprofileType";
    public static final String SECOND_SIGN_TOKEN = "secondSignToken";
    public static final String SECOND_SIGN_TYPE = "secondSignType";
    public static final String SECOND_SIGN_IMSI = "secondSignImsi";
    public static final String SECOND_SIGN_CERT_ID = "secondSignCertId";
    public static final String SECOND_SIGN_PHONE = "secondSignPhone";
    public static final String SECOND_SIGN_EMAIL = "secondSignEmail";
    public static final String SECOND_SIGN_DIGITAL_USER_ID = "secondSignDigitalUserId";
    public static final String SECOND_SIGN_LOGIN = "secondSignLogin";
    public static final String THIRD_SIGN_IP = "thirdSignIp";
    public static final String THIRD_SIGN_CHANNEL = "thirdSignChannel";
    public static final String THIRD_SIGN_TIME = "thirdSignTime";
    public static final String THIRD_SIGN_CRYPTOPROFILE = "thirdSignCryptoprofile";
    public static final String THIRD_SIGN_CRYPTOPROFILE_TYPE = "thirdSignCryptoprofileType";
    public static final String THIRD_SIGN_TOKEN = "thirdSignToken";
    public static final String THIRD_SIGN_TYPE = "thirdSignType";
    public static final String THIRD_SIGN_IMSI = "thirdSignImsi";
    public static final String THIRD_SIGN_CERT_ID = "thirdSignCertId";
    public static final String THIRD_SIGN_PHONE = "thirdSignPhone";
    public static final String THIRD_SIGN_EMAIL = "thirdSignEmail";
    public static final String THIRD_SIGN_DIGITAL_USER_ID = "thirdSignDigitalUserId";
    public static final String THIRD_SIGN_LOGIN = "thirdSignLogin";
    public static final String SINGLE_SIGN_IP = "singleSignIp";
    public static final String SINGLE_SIGN_CHANNEL = "singleSignChannel";
    public static final String SINGLE_SIGN_TIME = "singleSignTime";
    public static final String SINGLE_SIGN_CRYPTOPROFILE = "singleSignCryptoprofile";
    public static final String SINGLE_SIGN_CRYPTOPROFILE_TYPE = "singleSignCryptoprofileType";
    public static final String SINGLE_SIGN_TOKEN = "singleSignToken";
    public static final String SINGLE_SIGN_TYPE = "singleSignType";
    public static final String SINGLE_SIGN_IMSI = "singleSignImsi";
    public static final String SINGLE_SIGN_CERT_ID = "singleSignCertId";
    public static final String SINGLE_SIGN_PHONE = "singleSignPhone";
    public static final String SINGLE_SIGN_EMAIL = "singleSignEmail";
    public static final String SINGLE_SIGN_DIGITAL_USER_ID = "singleSignDigitalUserId";
    public static final String SINGLE_SIGN_LOGIN = "singleSignLogin";

    private static final Map<String, Function<IptClientDefinedAttributeList, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 92;

    static {
        Map<String, Function<IptClientDefinedAttributeList, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(PAYER_NAME, IptClientDefinedAttributeList::getPayerName);
        criteriaMap.put(PAYER_INN, IptClientDefinedAttributeList::getPayerInn);
        criteriaMap.put(PAYER_BIC, IptClientDefinedAttributeList::getPayerBic);
        criteriaMap.put(PURPOSE, IptClientDefinedAttributeList::getPurpose);
        criteriaMap.put(CURRENCY_NAME, IptClientDefinedAttributeList::getCurrencyName);
        criteriaMap.put(SENDER_INN, IptClientDefinedAttributeList::getSenderInn);
        criteriaMap.put(PAYEE_PHONE, IptClientDefinedAttributeList::getPayeePhone);
        criteriaMap.put(PAYEE_KPP, IptClientDefinedAttributeList::getPayeeKpp);
        criteriaMap.put(PAYEE_ACCOUNT_NUMBER, IptClientDefinedAttributeList::getPayeeAccountNumber);
        criteriaMap.put(PAYEE_MOBILE_PHONE, IptClientDefinedAttributeList::getPayeeMobilePhone);
        criteriaMap.put(OSB_CODE, IptClientDefinedAttributeList::getOsbCode);
        criteriaMap.put(VSP_CODE, IptClientDefinedAttributeList::getVspCode);
        criteriaMap.put(DBO_CODE, IptClientDefinedAttributeList::getDboCode);
        criteriaMap.put(DOC_NUMBER, IptClientDefinedAttributeList::getDocNumber);
        criteriaMap.put(CLIENT_NAME, IptClientDefinedAttributeList::getClientName);
        criteriaMap.put(PAYEE_BANK_BIC, IptClientDefinedAttributeList::getPayeeBankBic);
        criteriaMap.put(PAYEE_BANK_NAME_CITY, IptClientDefinedAttributeList::getPayeeBankNameCity);
        criteriaMap.put(PAYER_CORR_BANK_NAME, IptClientDefinedAttributeList::getPayerCorrBankName);
        criteriaMap.put(PAYEE_ACCOUNT_NUMBER_LIST, IptClientDefinedAttributeList::getPayeeAccountNumberList);
        criteriaMap.put(PAYEE_BIC, IptClientDefinedAttributeList::getPayeeBic);
        criteriaMap.put(PAYER_BANK_NAME, IptClientDefinedAttributeList::getPayerBankName);
        criteriaMap.put(PAYER_BANK_CORR_ACC, IptClientDefinedAttributeList::getPayerBankCorrAcc);
        criteriaMap.put(TB_CODE, IptClientDefinedAttributeList::getTbCode);
        criteriaMap.put(EPK_ID, IptClientDefinedAttributeList::getEpkId);
        criteriaMap.put(DIGITAL_ID, IptClientDefinedAttributeList::getDigitalId);
        criteriaMap.put(CUR_ORG_ID, IptClientDefinedAttributeList::getCurOrgId);
        criteriaMap.put(AMOUNT, IptClientDefinedAttributeList::getAmount);
        criteriaMap.put(DOC_DATE, IptClientDefinedAttributeList::getDocDate);
        criteriaMap.put(CURRENCY_ISO_CODE, IptClientDefinedAttributeList::getCurrencyIsoCode);
        criteriaMap.put(VAT_AMOUNT, IptClientDefinedAttributeList::getVatAmount);
        criteriaMap.put(VAT_TYPE, IptClientDefinedAttributeList::getVatType);
        criteriaMap.put(VAT_VALUE, IptClientDefinedAttributeList::getVatValue);
        criteriaMap.put(PAYEE_BANK_CITY, IptClientDefinedAttributeList::getPayeeBankCity);
        criteriaMap.put(PAYER_KPP, IptClientDefinedAttributeList::getPayerKpp);
        criteriaMap.put(PAYEE_BANK_CORR_ACC, IptClientDefinedAttributeList::getPayerBankCorrAcc);
        criteriaMap.put(PAYEE_NAME, IptClientDefinedAttributeList::getPayeeName);
        criteriaMap.put(PAYEE_INN, IptClientDefinedAttributeList::getPayeeInn);
        criteriaMap.put(CHANNEL, IptClientDefinedAttributeList::getChannel);
        criteriaMap.put(DIGITAL_USER_ID, IptClientDefinedAttributeList::getDigitalUserId);
        criteriaMap.put(DOC_GUID, IptClientDefinedAttributeList::getDocGuid);
        criteriaMap.put(FIRST_SIGN_IP, IptClientDefinedAttributeList::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_CHANNEL, IptClientDefinedAttributeList::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TIME, IptClientDefinedAttributeList::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, IptClientDefinedAttributeList::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, IptClientDefinedAttributeList::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_TOKEN, IptClientDefinedAttributeList::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, IptClientDefinedAttributeList::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, IptClientDefinedAttributeList::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, IptClientDefinedAttributeList::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, IptClientDefinedAttributeList::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, IptClientDefinedAttributeList::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_DIGITAL_USER_ID, IptClientDefinedAttributeList::getFirstSignDigitalUserId);
        criteriaMap.put(FIRST_SIGN_LOGIN, IptClientDefinedAttributeList::getFirstSignLogin);
        criteriaMap.put(SECOND_SIGN_IP, IptClientDefinedAttributeList::getSecondSignIp);
        criteriaMap.put(SECOND_SIGN_CHANNEL, IptClientDefinedAttributeList::getSecondSignChannel);
        criteriaMap.put(SECOND_SIGN_TIME, IptClientDefinedAttributeList::getSecondSignTime);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE, IptClientDefinedAttributeList::getSecondSignCryptoprofile);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, IptClientDefinedAttributeList::getSecondSignCryptoprofileType);
        criteriaMap.put(SECOND_SIGN_TOKEN, IptClientDefinedAttributeList::getSecondSignToken);
        criteriaMap.put(SECOND_SIGN_TYPE, IptClientDefinedAttributeList::getSecondSignType);
        criteriaMap.put(SECOND_SIGN_IMSI, IptClientDefinedAttributeList::getSecondSignImsi);
        criteriaMap.put(SECOND_SIGN_CERT_ID, IptClientDefinedAttributeList::getSecondSignCertId);
        criteriaMap.put(SECOND_SIGN_PHONE, IptClientDefinedAttributeList::getSecondSignPhone);
        criteriaMap.put(SECOND_SIGN_EMAIL, IptClientDefinedAttributeList::getSecondSignEmail);
        criteriaMap.put(SECOND_SIGN_DIGITAL_USER_ID, IptClientDefinedAttributeList::getSecondSignDigitalUserId);
        criteriaMap.put(SECOND_SIGN_LOGIN, IptClientDefinedAttributeList::getSecondSignLogin);
        criteriaMap.put(THIRD_SIGN_IP, IptClientDefinedAttributeList::getThirdSignIp);
        criteriaMap.put(THIRD_SIGN_CHANNEL, IptClientDefinedAttributeList::getThirdSignChannel);
        criteriaMap.put(THIRD_SIGN_TIME, IptClientDefinedAttributeList::getThirdSignTime);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE, IptClientDefinedAttributeList::getThirdSignCryptoprofile);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, IptClientDefinedAttributeList::getThirdSignCryptoprofileType);
        criteriaMap.put(THIRD_SIGN_TOKEN, IptClientDefinedAttributeList::getThirdSignToken);
        criteriaMap.put(THIRD_SIGN_TYPE, IptClientDefinedAttributeList::getThirdSignType);
        criteriaMap.put(THIRD_SIGN_IMSI, IptClientDefinedAttributeList::getThirdSignImsi);
        criteriaMap.put(THIRD_SIGN_CERT_ID, IptClientDefinedAttributeList::getThirdSignCertId);
        criteriaMap.put(THIRD_SIGN_PHONE, IptClientDefinedAttributeList::getThirdSignPhone);
        criteriaMap.put(THIRD_SIGN_EMAIL, IptClientDefinedAttributeList::getThirdSignEmail);
        criteriaMap.put(THIRD_SIGN_DIGITAL_USER_ID, IptClientDefinedAttributeList::getThirdSignDigitalUserId);
        criteriaMap.put(THIRD_SIGN_LOGIN, IptClientDefinedAttributeList::getThirdSignLogin);
        criteriaMap.put(SINGLE_SIGN_IP, IptClientDefinedAttributeList::getSingleSignIp);
        criteriaMap.put(SINGLE_SIGN_CHANNEL, IptClientDefinedAttributeList::getSingleSignChannel);
        criteriaMap.put(SINGLE_SIGN_TIME, IptClientDefinedAttributeList::getSingleSignTime);
        criteriaMap.put(SINGLE_SIGN_CRYPTOPROFILE, IptClientDefinedAttributeList::getSingleSignCryptoprofile);
        criteriaMap.put(SINGLE_SIGN_CRYPTOPROFILE_TYPE, IptClientDefinedAttributeList::getSingleSignCryptoprofileType);
        criteriaMap.put(SINGLE_SIGN_TOKEN, IptClientDefinedAttributeList::getSingleSignToken);
        criteriaMap.put(SINGLE_SIGN_TYPE, IptClientDefinedAttributeList::getSingleSignType);
        criteriaMap.put(SINGLE_SIGN_IMSI, IptClientDefinedAttributeList::getSingleSignImsi);
        criteriaMap.put(SINGLE_SIGN_CERT_ID, IptClientDefinedAttributeList::getSingleSignCertId);
        criteriaMap.put(SINGLE_SIGN_PHONE, IptClientDefinedAttributeList::getSingleSignPhone);
        criteriaMap.put(SINGLE_SIGN_EMAIL, IptClientDefinedAttributeList::getSingleSignEmail);
        criteriaMap.put(SINGLE_SIGN_DIGITAL_USER_ID, IptClientDefinedAttributeList::getSingleSignDigitalUserId);
        criteriaMap.put(SINGLE_SIGN_LOGIN, IptClientDefinedAttributeList::getSingleSignLogin);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(PAYER_NAME, "Наименование получателя платежа");
        descriptionMap.put(PAYER_INN, "ИНН получателя");
        descriptionMap.put(PAYER_BIC, "БИК SWIFT получателя");
        descriptionMap.put(PURPOSE, "Назначение платежа");
        descriptionMap.put(CURRENCY_NAME, "Валюта");
        descriptionMap.put(SENDER_INN, "ИНН отправителя");
        descriptionMap.put(PAYEE_PHONE, "Контактный телефон");
        descriptionMap.put(PAYEE_KPP, "КПП");
        descriptionMap.put(PAYEE_ACCOUNT_NUMBER, "Счет клиента");
        descriptionMap.put(PAYEE_MOBILE_PHONE, "Номер мобильного телефона");
        descriptionMap.put(OSB_CODE, "Номер ОСБ");
        descriptionMap.put(VSP_CODE, "Номер ВСП");
        descriptionMap.put(DBO_CODE, "Операция ДБО");
        descriptionMap.put(DOC_NUMBER, "Номер платежного документа");
        descriptionMap.put(CLIENT_NAME, "Наименование клиента");
        descriptionMap.put(PAYEE_BANK_BIC, "Бик банка, где открыт расчетный счет организации");
        descriptionMap.put(PAYEE_BANK_NAME_CITY, "Наименование и расположение банка");
        descriptionMap.put(PAYER_CORR_BANK_NAME, "Наименование банка-корреспондента");
        descriptionMap.put(PAYEE_ACCOUNT_NUMBER_LIST, "Список номеров счетов");
        descriptionMap.put(PAYEE_BIC, "БИК SWIFT плательщика");
        descriptionMap.put(PAYER_BANK_NAME, "Наименование Банка получателя");
        descriptionMap.put(PAYER_BANK_CORR_ACC, "Корсчет Банка получателя");
        descriptionMap.put(TB_CODE, "ТЕРБАНК");
        descriptionMap.put(EPK_ID, "ЕПК.ID");
        descriptionMap.put(DIGITAL_ID, "Идентификатор личного кабинета");
        descriptionMap.put(CUR_ORG_ID, "UUID текущей организации пользователя в CББОЛ");
        descriptionMap.put(AMOUNT, "Сумма платежа");
        descriptionMap.put(DOC_DATE, "Дата платежного документа");
        descriptionMap.put(CURRENCY_ISO_CODE, "Валюта документа ISO формата");
        descriptionMap.put(VAT_AMOUNT, "Сумма НДС");
        descriptionMap.put(VAT_TYPE, "Тип НДС");
        descriptionMap.put(VAT_VALUE, "Значение ставки НДС");
        descriptionMap.put(PAYEE_BANK_CITY, "Город");
        descriptionMap.put(PAYER_KPP, "КПП получателя");
        descriptionMap.put(PAYEE_BANK_CORR_ACC, "Корр. Плательщика");
        descriptionMap.put(PAYEE_NAME, "Наименование получателя");
        descriptionMap.put(PAYEE_INN, "ИНН плательщика");
        descriptionMap.put(CHANNEL, "Канал источник создания");
        descriptionMap.put(DIGITAL_USER_ID, "digitalUserId");
        descriptionMap.put(DOC_GUID, "Идентификатор платежного требования");
        descriptionMap.put(FIRST_SIGN_IP, "1-я подпись IP адрес");
        descriptionMap.put(FIRST_SIGN_CHANNEL, "1-я подпись Канал подписи");
        descriptionMap.put(FIRST_SIGN_TIME, "1-я подпись Время подписи");
        descriptionMap.put(FIRST_SIGN_CRYPTOPROFILE, "1-я подпись Наименование криптопрофиля");
        descriptionMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, "1-я подпись Тип криптопрофиля");
        descriptionMap.put(FIRST_SIGN_TOKEN, "1-я подпись Данные Токена");
        descriptionMap.put(FIRST_SIGN_TYPE, "1-я подпись Тип подписи");
        descriptionMap.put(FIRST_SIGN_IMSI, "1-я подпись IMSI");
        descriptionMap.put(FIRST_SIGN_CERT_ID, "1-я подпись Идентификатор сертификата");
        descriptionMap.put(FIRST_SIGN_PHONE, "1-я подпись Номер телефона");
        descriptionMap.put(FIRST_SIGN_EMAIL, "1-я подпись Адрес электронной почты");
        descriptionMap.put(FIRST_SIGN_DIGITAL_USER_ID, "1-я подпись digitalUserId");
        descriptionMap.put(FIRST_SIGN_LOGIN, "1-я подпись Логин");
        descriptionMap.put(SECOND_SIGN_IP, "2-я подпись IP адрес");
        descriptionMap.put(SECOND_SIGN_CHANNEL, "2-я подпись Канал подписи");
        descriptionMap.put(SECOND_SIGN_TIME, "2-я подпись Время подписи");
        descriptionMap.put(SECOND_SIGN_CRYPTOPROFILE, "2-я подпись Наименование криптопрофиля");
        descriptionMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, "2-я подпись Тип криптопрофиля");
        descriptionMap.put(SECOND_SIGN_TOKEN, "2-я подпись Данные Токена");
        descriptionMap.put(SECOND_SIGN_TYPE, "2-я подпись Тип подписи");
        descriptionMap.put(SECOND_SIGN_IMSI, "2-я подпись IMSI");
        descriptionMap.put(SECOND_SIGN_CERT_ID, "2-я подпись Идентификатор сертификата");
        descriptionMap.put(SECOND_SIGN_PHONE, "2-я подпись Номер телефона");
        descriptionMap.put(SECOND_SIGN_EMAIL, "2-я подпись Адрес электронной почты");
        descriptionMap.put(SECOND_SIGN_DIGITAL_USER_ID, "2-я подпись digitalUserId");
        descriptionMap.put(SECOND_SIGN_LOGIN, "2-я подпись Логин");
        descriptionMap.put(THIRD_SIGN_IP, "3-я подпись IP адрес");
        descriptionMap.put(THIRD_SIGN_CHANNEL, "3-я подпись Канал подписи");
        descriptionMap.put(THIRD_SIGN_TIME, "3-я подпись Время подписи");
        descriptionMap.put(THIRD_SIGN_CRYPTOPROFILE, "3-я подпись Наименование криптопрофиля");
        descriptionMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, "3-я подпись Тип криптопрофиля");
        descriptionMap.put(THIRD_SIGN_TOKEN, "3-я подпись Данные Токена");
        descriptionMap.put(THIRD_SIGN_TYPE, "3-я подпись Тип подписи");
        descriptionMap.put(THIRD_SIGN_IMSI, "3-я подпись IMSI");
        descriptionMap.put(THIRD_SIGN_CERT_ID, "3-я подпись Идентификатор сертификата");
        descriptionMap.put(THIRD_SIGN_PHONE, "3-я подпись Номер телефона");
        descriptionMap.put(THIRD_SIGN_EMAIL, "3-я подпись Адрес электронной почты");
        descriptionMap.put(THIRD_SIGN_DIGITAL_USER_ID, "3-я подпись digitalUserId");
        descriptionMap.put(THIRD_SIGN_LOGIN, "3-я подпись Логин");
        descriptionMap.put(SINGLE_SIGN_IP, "Единственная подпись IP адрес");
        descriptionMap.put(SINGLE_SIGN_CHANNEL, "Единственная подпись Канал подписи");
        descriptionMap.put(SINGLE_SIGN_TIME, "Единственная подпись Время подписи");
        descriptionMap.put(SINGLE_SIGN_CRYPTOPROFILE, "Единственная подпись Наименование криптопрофиля");
        descriptionMap.put(SINGLE_SIGN_CRYPTOPROFILE_TYPE, "Единственная подпись Тип криптопрофиля");
        descriptionMap.put(SINGLE_SIGN_TOKEN, "Единственная подпись Данные Токена");
        descriptionMap.put(SINGLE_SIGN_TYPE, "Единственная подпись Тип подписи");
        descriptionMap.put(SINGLE_SIGN_IMSI, "Единственная подпись IMSI");
        descriptionMap.put(SINGLE_SIGN_CERT_ID, "Единственная подпись Идентификатор сертификата");
        descriptionMap.put(SINGLE_SIGN_PHONE, "Единственная подпись Номер телефона");
        descriptionMap.put(SINGLE_SIGN_EMAIL, "Единственная подпись Адрес электронной почты");
        descriptionMap.put(SINGLE_SIGN_DIGITAL_USER_ID, "Единственная подпись digitalUserId");
        descriptionMap.put(SINGLE_SIGN_LOGIN, "Единственная подпись Логин");
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    @Mapping(target = "identificationData.requestId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "deviceRequest.httpReferrer", source = "deviceRequest.httpReferer")
    @Mapping(target = "eventDataList.eventData.eventType", source = "eventData.eventType")
    @Mapping(target = "eventDataList.eventData.eventDescription", source = "eventData.eventDescription")
    @Mapping(target = "eventDataList.eventData.clientDefinedEventType", source = "eventData.clientDefinedEventType")
    @Mapping(target = "eventDataList.eventData.timeOfOccurrence", source = "eventData.timeOfOccurrence")
    @Mapping(target = "eventDataList.transactionData.amount.amount", source = "eventData.transactionData.amount", defaultValue = "0L")
    @Mapping(target = "eventDataList.transactionData.amount.currency", source = "eventData.transactionData.currency")
    @Mapping(target = "eventDataList.transactionData.executionSpeed", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountBankType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.myAccountData.accountNumber", source = "eventData.transactionData.myAccountData.accountNumber")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountName", source = "eventData.transactionData.otherAccountData.accountName")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountNumber", source = "eventData.transactionData.otherAccountData.accountNumber")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.routingCode", source = "eventData.transactionData.otherAccountData.routingCode")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.transferMediumType", ignore = true)
    @Mapping(target = "eventDataList.customersDataList", ignore = true)
    @Mapping(target = "eventDataList.clientDefinedAttributeList", ignore = true)
    public abstract AnalyzeRequest toAnalyzeRequest(IptSendToAnalyzeRq request);

    @AfterMapping
    protected void checkDeviceRequest(@MappingTarget AnalyzeRequest request) {
        if (request.getDeviceRequest() == null) {
            request.setDeviceRequest(new DeviceRequest());
        }
        request.getDeviceRequest().setIpAddress(request.getDeviceRequest().getIpAddress() == null ? "" : request.getDeviceRequest().getIpAddress());
    }

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, IptSendToAnalyzeRq request) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, request.getClientDefinedAttributeList(), CRITERIA_MAP, DESCRIPTION_MAP);
    }

    // Требование ФП ИС прибавлять 3 часа для приведения времени к МСК. По согласованию данные атрибуты приходят в 0 тайм зоне
    @AfterMapping
    protected void add3HoursToTime(@MappingTarget AnalyzeRequest analyzeRequest) {
        addHoursToTime(analyzeRequest, DESCRIPTION_MAP, 3);
    }

}
