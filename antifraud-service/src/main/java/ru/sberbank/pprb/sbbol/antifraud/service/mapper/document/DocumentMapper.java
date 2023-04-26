package ru.sberbank.pprb.sbbol.antifraud.service.mapper.document;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.ClientDefinedAttributeList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.document.Document;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
public interface DocumentMapper extends CommonMapper<Document> {

    String SENDER_INN = "ИНН отправителя";
    String CUSTOM_FACT_DATA_TYPE = "STRING";

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", expression = "java(UUID.randomUUID())")
    Document toEntity(DocumentSaveRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastChangeDate", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    void updateEntity(DocumentSaveRequest request, @MappingTarget Document entity);

    @Mapping(target = "messageHeader.timeStamp", source = "timestamp")
    @Mapping(target = "messageHeader.requestType", source = "requestType")
    @Mapping(target = "identificationData.clientTransactionId", source = "docId")
    @Mapping(target = "identificationData.orgName", source = "orgName")
    @Mapping(target = "identificationData.userName", source = "userName")
    @Mapping(target = "identificationData.dboOperation", source = "dboOperation")
    @Mapping(target = "identificationData.requestId", source = "requestId")
    @Mapping(target = "identificationData.userLoginName", source = "userLoginName")
    @Mapping(target = "deviceRequest.devicePrint", source = "devicePrint")
    @Mapping(target = "deviceRequest.mobSdkData", source = "mobSdkData")
    @Mapping(target = "deviceRequest.httpAccept", source = "httpAccept")
    @Mapping(target = "deviceRequest.httpAcceptChars", source = "httpAcceptChars")
    @Mapping(target = "deviceRequest.httpAcceptEncoding", source = "httpAcceptEncoding")
    @Mapping(target = "deviceRequest.httpAcceptLanguage", source = "httpAcceptLanguage")
    @Mapping(target = "deviceRequest.httpReferrer", source = "httpReferrer")
    @Mapping(target = "deviceRequest.ipAddress", source = "ipAddress")
    @Mapping(target = "deviceRequest.userAgent", source = "userAgent")
    @Mapping(target = "eventDataList.eventData.eventType", source = "eventType")
    @Mapping(target = "eventDataList.eventData.eventDescription", source = "eventDescription")
    @Mapping(target = "eventDataList.eventData.clientDefinedEventType", source = "clientDefinedEventType")
    @Mapping(target = "eventDataList.eventData.timeOfOccurrence", source = "timeOfOccurrence")
    @Mapping(target = "eventDataList.transactionData.amount.amount", source = "amount")
    @Mapping(target = "eventDataList.transactionData.amount.currency", source = "currency")
    @Mapping(target = "eventDataList.transactionData.executionSpeed", source = "executionSpeed")
    @Mapping(target = "eventDataList.transactionData.otherAccountBankType", source = "otherAccountBankType")
    @Mapping(target = "eventDataList.transactionData.myAccountData.accountNumber", source = "myAccountNumber")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountName", source = "accountName")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountNumber", source = "otherAccountNumber")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.routingCode", source = "routingCode")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType", source = "otherAccountOwnershipType")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.otherAccountType", source = "otherAccountType")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.transferMediumType", source = "transferMediumType")
    @Mapping(target = "eventDataList.clientDefinedAttributeList.fact", source = "clientDefinedAttributeList")
    @Mapping(target = "eventDataList.customersDataList.customer", source = "customersDataList")
    AnalyzeRequest toAnalyzeRequest(Document entity);

    @AfterMapping
    default void fillAnalyzeRequest(@MappingTarget AnalyzeRequest request) {
        if (request.getIdentificationData() == null) {
            request.setIdentificationData(new IdentificationData());
        }
        IdentificationData identData = request.getIdentificationData();
        identData.setRequestId(identData.getRequestId() == null ? UUID.randomUUID() : identData.getRequestId());
        identData.setUserName(identData.getUserName() == null ? "" : identData.getUserName());
        identData.setOrgName(identData.getOrgName() == null ? "" : identData.getOrgName());

        if (request.getEventDataList() == null) {
            request.setEventDataList(new EventDataList());
        }
        if (request.getEventDataList().getClientDefinedAttributeList() == null) {
            request.getEventDataList().setClientDefinedAttributeList(new ClientDefinedAttributeList(new ArrayList<>()));
        } else if (request.getEventDataList().getClientDefinedAttributeList().getFact() == null) {
            request.getEventDataList().getClientDefinedAttributeList().setFact(new ArrayList<>());
        }
        Optional<Attribute> attribute = request.getEventDataList().getClientDefinedAttributeList().getFact().stream()
                .filter(attr -> SENDER_INN.equals(attr.getName()))
                .findFirst();
        if (attribute.isPresent()) {
            attribute.get().setValue(attribute.get().getValue() == null ? "" : attribute.get().getValue());
            attribute.get().setDataType(CUSTOM_FACT_DATA_TYPE);
        } else {
            request.getEventDataList().getClientDefinedAttributeList().getFact().add(new Attribute(SENDER_INN, "", CUSTOM_FACT_DATA_TYPE));
        }
    }

}
