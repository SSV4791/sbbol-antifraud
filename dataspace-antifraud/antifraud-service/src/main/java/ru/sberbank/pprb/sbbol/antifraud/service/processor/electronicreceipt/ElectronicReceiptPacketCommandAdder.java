package ru.sberbank.pprb.sbbol.antifraud.service.processor.electronicreceipt;

import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.packet.ElectronicReceiptOperationRef;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;

import java.util.UUID;

/**
 * Сервис добавления команд создания и обновления записей электронных чеков в пакет команд
 */
public class ElectronicReceiptPacketCommandAdder {

    private ElectronicReceiptPacketCommandAdder() {
    }

    /**
     * Добавление команды создания записи на основе модели электронного чека в пакет команд
     *
     * @param packet    покет команд
     * @param operation модель платежного поручения
     * @return идентификатор запроса (UUID)
     */
    public static RequestId addCreateCommandToPaket(Packet packet, ElectronicReceiptOperation operation) {
        RequestId requestId = new RequestId(UUID.randomUUID());
        packet.electronicReceiptOperation.create(param -> {
            param.setRequestId(requestId.getId().toString());
            param.setTimeStamp(operation.getSign().getSignTime());
            param.setEpkId(operation.getOrgGuid());
            param.setDigitalId(operation.getDigitalId());
            param.setPrivateIpAddress(operation.getPrivateIpAddress());
            param.setUserGuid(operation.getSign().getUserGuid().toString());
            param.setTbCode(operation.getDocument().getPayer().getTbCode());
            param.setHttpAccept(operation.getDeviceRequest().getHttpAccept());
            param.setHttpReferer(operation.getDeviceRequest().getHttpReferer());
            param.setHttpAcceptChars(operation.getDeviceRequest().getHttpAcceptChars());
            param.setHttpAcceptEncoding(operation.getDeviceRequest().getHttpAcceptEncoding());
            param.setHttpAcceptLanguage(operation.getDeviceRequest().getHttpAcceptLanguage());
            param.setIpAddress(operation.getDeviceRequest().getIpAddress());
            param.setUserAgent(operation.getDeviceRequest().getUserAgent());
            param.setDevicePrint(operation.getDeviceRequest().getDevicePrint());
            param.setDocId(operation.getDocument().getId().toString());
            param.setDocNumber(operation.getDocument().getNumber());
            param.setDocDate(operation.getDocument().getDate());
            param.setAmount(operation.getDocument().getAmount());
            param.setCurrency(operation.getDocument().getCurrency());
            param.setAccountNumber(operation.getDocument().getPayer().getAccountNumber());
            param.setCodeBic(operation.getDocument().getPayer().getCodeBic());
            param.setDestination(operation.getDocument().getDestination());
            param.setPayerName(operation.getDocument().getPayer().getName());
            param.setPayerInn(operation.getDocument().getPayer().getInn());
            param.setFirstName(operation.getDocument().getReceiver().getFirstName());
            param.setSecondName(operation.getDocument().getReceiver().getSecondName());
            param.setMiddleName(operation.getDocument().getReceiver().getMiddleName());
            param.setBirthDay(operation.getDocument().getReceiver().getBirthDay());
            param.setDulType(operation.getDocument().getReceiver().getDulType());
            param.setDulSerieNumber(operation.getDocument().getReceiver().getDulSerieNumber());
            param.setDulWhoIssue(operation.getDocument().getReceiver().getDulWhoIssue());
            param.setDulDateIssue(operation.getDocument().getReceiver().getDulDateIssue());
            param.setDulCodeIssue(operation.getDocument().getReceiver().getDulCodeIssue());
            param.setReceiptDate(operation.getDocument().getReceipt().getReceiptDate());
            param.setReceiptTbCode(operation.getDocument().getReceipt().getReceiptTbCode());
            param.setReceiptOsbNumber(operation.getDocument().getReceipt().getReceiptOsbNumber());
            param.setReceiptVspNumber(operation.getDocument().getReceipt().getReceiptVspNumber());
            param.setReceiptPlaceName(operation.getDocument().getReceipt().getReceiptPlaceName());
            param.setReceiptPlaceAddress(operation.getDocument().getReceipt().getReceiptPlaceAddress());
            if (operation.getSign().getSignNumber() == 1) {
                param.setTimeOfOccurrence(operation.getSign().getSignTime());
                param.setFirstSignTime(operation.getSign().getSignTime());
                param.setFirstSignIp(operation.getSign().getSignIpAddress());
                param.setFirstSignLogin(operation.getSign().getSignLogin());
                param.setFirstSignCryptoprofile(operation.getSign().getSignCryptoprofile());
                param.setFirstSignCryptoprofileType(operation.getSign().getSignCryptoprofileType());
                param.setFirstSignChannel(operation.getSign().getSignChannel());
                param.setFirstSignToken(operation.getSign().getSignToken());
                param.setFirstSignType(operation.getSign().getSignType());
                param.setFirstSignImsi(operation.getSign().getSignImsi());
                param.setFirstSignCertId(operation.getSign().getSignCertId());
                param.setFirstSignPhone(operation.getSign().getSignPhone());
                param.setFirstSignEmail(operation.getSign().getSignEmail());
            } else if (operation.getSign().getSignNumber() == 2) {
                param.setSecondSignTime(operation.getSign().getSignTime());
                param.setSecondSignIp(operation.getSign().getSignIpAddress());
                param.setSecondSignLogin(operation.getSign().getSignLogin());
                param.setSecondSignCryptoprofile(operation.getSign().getSignCryptoprofile());
                param.setSecondSignCryptoprofileType(operation.getSign().getSignCryptoprofileType());
                param.setSecondSignChannel(operation.getSign().getSignChannel());
                param.setSecondSignToken(operation.getSign().getSignToken());
                param.setSecondSignType(operation.getSign().getSignType());
                param.setSecondSignImsi(operation.getSign().getSignImsi());
                param.setSecondSignCertId(operation.getSign().getSignCertId());
                param.setSecondSignPhone(operation.getSign().getSignPhone());
                param.setSecondSignEmail(operation.getSign().getSignEmail());
            }
            param.setSenderSignTime(operation.getSign().getSignTime());
            param.setSenderIp(operation.getSign().getSignIpAddress());
            param.setSenderLogin(operation.getSign().getSignLogin());
            param.setSenderCryptoprofile(operation.getSign().getSignCryptoprofile());
            param.setSenderCryptoprofileType(operation.getSign().getSignCryptoprofileType());
            param.setSenderSignChannel(operation.getSign().getSignChannel());
            param.setSenderToken(operation.getSign().getSignToken());
            param.setSenderSignType(operation.getSign().getSignType());
            param.setSenderSignImsi(operation.getSign().getSignImsi());
            param.setSenderCertId(operation.getSign().getSignCertId());
            param.setSenderPhone(operation.getSign().getSignPhone());
            param.setSenderEmail(operation.getSign().getSignEmail());
        });
        return requestId;
    }

    /**
     * Добавление команды обновления записи на основе модели электронного чека в пакет команд
     *
     * @param packet    покет команд
     * @param operation модель платежного поручения
     * @param objectId  идентификатор записи в базе данных
     */
    public static void addUpdateCommandToPacket(Packet packet, ElectronicReceiptOperation operation, String objectId) {
        ElectronicReceiptOperationRef ref = ElectronicReceiptOperationRef.of(objectId);
        packet.electronicReceiptOperation.update(ref, param -> {
            param.setReceiptPlaceAddress(operation.getDocument().getReceipt().getReceiptPlaceAddress());
            param.setReceiptPlaceName(operation.getDocument().getReceipt().getReceiptPlaceName());
            param.setReceiptVspNumber(operation.getDocument().getReceipt().getReceiptVspNumber());
            param.setReceiptOsbNumber(operation.getDocument().getReceipt().getReceiptOsbNumber());
            param.setReceiptTbCode(operation.getDocument().getReceipt().getReceiptTbCode());
            param.setReceiptDate(operation.getDocument().getReceipt().getReceiptDate());
            param.setDulCodeIssue(operation.getDocument().getReceiver().getDulCodeIssue());
            param.setDulDateIssue(operation.getDocument().getReceiver().getDulDateIssue());
            param.setDulWhoIssue(operation.getDocument().getReceiver().getDulWhoIssue());
            param.setDulSerieNumber(operation.getDocument().getReceiver().getDulSerieNumber());
            param.setDulType(operation.getDocument().getReceiver().getDulType());
            param.setBirthDay(operation.getDocument().getReceiver().getBirthDay());
            param.setMiddleName(operation.getDocument().getReceiver().getMiddleName());
            param.setSecondName(operation.getDocument().getReceiver().getSecondName());
            param.setFirstName(operation.getDocument().getReceiver().getFirstName());
            param.setPayerInn(operation.getDocument().getPayer().getInn());
            param.setPayerName(operation.getDocument().getPayer().getName());
            param.setDestination(operation.getDocument().getDestination());
            param.setCodeBic(operation.getDocument().getPayer().getCodeBic());
            param.setAccountNumber(operation.getDocument().getPayer().getAccountNumber());
            param.setCurrency(operation.getDocument().getCurrency());
            param.setAmount(operation.getDocument().getAmount());
            param.setDocDate(operation.getDocument().getDate());
            param.setDocNumber(operation.getDocument().getNumber());
            param.setDocId(operation.getDocument().getId().toString());
            param.setDevicePrint(operation.getDeviceRequest().getDevicePrint());
            param.setUserAgent(operation.getDeviceRequest().getUserAgent());
            param.setIpAddress(operation.getDeviceRequest().getIpAddress());
            param.setHttpAcceptLanguage(operation.getDeviceRequest().getHttpAcceptLanguage());
            param.setHttpAcceptEncoding(operation.getDeviceRequest().getHttpAcceptEncoding());
            param.setHttpAcceptChars(operation.getDeviceRequest().getHttpAcceptChars());
            param.setHttpReferer(operation.getDeviceRequest().getHttpReferer());
            param.setHttpAccept(operation.getDeviceRequest().getHttpAccept());
            param.setTbCode(operation.getDocument().getPayer().getTbCode());
            param.setUserGuid(operation.getSign().getUserGuid().toString());
            param.setPrivateIpAddress(operation.getPrivateIpAddress());
            param.setDigitalId(operation.getDigitalId());
            param.setEpkId(operation.getOrgGuid());
            param.setTimeStamp(operation.getSign().getSignTime());
            if (operation.getSign().getSignNumber() == 1) {
                param.setFirstSignEmail(operation.getSign().getSignEmail());
                param.setFirstSignPhone(operation.getSign().getSignPhone());
                param.setFirstSignCertId(operation.getSign().getSignCertId());
                param.setFirstSignImsi(operation.getSign().getSignImsi());
                param.setFirstSignType(operation.getSign().getSignType());
                param.setFirstSignToken(operation.getSign().getSignToken());
                param.setFirstSignChannel(operation.getSign().getSignChannel());
                param.setFirstSignCryptoprofileType(operation.getSign().getSignCryptoprofileType());
                param.setFirstSignCryptoprofile(operation.getSign().getSignCryptoprofile());
                param.setFirstSignLogin(operation.getSign().getSignLogin());
                param.setFirstSignIp(operation.getSign().getSignIpAddress());
                param.setFirstSignTime(operation.getSign().getSignTime());
                param.setTimeOfOccurrence(operation.getSign().getSignTime());
            } else if (operation.getSign().getSignNumber() == 2) {
                param.setSecondSignEmail(operation.getSign().getSignEmail());
                param.setSecondSignPhone(operation.getSign().getSignPhone());
                param.setSecondSignCertId(operation.getSign().getSignCertId());
                param.setSecondSignImsi(operation.getSign().getSignImsi());
                param.setSecondSignType(operation.getSign().getSignType());
                param.setSecondSignToken(operation.getSign().getSignToken());
                param.setSecondSignChannel(operation.getSign().getSignChannel());
                param.setSecondSignCryptoprofileType(operation.getSign().getSignCryptoprofileType());
                param.setSecondSignCryptoprofile(operation.getSign().getSignCryptoprofile());
                param.setSecondSignLogin(operation.getSign().getSignLogin());
                param.setSecondSignIp(operation.getSign().getSignIpAddress());
                param.setSecondSignTime(operation.getSign().getSignTime());
            }
            param.setSenderEmail(operation.getSign().getSignEmail());
            param.setSenderPhone(operation.getSign().getSignPhone());
            param.setSenderCertId(operation.getSign().getSignCertId());
            param.setSenderSignImsi(operation.getSign().getSignImsi());
            param.setSenderSignType(operation.getSign().getSignType());
            param.setSenderToken(operation.getSign().getSignToken());
            param.setSenderSignChannel(operation.getSign().getSignChannel());
            param.setSenderCryptoprofileType(operation.getSign().getSignCryptoprofileType());
            param.setSenderCryptoprofile(operation.getSign().getSignCryptoprofile());
            param.setSenderLogin(operation.getSign().getSignLogin());
            param.setSenderIp(operation.getSign().getSignIpAddress());
            param.setSenderSignTime(operation.getSign().getSignTime());
        });
    }

}
