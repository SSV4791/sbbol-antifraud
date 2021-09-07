package ru.sberbank.pprb.sbbol.antifraud.service.mapper;

import java.util.UUID;

public interface CommonMapper {

    default UUID stringToUuid(String s) {
        return s == null ? null : UUID.fromString(s);
    }

    default String uuidToString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

}
