package ru.sberbank.pprb.sbbol.antifraud.service.processor.sbp;

import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.packet.SbpPaymentOperationRef;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;

import java.util.UUID;

/**
 * Сервис добавления команд создания и обновления записей платежных поручений СБП в пакет команд
 */
public final class SbpPaymentPacketCommandAdder {

    private SbpPaymentPacketCommandAdder() {
    }

    /**
     * Добавление команды создания записи на основе модели платежного поручения СБП в пакет команд
     *
     * @param packet    покет команд
     * @param operation модель платежного поручения СБП
     * @return идентификатор запроса (UUID)
     */
    public static RequestId addCreateCommandToPacket(Packet packet, SbpPaymentOperation operation) {
        RequestId requestId = new RequestId(UUID.randomUUID());
        packet.sbpPaymentOperation.create(param -> {
            if (!operation.getMappedSigns().isEmpty()) {
                param.setSenderIp(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getIpAddress());
                param.setSenderSignTime(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignTime());
                param.setSenderCryptoprofile(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignCryptoprofile());
                param.setSenderLogin(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignLogin());
                param.setSenderSignChannel(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getChannelIndicator());
                param.setSenderCryptoprofileType(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignCryptoprofileType());
                param.setSenderSignType(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignType());
                param.setSenderToken(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignToken());
                param.setSenderCertId(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignCertId());
                param.setSenderSignImsi(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignImsi());
                param.setSenderEmail(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignEmail());
                param.setSenderPhone(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignPhone());
                param.setSenderSource(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignSource());

                param.setFirstSignIp(operation.getMappedSigns().get(0).getIpAddress());
                param.setFirstSignTime(operation.getMappedSigns().get(0).getSignTime());
                param.setFirstSignCryptoprofile(operation.getMappedSigns().get(0).getSignCryptoprofile());
                param.setFirstSignLogin(operation.getMappedSigns().get(0).getSignLogin());
                param.setFirstSignChannel(operation.getMappedSigns().get(0).getChannelIndicator());
                param.setFirstSignCryptoprofileType(operation.getMappedSigns().get(0).getSignCryptoprofileType());
                param.setFirstSignType(operation.getMappedSigns().get(0).getSignType());
                param.setFirstSignToken(operation.getMappedSigns().get(0).getSignToken());
                param.setFirstSignCertId(operation.getMappedSigns().get(0).getSignCertId());
                param.setFirstSignImsi(operation.getMappedSigns().get(0).getSignImsi());
                param.setFirstSignEmail(operation.getMappedSigns().get(0).getSignEmail());
                param.setFirstSignPhone(operation.getMappedSigns().get(0).getSignPhone());
                param.setFirstSignSource(operation.getMappedSigns().get(0).getSignSource());

                param.setHttpReferer(operation.getMappedSigns().get(0).getHttpReferer());
                param.setHttpAccept(operation.getMappedSigns().get(0).getHttpAccept());
                param.setHttpAcceptEncoding(operation.getMappedSigns().get(0).getHttpAcceptEncoding());
                param.setHttpAcceptChars(operation.getMappedSigns().get(0).getHttpAcceptChars());
                param.setIpAddress(operation.getMappedSigns().get(0).getIpAddress());
                param.setHttpAcceptLanguage(operation.getMappedSigns().get(0).getHttpAcceptLanguage());
                param.setUserAgent(operation.getMappedSigns().get(0).getUserAgent());
                param.setPrivateIpAddress(operation.getMappedSigns().get(0).getPrivateIpAddress());
                param.setMobSdkData(operation.getMappedSigns().get(0).getMobSdkData());
                param.setDevicePrint(operation.getMappedSigns().get(0).getDevicePrint());
                param.setChannelIndicator(operation.getMappedSigns().get(0).getChannelIndicator());
                param.setTbCode(operation.getMappedSigns().get(0).getTbCode());
                param.setUserGuid(operation.getMappedSigns().get(0).getUserGuid() != null ?
                        operation.getMappedSigns().get(0).getUserGuid().toString() : null);
                param.setClientDefinedChannelIndicator(operation.getMappedSigns().get(0).getClientDefinedChannelIndicator());
            }

            if (operation.getDocument().getReceiver() != null) {
                param.setOtherAccName(operation.getDocument().getReceiver().getOtherAccName());
                param.setOtherBicCode(operation.getDocument().getReceiver().getOtherBicCode());
                param.setReceiverInn(operation.getDocument().getReceiver().getInn());
                param.setReceiverBankName(operation.getDocument().getReceiver().getBankName());
                param.setReceiverBankCountryCode(operation.getDocument().getReceiver().getBankCountryCode());
                param.setReceiverBankCorrAcc(operation.getDocument().getReceiver().getBankCorrAcc());
                param.setReceiverBankId(operation.getDocument().getReceiver().getBankId());
                param.setReceiverDocument(operation.getDocument().getReceiver().getDocument());
                param.setReceiverDocumentType(operation.getDocument().getReceiver().getDocumentType());
                param.setReceiverPhoneNumber(operation.getDocument().getReceiver().getPhoneNumber());
                param.setReceiverAccount(operation.getDocument().getReceiver().getAccount());
            }

            if (operation.getDocument().getPayer() != null) {
                param.setAccountNumber(operation.getDocument().getPayer().getAccountNumber());
                param.setPayerFinancialName(operation.getDocument().getPayer().getFinancialName());
                param.setPayerOsbNum(operation.getDocument().getPayer().getOsbNum());
                param.setPayerVspNum(operation.getDocument().getPayer().getVspNum());
                param.setPayerAccBalance(operation.getDocument().getPayer().getAccBalance());
                param.setPayerAccCreateDate(operation.getDocument().getPayer().getAccCreateDate());
                param.setPayerBic(operation.getDocument().getPayer().getBic());
                param.setPayerDocumentNumber(operation.getDocument().getPayer().getDocumentNumber());
                param.setPayerDocumentType(operation.getDocument().getPayer().getDocumentType());
                param.setPayerSegment(operation.getDocument().getPayer().getSegment());
                param.setPayerInn(operation.getDocument().getPayer().getInn());
            }

            param.setDocId(operation.getDocument().getId().toString());
            param.setDocNumber(operation.getDocument().getNumber());
            param.setDocDate(operation.getDocument().getDate());
            param.setAmount(operation.getDocument().getAmount());
            param.setCurrency(operation.getDocument().getCurrency());
            param.setIdOperationOPKC(operation.getDocument().getIdOperationOPKC());
            param.setDestination(operation.getDocument().getDestination());

            param.setRequestId(requestId.getId().toString());
            param.setTimeStamp(operation.getTimeStamp());
            param.setEpkId(operation.getOrgGuid());
            param.setDigitalId(operation.getDigitalId());
            param.setTimeOfOccurrence(operation.getTimeOfOccurrence());
        });
        return requestId;
    }

    /**
     * Добавление команды обновления записи на основе модели платежного поручения СБП в пакет команд
     *
     * @param packet    покет команд
     * @param operation модель платежного поручения СБП
     * @param objectId  идентификатор записи в базе данных
     */
    public static void addUpdateCommandToPacket(Packet packet, SbpPaymentOperation operation, String objectId) {
        SbpPaymentOperationRef ref = SbpPaymentOperationRef.of(objectId);
        packet.sbpPaymentOperation.update(ref, param -> {
            if (!operation.getMappedSigns().isEmpty()) {
                param.setSenderSource(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignSource());
                param.setSenderPhone(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignPhone());
                param.setSenderEmail(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignEmail());
                param.setSenderSignImsi(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignImsi());
                param.setSenderCertId(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignCertId());
                param.setSenderToken(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignToken());
                param.setSenderSignType(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignType());
                param.setSenderCryptoprofileType(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignCryptoprofileType());
                param.setSenderSignChannel(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getChannelIndicator());
                param.setSenderLogin(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignLogin());
                param.setSenderCryptoprofile(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignCryptoprofile());
                param.setSenderSignTime(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getSignTime());
                param.setSenderIp(operation.getMappedSigns().get(operation.getMappedSigns().size() - 1).getIpAddress());

                param.setClientDefinedChannelIndicator(operation.getMappedSigns().get(0).getClientDefinedChannelIndicator());
                param.setFirstSignSource(operation.getMappedSigns().get(0).getSignSource());
                param.setFirstSignPhone(operation.getMappedSigns().get(0).getSignPhone());
                param.setFirstSignEmail(operation.getMappedSigns().get(0).getSignEmail());
                param.setFirstSignImsi(operation.getMappedSigns().get(0).getSignImsi());
                param.setFirstSignCertId(operation.getMappedSigns().get(0).getSignCertId());
                param.setFirstSignToken(operation.getMappedSigns().get(0).getSignToken());
                param.setFirstSignType(operation.getMappedSigns().get(0).getSignType());
                param.setFirstSignCryptoprofileType(operation.getMappedSigns().get(0).getSignCryptoprofileType());
                param.setFirstSignChannel(operation.getMappedSigns().get(0).getChannelIndicator());
                param.setFirstSignLogin(operation.getMappedSigns().get(0).getSignLogin());
                param.setFirstSignCryptoprofile(operation.getMappedSigns().get(0).getSignCryptoprofile());
                param.setFirstSignTime(operation.getMappedSigns().get(0).getSignTime());
                param.setFirstSignIp(operation.getMappedSigns().get(0).getIpAddress());

                param.setUserGuid(operation.getMappedSigns().get(0).getUserGuid() != null ?
                        operation.getMappedSigns().get(0).getUserGuid().toString() : null);
                param.setTbCode(operation.getMappedSigns().get(0).getTbCode());
                param.setChannelIndicator(operation.getMappedSigns().get(0).getChannelIndicator());
                param.setDevicePrint(operation.getMappedSigns().get(0).getDevicePrint());
                param.setMobSdkData(operation.getMappedSigns().get(0).getMobSdkData());
                param.setPrivateIpAddress(operation.getMappedSigns().get(0).getPrivateIpAddress());
                param.setUserAgent(operation.getMappedSigns().get(0).getUserAgent());
                param.setHttpAcceptLanguage(operation.getMappedSigns().get(0).getHttpAcceptLanguage());
                param.setIpAddress(operation.getMappedSigns().get(0).getIpAddress());
                param.setHttpAcceptChars(operation.getMappedSigns().get(0).getHttpAcceptChars());
                param.setHttpAcceptEncoding(operation.getMappedSigns().get(0).getHttpAcceptEncoding());
                param.setHttpAccept(operation.getMappedSigns().get(0).getHttpAccept());
                param.setHttpReferer(operation.getMappedSigns().get(0).getHttpReferer());
            }

            if (operation.getDocument().getReceiver() != null) {
                param.setReceiverAccount(operation.getDocument().getReceiver().getAccount());
                param.setReceiverPhoneNumber(operation.getDocument().getReceiver().getPhoneNumber());
                param.setReceiverDocumentType(operation.getDocument().getReceiver().getDocumentType());
                param.setReceiverDocument(operation.getDocument().getReceiver().getDocument());
                param.setReceiverBankId(operation.getDocument().getReceiver().getBankId());
                param.setReceiverBankCorrAcc(operation.getDocument().getReceiver().getBankCorrAcc());
                param.setReceiverBankCountryCode(operation.getDocument().getReceiver().getBankCountryCode());
                param.setReceiverBankName(operation.getDocument().getReceiver().getBankName());
                param.setReceiverInn(operation.getDocument().getReceiver().getInn());
                param.setOtherBicCode(operation.getDocument().getReceiver().getOtherBicCode());
                param.setOtherAccName(operation.getDocument().getReceiver().getOtherAccName());
            }

            if (operation.getDocument().getPayer() != null) {
                param.setPayerInn(operation.getDocument().getPayer().getInn());
                param.setPayerSegment(operation.getDocument().getPayer().getSegment());
                param.setPayerDocumentType(operation.getDocument().getPayer().getDocumentType());
                param.setPayerDocumentNumber(operation.getDocument().getPayer().getDocumentNumber());
                param.setPayerBic(operation.getDocument().getPayer().getBic());
                param.setPayerAccCreateDate(operation.getDocument().getPayer().getAccCreateDate());
                param.setPayerAccBalance(operation.getDocument().getPayer().getAccBalance());
                param.setPayerVspNum(operation.getDocument().getPayer().getVspNum());
                param.setPayerOsbNum(operation.getDocument().getPayer().getOsbNum());
                param.setPayerFinancialName(operation.getDocument().getPayer().getFinancialName());
                param.setAccountNumber(operation.getDocument().getPayer().getAccountNumber());
            }

            param.setDestination(operation.getDocument().getDestination());
            param.setIdOperationOPKC(operation.getDocument().getIdOperationOPKC());
            param.setCurrency(operation.getDocument().getCurrency());
            param.setAmount(operation.getDocument().getAmount());
            param.setDocDate(operation.getDocument().getDate());
            param.setDocNumber(operation.getDocument().getNumber());
            param.setDocId(operation.getDocument().getId().toString());

            param.setTimeOfOccurrence(operation.getTimeOfOccurrence());
            param.setDigitalId(operation.getDigitalId());
            param.setEpkId(operation.getOrgGuid());
            param.setTimeStamp(operation.getTimeStamp());
        });
    }
}
