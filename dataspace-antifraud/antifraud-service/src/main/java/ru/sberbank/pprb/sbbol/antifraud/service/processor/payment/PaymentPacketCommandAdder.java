package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.packet.PaymentOperationRef;

import java.util.UUID;

/**
 * Сервис добавления команд создания и обновления записей платежных поручений в пакет команд
 */
public final class PaymentPacketCommandAdder {

    private PaymentPacketCommandAdder() {

    }

    /**
     * Добавление команды создания записи на основе модели платежного поручения в пакет команд
     *
     * @param packet  покет команд
     * @param payment модель платежного поручения
     * @return идентификатор запроса (UUID)
     */
    public static RequestId addCreateCommandToPaket(Packet packet, PaymentOperation payment) {
        RequestId requestId = new RequestId(UUID.randomUUID());
        packet.paymentOperation.create(param -> {
            param.setRequestId(requestId.getId().toString());
            param.setTimeStamp(payment.getTimeStamp());
            param.setEpkId(payment.getOrgGuid());
            param.setDigitalId(payment.getDigitalId());
            param.setTimeOfOccurrence(payment.getTimeOfOccurrence());

            param.setDocId(payment.getDocument().getId().toString());
            param.setDocNumber(payment.getDocument().getNumber());
            param.setDocDate(payment.getDocument().getDate());
            param.setAmount(payment.getDocument().getAmount());
            param.setCurrency(payment.getDocument().getCurrency());
            param.setExecutionSpeed(payment.getDocument().getExecutionSpeed());
            param.setOtherAccBankType(payment.getDocument().getOtherAccBankType());
            param.setOtherAccOwnershipType(payment.getDocument().getOtherAccOwnershipType());
            param.setTransferMediumType(payment.getDocument().getTransferMediumType());
            param.setDestination(payment.getDocument().getDestination());

            if (payment.getDocument().getPayer() != null) {
                param.setAccountNumber(payment.getDocument().getPayer().getAccountNumber());
                param.setPayerInn(payment.getDocument().getPayer().getInn());
            }

            if (payment.getDocument().getReceiver() != null) {
                param.setOtherAccName(payment.getDocument().getReceiver().getOtherAccName());
                param.setBalAccNumber(payment.getDocument().getReceiver().getBalAccNumber());
                param.setOtherBicCode(payment.getDocument().getReceiver().getOtherBicCode());
                param.setOtherAccType(payment.getDocument().getReceiver().getOtherAccType());
                param.setReceiverInn(payment.getDocument().getReceiver().getInn());
            }

            if (!payment.getMappedSigns().isEmpty()) {
                param.setHttpAccept(payment.getMappedSigns().get(0).getHttpAccept());
                param.setHttpReferer(payment.getMappedSigns().get(0).getHttpReferer());
                param.setHttpAcceptChars(payment.getMappedSigns().get(0).getHttpAcceptChars());
                param.setHttpAcceptEncoding(payment.getMappedSigns().get(0).getHttpAcceptEncoding());
                param.setHttpAcceptLanguage(payment.getMappedSigns().get(0).getHttpAcceptLanguage());
                param.setIpAddress(payment.getMappedSigns().get(0).getIpAddress());
                param.setPrivateIpAddress(payment.getMappedSigns().get(0).getPrivateIpAddress());
                param.setUserAgent(payment.getMappedSigns().get(0).getUserAgent());
                param.setDevicePrint(payment.getMappedSigns().get(0).getDevicePrint());
                param.setMobSdkData(payment.getMappedSigns().get(0).getMobSdkData());
                param.setChannelIndicator(payment.getMappedSigns().get(0).getChannelIndicator());
                param.setUserGuid(payment.getMappedSigns().get(0).getUserGuid() != null ?
                        payment.getMappedSigns().get(0).getUserGuid().toString() : null);
                param.setTbCode(payment.getMappedSigns().get(0).getTbCode());
                param.setClientDefinedChannelIndicator(payment.getMappedSigns().get(0).getClientDefinedChannelIndicator());

                param.setFirstSignTime(payment.getMappedSigns().get(0).getSignTime());
                param.setFirstSignIp(payment.getMappedSigns().get(0).getIpAddress());
                param.setFirstSignLogin(payment.getMappedSigns().get(0).getSignLogin());
                param.setFirstSignCryptoprofile(payment.getMappedSigns().get(0).getSignCryptoprofile());
                param.setFirstSignCryptoprofileType(payment.getMappedSigns().get(0).getSignCryptoprofileType());
                param.setFirstSignChannel(payment.getMappedSigns().get(0).getChannelIndicator());
                param.setFirstSignToken(payment.getMappedSigns().get(0).getSignToken());
                param.setFirstSignType(payment.getMappedSigns().get(0).getSignType());
                param.setFirstSignImsi(payment.getMappedSigns().get(0).getSignImsi());
                param.setFirstSignCertId(payment.getMappedSigns().get(0).getSignCertId());
                param.setFirstSignPhone(payment.getMappedSigns().get(0).getSignPhone());
                param.setFirstSignEmail(payment.getMappedSigns().get(0).getSignEmail());
                param.setFirstSignSource(payment.getMappedSigns().get(0).getSignSource());

                param.setSenderSignTime(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignTime());
                param.setSenderIp(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getIpAddress());
                param.setSenderLogin(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignLogin());
                param.setSenderCryptoprofile(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignCryptoprofile());
                param.setSenderCryptoprofileType(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignCryptoprofileType());
                param.setSenderSignChannel(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getChannelIndicator());
                param.setSenderToken(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignToken());
                param.setSenderSignType(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignType());
                param.setSenderSignImsi(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignImsi());
                param.setSenderCertId(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignCertId());
                param.setSenderPhone(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignPhone());
                param.setSenderEmail(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignEmail());
                param.setSenderSource(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignSource());
            }

            if (payment.getMappedSigns().size() > 1) {
                param.setSecondSignTime(payment.getMappedSigns().get(1).getSignTime());
                param.setSecondSignIp(payment.getMappedSigns().get(1).getIpAddress());
                param.setSecondSignLogin(payment.getMappedSigns().get(1).getSignLogin());
                param.setSecondSignCryptoprofile(payment.getMappedSigns().get(1).getSignCryptoprofile());
                param.setSecondSignCryptoprofileType(payment.getMappedSigns().get(1).getSignCryptoprofileType());
                param.setSecondSignChannel(payment.getMappedSigns().get(1).getChannelIndicator());
                param.setSecondSignToken(payment.getMappedSigns().get(1).getSignToken());
                param.setSecondSignType(payment.getMappedSigns().get(1).getSignType());
                param.setSecondSignImsi(payment.getMappedSigns().get(1).getSignImsi());
                param.setSecondSignCertId(payment.getMappedSigns().get(1).getSignCertId());
                param.setSecondSignPhone(payment.getMappedSigns().get(1).getSignPhone());
                param.setSecondSignEmail(payment.getMappedSigns().get(1).getSignEmail());
                param.setSecondSignSource(payment.getMappedSigns().get(1).getSignSource());
            }

            if (payment.getMappedSigns().size() > 2) {
                param.setThirdSignTime(payment.getMappedSigns().get(2).getSignTime());
                param.setThirdSignIp(payment.getMappedSigns().get(2).getIpAddress());
                param.setThirdSignLogin(payment.getMappedSigns().get(2).getSignLogin());
                param.setThirdSignCryptoprofile(payment.getMappedSigns().get(2).getSignCryptoprofile());
                param.setThirdSignCryptoprofileType(payment.getMappedSigns().get(2).getSignCryptoprofileType());
                param.setThirdSignChannel(payment.getMappedSigns().get(2).getChannelIndicator());
                param.setThirdSignToken(payment.getMappedSigns().get(2).getSignToken());
                param.setThirdSignType(payment.getMappedSigns().get(2).getSignType());
                param.setThirdSignImsi(payment.getMappedSigns().get(2).getSignImsi());
                param.setThirdSignCertId(payment.getMappedSigns().get(2).getSignCertId());
                param.setThirdSignPhone(payment.getMappedSigns().get(2).getSignPhone());
                param.setThirdSignEmail(payment.getMappedSigns().get(2).getSignEmail());
                param.setThirdSignSource(payment.getMappedSigns().get(2).getSignSource());
            }
        });
        return requestId;
    }

    /**
     * Добавление команды обновления записи на основе модели платежного поручения в пакет команд
     *
     * @param packet   покет команд
     * @param payment  модель платежного поручения
     * @param objectId идентификатор записи в базе данных
     */
    public static void addUpdateCommandToPacket(Packet packet, PaymentOperation payment, String objectId) {
        PaymentOperationRef ref = PaymentOperationRef.of(objectId);
        packet.paymentOperation.update(ref, param -> {
            param.setTimeOfOccurrence(payment.getTimeOfOccurrence());
            param.setDigitalId(payment.getDigitalId());
            param.setEpkId(payment.getOrgGuid());
            param.setTimeStamp(payment.getTimeStamp());

            param.setDestination(payment.getDocument().getDestination());
            param.setTransferMediumType(payment.getDocument().getTransferMediumType());
            param.setOtherAccOwnershipType(payment.getDocument().getOtherAccOwnershipType());
            param.setOtherAccBankType(payment.getDocument().getOtherAccBankType());
            param.setExecutionSpeed(payment.getDocument().getExecutionSpeed());
            param.setCurrency(payment.getDocument().getCurrency());
            param.setAmount(payment.getDocument().getAmount());
            param.setDocDate(payment.getDocument().getDate());
            param.setDocNumber(payment.getDocument().getNumber());
            param.setDocId(payment.getDocument().getId().toString());

            if (payment.getDocument().getPayer() != null) {
                param.setPayerInn(payment.getDocument().getPayer().getInn());
                param.setAccountNumber(payment.getDocument().getPayer().getAccountNumber());
            }

            if (payment.getDocument().getReceiver() != null) {
                param.setReceiverInn(payment.getDocument().getReceiver().getInn());
                param.setOtherAccType(payment.getDocument().getReceiver().getOtherAccType());
                param.setOtherBicCode(payment.getDocument().getReceiver().getOtherBicCode());
                param.setBalAccNumber(payment.getDocument().getReceiver().getBalAccNumber());
                param.setOtherAccName(payment.getDocument().getReceiver().getOtherAccName());
            }

            if (!payment.getMappedSigns().isEmpty()) {
                param.setClientDefinedChannelIndicator(payment.getMappedSigns().get(0).getClientDefinedChannelIndicator());
                param.setTbCode(payment.getMappedSigns().get(0).getTbCode());
                param.setUserGuid(payment.getMappedSigns().get(0).getUserGuid() != null ?
                        payment.getMappedSigns().get(0).getUserGuid().toString() : null);
                param.setChannelIndicator(payment.getMappedSigns().get(0).getChannelIndicator());
                param.setMobSdkData(payment.getMappedSigns().get(0).getMobSdkData());
                param.setDevicePrint(payment.getMappedSigns().get(0).getDevicePrint());
                param.setUserAgent(payment.getMappedSigns().get(0).getUserAgent());
                param.setPrivateIpAddress(payment.getMappedSigns().get(0).getPrivateIpAddress());
                param.setIpAddress(payment.getMappedSigns().get(0).getIpAddress());
                param.setHttpAcceptLanguage(payment.getMappedSigns().get(0).getHttpAcceptLanguage());
                param.setHttpAcceptEncoding(payment.getMappedSigns().get(0).getHttpAcceptEncoding());
                param.setHttpAcceptChars(payment.getMappedSigns().get(0).getHttpAcceptChars());
                param.setHttpReferer(payment.getMappedSigns().get(0).getHttpReferer());
                param.setHttpAccept(payment.getMappedSigns().get(0).getHttpAccept());

                param.setFirstSignSource(payment.getMappedSigns().get(0).getSignSource());
                param.setFirstSignEmail(payment.getMappedSigns().get(0).getSignEmail());
                param.setFirstSignPhone(payment.getMappedSigns().get(0).getSignPhone());
                param.setFirstSignCertId(payment.getMappedSigns().get(0).getSignCertId());
                param.setFirstSignImsi(payment.getMappedSigns().get(0).getSignImsi());
                param.setFirstSignType(payment.getMappedSigns().get(0).getSignType());
                param.setFirstSignToken(payment.getMappedSigns().get(0).getSignToken());
                param.setFirstSignChannel(payment.getMappedSigns().get(0).getChannelIndicator());
                param.setFirstSignCryptoprofileType(payment.getMappedSigns().get(0).getSignCryptoprofileType());
                param.setFirstSignCryptoprofile(payment.getMappedSigns().get(0).getSignCryptoprofile());
                param.setFirstSignLogin(payment.getMappedSigns().get(0).getSignLogin());
                param.setFirstSignIp(payment.getMappedSigns().get(0).getIpAddress());
                param.setFirstSignTime(payment.getMappedSigns().get(0).getSignTime());

                param.setSenderSource(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignSource());
                param.setSenderEmail(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignEmail());
                param.setSenderPhone(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignPhone());
                param.setSenderCertId(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignCertId());
                param.setSenderSignImsi(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignImsi());
                param.setSenderSignType(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignType());
                param.setSenderToken(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignToken());
                param.setSenderSignChannel(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getChannelIndicator());
                param.setSenderCryptoprofileType(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignCryptoprofileType());
                param.setSenderCryptoprofile(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignCryptoprofile());
                param.setSenderLogin(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignLogin());
                param.setSenderIp(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getIpAddress());
                param.setSenderSignTime(payment.getMappedSigns().get(payment.getMappedSigns().size() - 1).getSignTime());
            }

            if (payment.getMappedSigns().size() > 1) {
                param.setSecondSignSource(payment.getMappedSigns().get(1).getSignSource());
                param.setSecondSignEmail(payment.getMappedSigns().get(1).getSignEmail());
                param.setSecondSignPhone(payment.getMappedSigns().get(1).getSignPhone());
                param.setSecondSignCertId(payment.getMappedSigns().get(1).getSignCertId());
                param.setSecondSignImsi(payment.getMappedSigns().get(1).getSignImsi());
                param.setSecondSignType(payment.getMappedSigns().get(1).getSignType());
                param.setSecondSignToken(payment.getMappedSigns().get(1).getSignToken());
                param.setSecondSignChannel(payment.getMappedSigns().get(1).getChannelIndicator());
                param.setSecondSignCryptoprofileType(payment.getMappedSigns().get(1).getSignCryptoprofileType());
                param.setSecondSignCryptoprofile(payment.getMappedSigns().get(1).getSignCryptoprofile());
                param.setSecondSignLogin(payment.getMappedSigns().get(1).getSignLogin());
                param.setSecondSignIp(payment.getMappedSigns().get(1).getIpAddress());
                param.setSecondSignTime(payment.getMappedSigns().get(1).getSignTime());
            }

            if (payment.getMappedSigns().size() > 2) {
                param.setThirdSignSource(payment.getMappedSigns().get(2).getSignSource());
                param.setThirdSignEmail(payment.getMappedSigns().get(2).getSignEmail());
                param.setThirdSignPhone(payment.getMappedSigns().get(2).getSignPhone());
                param.setThirdSignCertId(payment.getMappedSigns().get(2).getSignCertId());
                param.setThirdSignImsi(payment.getMappedSigns().get(2).getSignImsi());
                param.setThirdSignType(payment.getMappedSigns().get(2).getSignType());
                param.setThirdSignToken(payment.getMappedSigns().get(2).getSignToken());
                param.setThirdSignChannel(payment.getMappedSigns().get(2).getChannelIndicator());
                param.setThirdSignCryptoprofileType(payment.getMappedSigns().get(2).getSignCryptoprofileType());
                param.setThirdSignCryptoprofile(payment.getMappedSigns().get(2).getSignCryptoprofile());
                param.setThirdSignLogin(payment.getMappedSigns().get(2).getSignLogin());
                param.setThirdSignIp(payment.getMappedSigns().get(2).getIpAddress());
                param.setThirdSignTime(payment.getMappedSigns().get(2).getSignTime());
            }
        });
    }

}
