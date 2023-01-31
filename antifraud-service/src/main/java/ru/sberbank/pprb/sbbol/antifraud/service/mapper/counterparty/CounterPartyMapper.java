package ru.sberbank.pprb.sbbol.antifraud.service.mapper.counterparty;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyClientDefinedAttributes;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Mapper(componentModel = "spring", imports = {DboOperation.class, UUID.class})
public abstract class CounterPartyMapper implements CommonMapper<CounterPartyClientDefinedAttributes> {

    public static final String RECEIVER_NAME = "receiverName";
    public static final String COUNTER_PARTY_ID = "counterpartyId";
    public static final String USER_COMMENT = "userComment";
    public static final String RECEIVER_INN = "receiverInn";
    public static final String PAYER_INN = "payerInn";
    public static final String RECEIVER_BIC_SWIFT = "receiverBicSwift";
    public static final String RECEIVER_ACCOUNT = "receiverAccount";
    public static final String OSB_NUMBER = "osbNumber";
    public static final String VSP_NUMBER = "vspNumber";
    public static final String DBO_OPERATION_NAME = "dboOperationName";
    public static final String PAYER_NAME = "payerName";
    public static final String FIRST_SIGN_TIME = "firstSignTime";
    public static final String FIRST_SIGN_IP_ADDRESS = "firstSignIpAddress";
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
    public static final String SENDER_IP_ADDRESS = "senderIpAddress";
    public static final String SENDER_LOGIN = "senderLogin";
    public static final String SENDER_PHONE = "senderPhone";
    public static final String SENDER_EMAIL = "senderEmail";
    public static final String SENDER_SOURCE = "senderSource";
    public static final String PRIVATE_IP_ADDRESS = "privateIpAddress";
    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String SBBOL_GUID = "sbbolGuid";
    public static final String REESTR_ID = "reestrId";
    public static final String REESTR_ROW_COUNT = "reestrRowCount";
    public static final String REESTR_ROW_NUMBER = "reestrRowNumber";

    private static final Map<String, Function<CounterPartyClientDefinedAttributes, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 36;

    static {
        Map<String, Function<CounterPartyClientDefinedAttributes, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(RECEIVER_NAME, CounterPartyClientDefinedAttributes::getReceiverName);
        criteriaMap.put(COUNTER_PARTY_ID, CounterPartyClientDefinedAttributes::getCounterpartyId);
        criteriaMap.put(USER_COMMENT, CounterPartyClientDefinedAttributes::getUserComment);
        criteriaMap.put(RECEIVER_INN, CounterPartyClientDefinedAttributes::getReceiverInn);
        criteriaMap.put(PAYER_INN, CounterPartyClientDefinedAttributes::getPayerInn);
        criteriaMap.put(RECEIVER_BIC_SWIFT, CounterPartyClientDefinedAttributes::getReceiverBicSwift);
        criteriaMap.put(RECEIVER_ACCOUNT, CounterPartyClientDefinedAttributes::getReceiverAccount);
        criteriaMap.put(OSB_NUMBER, CounterPartyClientDefinedAttributes::getOsbNumber);
        criteriaMap.put(VSP_NUMBER, CounterPartyClientDefinedAttributes::getVspNumber);
        criteriaMap.put(DBO_OPERATION_NAME, CounterPartyClientDefinedAttributes::getDboOperationName);
        criteriaMap.put(PAYER_NAME, CounterPartyClientDefinedAttributes::getPayerName);
        criteriaMap.put(FIRST_SIGN_TIME, CounterPartyClientDefinedAttributes::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP_ADDRESS, CounterPartyClientDefinedAttributes::getFirstSignIpAddress);
        criteriaMap.put(FIRST_SIGN_LOGIN, CounterPartyClientDefinedAttributes::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, CounterPartyClientDefinedAttributes::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, CounterPartyClientDefinedAttributes::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, CounterPartyClientDefinedAttributes::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, CounterPartyClientDefinedAttributes::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, CounterPartyClientDefinedAttributes::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, CounterPartyClientDefinedAttributes::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, CounterPartyClientDefinedAttributes::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, CounterPartyClientDefinedAttributes::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, CounterPartyClientDefinedAttributes::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, CounterPartyClientDefinedAttributes::getFirstSignSource);
        criteriaMap.put(SENDER_IP_ADDRESS, CounterPartyClientDefinedAttributes::getSenderIpAddress);
        criteriaMap.put(SENDER_LOGIN, CounterPartyClientDefinedAttributes::getSenderLogin);
        criteriaMap.put(SENDER_PHONE, CounterPartyClientDefinedAttributes::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, CounterPartyClientDefinedAttributes::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, CounterPartyClientDefinedAttributes::getSenderSource);
        criteriaMap.put(PRIVATE_IP_ADDRESS, CounterPartyClientDefinedAttributes::getPrivateIpAddress);
        criteriaMap.put(EPK_ID, CounterPartyClientDefinedAttributes::getEpkId);
        criteriaMap.put(DIGITAL_ID, CounterPartyClientDefinedAttributes::getDigitalId);
        criteriaMap.put(SBBOL_GUID, CounterPartyClientDefinedAttributes::getSbbolGuid);
        criteriaMap.put(REESTR_ID, CounterPartyClientDefinedAttributes::getReestrId);
        criteriaMap.put(REESTR_ROW_COUNT, CounterPartyClientDefinedAttributes::getReestrRowCount);
        criteriaMap.put(REESTR_ROW_NUMBER, CounterPartyClientDefinedAttributes::getReestrRowNumber);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);
        
        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(RECEIVER_NAME, "Наименование получателя");
        descriptionMap.put(COUNTER_PARTY_ID, "Уникальный идентификатор партнера");
        descriptionMap.put(USER_COMMENT, "Комментарий пользователя");
        descriptionMap.put(RECEIVER_INN, "ИНН получателя");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
        descriptionMap.put(RECEIVER_BIC_SWIFT, "БИК SWIFT получателя");
        descriptionMap.put(RECEIVER_ACCOUNT, "Номер счета получателя");
        descriptionMap.put(OSB_NUMBER, "Номер ОСБ");
        descriptionMap.put(VSP_NUMBER, "Номер ВСП");
        descriptionMap.put(DBO_OPERATION_NAME, "Операция ДБО");
        descriptionMap.put(PAYER_NAME, "Наименование клиента");
        descriptionMap.put(FIRST_SIGN_TIME, "1-я подпись Время подписи");
        descriptionMap.put(FIRST_SIGN_IP_ADDRESS, "1-я подпись IP адрес");
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
        descriptionMap.put(SENDER_IP_ADDRESS, "Отправивший IP адрес");
        descriptionMap.put(SENDER_LOGIN, "Отправивший Логин");
        descriptionMap.put(SENDER_PHONE, "Отправивший Номер телефона");
        descriptionMap.put(SENDER_EMAIL, "Отправивший Адрес электронной почты");
        descriptionMap.put(SENDER_SOURCE, "Отправивший Канал");
        descriptionMap.put(PRIVATE_IP_ADDRESS, "Локальный IP адрес");
        descriptionMap.put(EPK_ID, "ЕПК.ID");
        descriptionMap.put(DIGITAL_ID, "digitalId");
        descriptionMap.put(SBBOL_GUID, "UUID текущей организации пользователя в CББОЛ");
        descriptionMap.put(REESTR_ID, "Id электронного реестра");
        descriptionMap.put(REESTR_ROW_COUNT, "Количество записей в электронном реестре");
        descriptionMap.put(REESTR_ROW_NUMBER, "Номер записи в электронном реестре");
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    @Mapping(target = "identificationData.requestId", expression = "java(UUID.randomUUID())")
    @Mapping(source = "eventData.timeOfOccurrence", target = "eventDataList.eventDataHeader.timeOfOccurrence")
    @Mapping(source = "eventData.eventType", target = "eventDataList.eventDataHeader.eventType")
    @Mapping(source = "eventData.eventDescription", target = "eventDataList.eventDataHeader.eventDescription")
    @Mapping(source = "eventData.clientDefinedEventType", target = "eventDataList.eventDataHeader.clientDefinedEventType")
    @Mapping(target = "eventDataList.clientDefinedAttributeList", ignore = true)
    @Mapping(target = "eventDataList.transactionData.executionSpeed", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountBankType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountName", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.routingCode", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountData.transferMediumType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.amount.sum", expression = "java(0L)")
    @Mapping(target = "eventDataList.transactionData.amount.currency", constant = "RUB")
    @Mapping(target = "eventDataList.transactionData.myAccountData.accountNumber", constant = "")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountNumber", constant = "")
    public abstract AnalyzeRequest toAnalyzeRequest(CounterPartySendToAnalyzeRq request);

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, CounterPartySendToAnalyzeRq request) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, request.getClientDefinedAttributeList(), CRITERIA_MAP, DESCRIPTION_MAP);
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
                .filter(attribute -> attribute.getName().equals(DESCRIPTION_MAP.get(FIRST_SIGN_TIME)))
                .filter(attribute -> Objects.nonNull(attribute.getValue()))
                .forEach(attribute -> attribute.setValue(LocalDateTime.parse(attribute.getValue()).plusHours(3).toString()));
    }

}
