package ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3TypedSign;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public abstract class PaymentV3Mapper implements CommonMapper<PaymentV3> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "requestType", source = "messageHeader.requestType")
    @Mapping(target = "timestamp", source = "messageHeader.timeStamp")
    @Mapping(target = "username", source = "identificationData.userName")
    @Mapping(target = "docId", source = "identificationData.clientTransactionId")
    @Mapping(target = "orgName", source = "identificationData.orgName")
    @Mapping(target = "userLoginName", source = "identificationData.userLoginName")
    @Mapping(target = "dboOperation", source = "identificationData.dboOperation")
    @Mapping(target = "httpAccept", source = "deviceRequest.httpAccept")
    @Mapping(target = "httpReferrer", source = "deviceRequest.httpReferrer")
    @Mapping(target = "httpAcceptChars", source = "deviceRequest.httpAcceptChars")
    @Mapping(target = "httpAcceptEncoding", source = "deviceRequest.httpAcceptEncoding")
    @Mapping(target = "httpAcceptLanguage", source = "deviceRequest.httpAcceptLanguage")
    @Mapping(target = "ipAddress", source = "deviceRequest.ipAddress")
    @Mapping(target = "userAgent", source = "deviceRequest.userAgent")
    @Mapping(target = "devicePrint", source = "deviceRequest.devicePrint")
    @Mapping(target = "mobSdkData", source = "deviceRequest.mobSdkData")
    @Mapping(target = "eventType", source = "eventDataList.eventData.eventType")
    @Mapping(target = "clientDefinedEventType", source = "eventDataList.eventData.clientDefinedEventType")
    @Mapping(target = "eventDescription", source = "eventDataList.eventData.eventDescription")
    @Mapping(target = "timeOfOccurrence", source = "eventDataList.eventData.timeOfOccurrence")
    @Mapping(target = "amount", source = "eventDataList.transactionData.amount.amount")
    @Mapping(target = "currency", source = "eventDataList.transactionData.amount.currency")
    @Mapping(target = "executionSpeed", source = "eventDataList.transactionData.executionSpeed")
    @Mapping(target = "otherAccountBankType", source = "eventDataList.transactionData.otherAccountBankType")
    @Mapping(target = "myAccountNumber", source = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(target = "otherAccountName", source = "eventDataList.transactionData.otherAccountData.accountName")
    @Mapping(target = "otherAccountNumber", source = "eventDataList.transactionData.otherAccountData.accountNumber")
    @Mapping(target = "routingCode", source = "eventDataList.transactionData.otherAccountData.routingCode")
    @Mapping(target = "otherAccOwnershipType", source = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType")
    @Mapping(target = "otherAccountType", source = "eventDataList.transactionData.otherAccountData.otherAccountType")
    @Mapping(target = "transferMediumType", source = "eventDataList.transactionData.otherAccountData.transferMediumType")
    @Mapping(target = "customFacts", source = "eventDataList.clientDefinedAttributeList.fact")
    @Mapping(target = "channelIndicator", source = "channelIndicator")
    @Mapping(target = "clientDefinedChannelIndicator", source = "clientDefinedChannelIndicator")
    public abstract PaymentV3 toEntity(PaymentOperationV3 dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "requestType", source = "messageHeader.requestType")
    @Mapping(target = "timestamp", source = "messageHeader.timeStamp")
    @Mapping(target = "username", source = "identificationData.userName")
    @Mapping(target = "docId", source = "identificationData.clientTransactionId")
    @Mapping(target = "orgName", source = "identificationData.orgName")
    @Mapping(target = "userLoginName", source = "identificationData.userLoginName")
    @Mapping(target = "dboOperation", source = "identificationData.dboOperation")
    @Mapping(target = "httpAccept", source = "deviceRequest.httpAccept")
    @Mapping(target = "httpReferrer", source = "deviceRequest.httpReferrer")
    @Mapping(target = "httpAcceptChars", source = "deviceRequest.httpAcceptChars")
    @Mapping(target = "httpAcceptEncoding", source = "deviceRequest.httpAcceptEncoding")
    @Mapping(target = "httpAcceptLanguage", source = "deviceRequest.httpAcceptLanguage")
    @Mapping(target = "ipAddress", source = "deviceRequest.ipAddress")
    @Mapping(target = "userAgent", source = "deviceRequest.userAgent")
    @Mapping(target = "devicePrint", source = "deviceRequest.devicePrint")
    @Mapping(target = "mobSdkData", source = "deviceRequest.mobSdkData")
    @Mapping(target = "eventType", source = "eventDataList.eventData.eventType")
    @Mapping(target = "clientDefinedEventType", source = "eventDataList.eventData.clientDefinedEventType")
    @Mapping(target = "eventDescription", source = "eventDataList.eventData.eventDescription")
    @Mapping(target = "timeOfOccurrence", source = "eventDataList.eventData.timeOfOccurrence")
    @Mapping(target = "amount", source = "eventDataList.transactionData.amount.amount")
    @Mapping(target = "currency", source = "eventDataList.transactionData.amount.currency")
    @Mapping(target = "executionSpeed", source = "eventDataList.transactionData.executionSpeed")
    @Mapping(target = "otherAccountBankType", source = "eventDataList.transactionData.otherAccountBankType")
    @Mapping(target = "myAccountNumber", source = "eventDataList.transactionData.myAccountData.accountNumber")
    @Mapping(target = "otherAccountName", source = "eventDataList.transactionData.otherAccountData.accountName")
    @Mapping(target = "otherAccountNumber", source = "eventDataList.transactionData.otherAccountData.accountNumber")
    @Mapping(target = "routingCode", source = "eventDataList.transactionData.otherAccountData.routingCode")
    @Mapping(target = "otherAccOwnershipType", source = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType")
    @Mapping(target = "otherAccountType", source = "eventDataList.transactionData.otherAccountData.otherAccountType")
    @Mapping(target = "transferMediumType", source = "eventDataList.transactionData.otherAccountData.transferMediumType")
    @Mapping(target = "customFacts", source = "eventDataList.clientDefinedAttributeList.fact")
    @Mapping(target = "channelIndicator", source = "channelIndicator")
    @Mapping(target = "clientDefinedChannelIndicator", source = "clientDefinedChannelIndicator")
    public abstract void updateFromDto(@MappingTarget PaymentV3 entity, PaymentOperationV3 dto);

    @Mapping(target = "eventDataList.customersDataList", ignore = true)
    @Mapping(target = "messageHeader.requestType", source = "requestType")
    @Mapping(target = "messageHeader.timeStamp", source = "timestamp")
    @Mapping(target = "identificationData.requestId", source = "requestId")
    @Mapping(target = "identificationData.userName", source = "username")
    @Mapping(target = "identificationData.clientTransactionId", source = "docId")
    @Mapping(target = "identificationData.orgName", source = "orgName")
    @Mapping(target = "identificationData.userLoginName", source = "userLoginName")
    @Mapping(target = "identificationData.dboOperation", source = "dboOperation")
    @Mapping(target = "deviceRequest.httpAccept", source = "httpAccept")
    @Mapping(target = "deviceRequest.httpReferrer", source = "httpReferrer")
    @Mapping(target = "deviceRequest.httpAcceptChars", source = "httpAcceptChars")
    @Mapping(target = "deviceRequest.httpAcceptEncoding", source = "httpAcceptEncoding")
    @Mapping(target = "deviceRequest.httpAcceptLanguage", source = "httpAcceptLanguage")
    @Mapping(target = "deviceRequest.ipAddress", source = "ipAddress")
    @Mapping(target = "deviceRequest.userAgent", source = "userAgent")
    @Mapping(target = "deviceRequest.devicePrint", source = "devicePrint")
    @Mapping(target = "deviceRequest.mobSdkData", source = "mobSdkData")
    @Mapping(target = "eventDataList.eventData.eventType", source = "eventType")
    @Mapping(target = "eventDataList.eventData.clientDefinedEventType", source = "clientDefinedEventType")
    @Mapping(target = "eventDataList.eventData.eventDescription", source = "eventDescription")
    @Mapping(target = "eventDataList.eventData.timeOfOccurrence", source = "timeOfOccurrence")
    @Mapping(target = "eventDataList.transactionData.amount.amount", source = "amount")
    @Mapping(target = "eventDataList.transactionData.amount.currency", source = "currency")
    @Mapping(target = "eventDataList.transactionData.executionSpeed", source = "executionSpeed")
    @Mapping(target = "eventDataList.transactionData.otherAccountBankType", source = "otherAccountBankType")
    @Mapping(target = "eventDataList.transactionData.myAccountData.accountNumber", source = "myAccountNumber")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountName", source = "otherAccountName")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountNumber", source = "otherAccountNumber")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.routingCode", source = "routingCode")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType", source = "otherAccOwnershipType")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountType", source = "otherAccountType")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.transferMediumType", source = "transferMediumType")
    @Mapping(target = "eventDataList.clientDefinedAttributeList.fact", source = "customFacts")
    @Mapping(target = "channelIndicator", source = "channelIndicator")
    @Mapping(target = "clientDefinedChannelIndicator", source = "clientDefinedChannelIndicator")
    public abstract AnalyzeRequest toAnalyzeRequest(PaymentV3 entity);

    @AfterMapping
    protected void addSignsInCustomFacts(@MappingTarget PaymentV3 entity, PaymentOperationV3 dto) {
        boolean isSenderSignAdded = false;
        boolean isFirstSignAdded = false;
        boolean isSecondSignAdded = false;
        boolean isSingleSignAdded = false;

        for (PaymentV3TypedSign typedSign : dto.getSigns()) {
            switch (typedSign.getSignNumber()) {
                case 0 -> {
                    if (!isSenderSignAdded) {
                        entity.getCustomFacts().addAll(convertSignToCustomFacts(typedSign, "Отправивший "));
                        isSenderSignAdded = true;
                    }
                }
                case 1 -> {
                    if (!isFirstSignAdded) {
                        entity.getCustomFacts().addAll(convertSignToCustomFacts(typedSign, "1-я подпись "));
                        isFirstSignAdded = true;
                    }
                }
                case 2 -> {
                    if (!isSecondSignAdded) {
                        entity.getCustomFacts().addAll(convertSignToCustomFacts(typedSign, "2-я подпись "));
                        isSecondSignAdded = true;
                    }
                }
                case 3 -> {
                    if (!isSingleSignAdded) {
                        entity.getCustomFacts().addAll(convertSignToCustomFacts(typedSign, "Единственная подпись "));
                        isSingleSignAdded = true;
                    }
                }
            }
        }
    }

    private List<Attribute> convertSignToCustomFacts(PaymentV3TypedSign typedSign, String signName) {
        String signTime = signName + "Время подписи";
        String signIp = signName + "IP адрес";
        String signLogin = signName + "Логин";
        String signCryptoprofile = signName + "Наименование криптопрофиля";
        String signCryptoprofileType = signName + "Тип криптопрофиля";
        String signChannel = signName + "Канал подписи";
        String signToken = signName + "Данные Токена";
        String signType = signName + "Тип подписи";
        String signImsi = signName + "IMSI";
        String signCertId = signName + "Идентификатор сертификата";
        String signPhone = signName + "Номер телефона";
        String signEmail = signName + "Адрес электронной почты";
        String signSource = signName + "Канал";
        String signDigitalUserId = signName + "digitalUserId";
        String signMacAddress = signName + "MAC адрес";
        String signGeoLocation = signName + "Данные о геолокации";
        String signPkProperty = signName + "Свойства компьютера";

        PaymentV3Sign sign = PaymentV3SignMapper.convertSign(typedSign.getSign());
        // время подписи приходит по UTC, требование Фрод-мон передавать по MSC
        sign.setSignTime(sign.getSignTime().plusHours(3));

        List<Attribute> signCustomFacts = new ArrayList<>(17);
        signCustomFacts.add(new Attribute(signTime, sign.getSignTime().toString(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signIp, sign.getSignIp(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signLogin, sign.getSignLogin(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signCryptoprofile, sign.getSignCryptoprofile(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signCryptoprofileType, sign.getSignCryptoprofileType(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signChannel, sign.getSignChannel(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signToken, sign.getSignToken(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signType, sign.getSignType(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signImsi, sign.getSignImsi(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signCertId, sign.getSignCertId(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signPhone, sign.getSignPhone(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signEmail, sign.getSignEmail(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signSource, sign.getSignSource(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signDigitalUserId, sign.getSignDigitalUserId(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signMacAddress, sign.getSignMacAddress(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signGeoLocation, sign.getSignMacAddress(), CUSTOM_FACT_DATA_TYPE));
        signCustomFacts.add(new Attribute(signPkProperty, sign.getSignPkProperty(), CUSTOM_FACT_DATA_TYPE));
        return signCustomFacts;
    }

}
