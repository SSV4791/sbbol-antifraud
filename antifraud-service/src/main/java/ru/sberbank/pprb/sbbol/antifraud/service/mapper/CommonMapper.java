package ru.sberbank.pprb.sbbol.antifraud.service.mapper;

import org.mapstruct.Mapping;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.ClientDefinedAttributeList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface CommonMapper<T> {

    @Mapping(source = "identificationData.transactionId", target = "transactionId")
    @Mapping(source = "statusHeader.statusCode", target = "statusCode")
    @Mapping(source = "statusHeader.reasonCode", target = "reasonCode")
    @Mapping(source = "riskResult.triggeredRule.actionCode", target = "actionCode")
    @Mapping(source = "riskResult.triggeredRule.comment", target = "comment")
    @Mapping(source = "riskResult.triggeredRule.detailledComment", target = "detailledComment")
    @Mapping(source = "riskResult.triggeredRule.waitingTime", target = "waitingTime")
    AnalyzeResponse toAnalyzeResponse(FullAnalyzeResponse dto);

    default String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    default UUID stringToUuid(String s) {
        return s == null ? null : UUID.fromString(s);
    }

    default String uuidToString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

    default String enumToString(Enum<?> arg) {
        return arg == null ? null : arg.name();
    }

    default LocalDateTime offSetDateTimeToLocalDateTime(OffsetDateTime dateTime) {
        return dateTime == null ? null : dateTime.atZoneSameInstant(ZoneOffset.of("+03:00")).toLocalDateTime();
    }

    default void createClientDefinedAttributeList(AnalyzeRequest analyzeRequest,
                                                  T operationGet,
                                                  Map<String, Function<T, Object>> criteriaMap,
                                                  Map<String, String> descriptionMap) {
        List<Attribute> clientDefinedAttributeList = new ArrayList<>(criteriaMap.size());
        for (Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            Object value = criteriaMap.get(entry.getKey()).apply(operationGet);
            if (value != null) {
                Attribute attribute = new Attribute();
                attribute.setName(entry.getValue());
                attribute.setValue(value.toString());
                attribute.setDataType("STRING");
                clientDefinedAttributeList.add(attribute);
            }
        }
        if (analyzeRequest.getEventDataList() == null) {
            analyzeRequest.setEventDataList(new EventDataList());
        }
        analyzeRequest.getEventDataList().setClientDefinedAttributeList(new ClientDefinedAttributeList(clientDefinedAttributeList));
    }

}
