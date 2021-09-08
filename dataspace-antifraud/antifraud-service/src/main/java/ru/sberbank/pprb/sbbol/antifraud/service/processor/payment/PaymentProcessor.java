package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Amount;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.ClientDefinedAttributeList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.DeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.MessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.PayerAccount;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.ReceiverAccount;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.TransactionData;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.graph.with.PaymentOperationCollectionWith;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.grasp.PaymentOperationGrasp;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.SignMapper;
import sbp.sbt.sdk.DataspaceCorePacketClient;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу PaymentOperation.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class PaymentProcessor implements Processor<PaymentOperation, PaymentSendRequest> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    public static final String EPK_ID = "epkId";
    public static final String DIGITAL_ID = "digitalId";
    public static final String DOC_NUMBER = "docNumber";
    public static final String DOC_DATE = "docDate";
    public static final String RECEIVER_INN = "receiverInn";
    public static final String DESTINATION = "destination";
    public static final String PAYER_INN = "payerInn";
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
    public static final String SECOND_SIGN_TIME = "secondSignTime";
    public static final String SECOND_SIGN_IP = "secondSignIp";
    public static final String SECOND_SIGN_LOGIN = "secondSignLogin";
    public static final String SECOND_SIGN_CRYPTOPROFILE = "secondSignCryptoprofile";
    public static final String SECOND_SIGN_CRYPTOPROFILE_TYPE = "secondSignCryptoprofileType";
    public static final String SECOND_SIGN_CHANNEL = "secondSignChannel";
    public static final String SECOND_SIGN_TOKEN = "secondSignToken";
    public static final String SECOND_SIGN_TYPE = "secondSignType";
    public static final String SECOND_SIGN_IMSI = "secondSignImsi";
    public static final String SECOND_SIGN_CERT_ID = "secondSignCertId";
    public static final String SECOND_SIGN_PHONE = "secondSignPhone";
    public static final String SECOND_SIGN_EMAIL = "secondSignEmail";
    public static final String SECOND_SIGN_SOURCE = "secondSignSource";
    public static final String THIRD_SIGN_TIME = "thirdSignTime";
    public static final String THIRD_SIGN_IP = "thirdSignIp";
    public static final String THIRD_SIGN_LOGIN = "thirdSignLogin";
    public static final String THIRD_SIGN_CRYPTOPROFILE = "thirdSignCryptoprofile";
    public static final String THIRD_SIGN_CRYPTOPROFILE_TYPE = "thirdSignCryptoprofileType";
    public static final String THIRD_SIGN_CHANNEL = "thirdSignChannel";
    public static final String THIRD_SIGN_TOKEN = "thirdSignToken";
    public static final String THIRD_SIGN_TYPE = "thirdSignType";
    public static final String THIRD_SIGN_IMSI = "thirdSignImsi";
    public static final String THIRD_SIGN_CERT_ID = "thirdSignCertId";
    public static final String THIRD_SIGN_PHONE = "thirdSignPhone";
    public static final String THIRD_SIGN_EMAIL = "thirdSignEmail";
    public static final String THIRD_SIGN_SOURCE = "thirdSignSource";
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

    private static final Map<String, Function<PaymentOperationGet, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    private static final int CAPACITY = 60;

    static {
        Map<String, Function<PaymentOperationGet, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(EPK_ID, PaymentOperationGet::getEpkId);
        criteriaMap.put(DIGITAL_ID, PaymentOperationGet::getDigitalId);
        criteriaMap.put(DOC_NUMBER, PaymentOperationGet::getDocNumber);
        criteriaMap.put(DOC_DATE, PaymentOperationGet::getDocDate);
        criteriaMap.put(RECEIVER_INN, PaymentOperationGet::getReceiverInn);
        criteriaMap.put(DESTINATION, PaymentOperationGet::getDestination);
        criteriaMap.put(PAYER_INN, PaymentOperationGet::getPayerInn);
        criteriaMap.put(PRIVATE_IP_ADDRESS, PaymentOperationGet::getPrivateIpAddress);
        criteriaMap.put(FIRST_SIGN_TIME, PaymentOperationGet::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, PaymentOperationGet::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, PaymentOperationGet::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, PaymentOperationGet::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, PaymentOperationGet::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, PaymentOperationGet::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, PaymentOperationGet::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, PaymentOperationGet::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, PaymentOperationGet::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, PaymentOperationGet::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, PaymentOperationGet::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, PaymentOperationGet::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, PaymentOperationGet::getFirstSignSource);
        criteriaMap.put(SECOND_SIGN_TIME, PaymentOperationGet::getSecondSignTime);
        criteriaMap.put(SECOND_SIGN_IP, PaymentOperationGet::getSecondSignIp);
        criteriaMap.put(SECOND_SIGN_LOGIN, PaymentOperationGet::getSecondSignLogin);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE, PaymentOperationGet::getSecondSignCryptoprofile);
        criteriaMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, PaymentOperationGet::getSecondSignCryptoprofileType);
        criteriaMap.put(SECOND_SIGN_CHANNEL, PaymentOperationGet::getSecondSignChannel);
        criteriaMap.put(SECOND_SIGN_TOKEN, PaymentOperationGet::getSecondSignToken);
        criteriaMap.put(SECOND_SIGN_TYPE, PaymentOperationGet::getSecondSignType);
        criteriaMap.put(SECOND_SIGN_IMSI, PaymentOperationGet::getSecondSignImsi);
        criteriaMap.put(SECOND_SIGN_CERT_ID, PaymentOperationGet::getSecondSignCertId);
        criteriaMap.put(SECOND_SIGN_PHONE, PaymentOperationGet::getSecondSignPhone);
        criteriaMap.put(SECOND_SIGN_EMAIL, PaymentOperationGet::getSecondSignEmail);
        criteriaMap.put(SECOND_SIGN_SOURCE, PaymentOperationGet::getSecondSignSource);
        criteriaMap.put(THIRD_SIGN_TIME, PaymentOperationGet::getThirdSignTime);
        criteriaMap.put(THIRD_SIGN_IP, PaymentOperationGet::getThirdSignIp);
        criteriaMap.put(THIRD_SIGN_LOGIN, PaymentOperationGet::getThirdSignLogin);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE, PaymentOperationGet::getThirdSignCryptoprofile);
        criteriaMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, PaymentOperationGet::getThirdSignCryptoprofileType);
        criteriaMap.put(THIRD_SIGN_CHANNEL, PaymentOperationGet::getThirdSignChannel);
        criteriaMap.put(THIRD_SIGN_TOKEN, PaymentOperationGet::getThirdSignToken);
        criteriaMap.put(THIRD_SIGN_TYPE, PaymentOperationGet::getThirdSignType);
        criteriaMap.put(THIRD_SIGN_IMSI, PaymentOperationGet::getThirdSignImsi);
        criteriaMap.put(THIRD_SIGN_CERT_ID, PaymentOperationGet::getThirdSignCertId);
        criteriaMap.put(THIRD_SIGN_PHONE, PaymentOperationGet::getThirdSignPhone);
        criteriaMap.put(THIRD_SIGN_EMAIL, PaymentOperationGet::getThirdSignEmail);
        criteriaMap.put(THIRD_SIGN_SOURCE, PaymentOperationGet::getThirdSignSource);
        criteriaMap.put(SENDER_SIGN_TIME, PaymentOperationGet::getSenderSignTime);
        criteriaMap.put(SENDER_IP, PaymentOperationGet::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, PaymentOperationGet::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, PaymentOperationGet::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, PaymentOperationGet::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, PaymentOperationGet::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, PaymentOperationGet::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, PaymentOperationGet::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, PaymentOperationGet::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, PaymentOperationGet::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, PaymentOperationGet::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, PaymentOperationGet::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, PaymentOperationGet::getSenderSource);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(EPK_ID, "ЕПК.id");
        descriptionMap.put(DIGITAL_ID, "Личный кабинет");
        descriptionMap.put(DOC_NUMBER, "Номер документа");
        descriptionMap.put(DOC_DATE, "Дата документа");
        descriptionMap.put(RECEIVER_INN, "ИНН получателя");
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
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
        descriptionMap.put(SECOND_SIGN_TIME, "2-я подпись Время подписи");
        descriptionMap.put(SECOND_SIGN_IP, "2-я подпись IP адрес");
        descriptionMap.put(SECOND_SIGN_LOGIN, "2-я подпись Логин");
        descriptionMap.put(SECOND_SIGN_CRYPTOPROFILE, "2-я подпись Наименование криптопрофиля");
        descriptionMap.put(SECOND_SIGN_CRYPTOPROFILE_TYPE, "2-я подпись Тип криптопрофиля");
        descriptionMap.put(SECOND_SIGN_CHANNEL, "2-я подпись Канал подписи");
        descriptionMap.put(SECOND_SIGN_TOKEN, "2-я подпись Данные Токена");
        descriptionMap.put(SECOND_SIGN_TYPE, "2-я подпись Тип подписи");
        descriptionMap.put(SECOND_SIGN_IMSI, "2-я подпись IMSI");
        descriptionMap.put(SECOND_SIGN_CERT_ID, "2-я подпись Идентификатор сертификата");
        descriptionMap.put(SECOND_SIGN_PHONE, "2-я подпись Номер телефона");
        descriptionMap.put(SECOND_SIGN_EMAIL, "2-я подпись Адрес электронной почты");
        descriptionMap.put(SECOND_SIGN_SOURCE, "2-я подпись Канал");
        descriptionMap.put(THIRD_SIGN_TIME, "3-я подпись Время подписи");
        descriptionMap.put(THIRD_SIGN_IP, "3-я подпись IP адрес");
        descriptionMap.put(THIRD_SIGN_LOGIN, "3-я подпись Логин");
        descriptionMap.put(THIRD_SIGN_CRYPTOPROFILE, "3-я подпись Наименование криптопрофиля");
        descriptionMap.put(THIRD_SIGN_CRYPTOPROFILE_TYPE, "3-я подпись Тип криптопрофиля");
        descriptionMap.put(THIRD_SIGN_CHANNEL, "3-я подпись Канал подписи");
        descriptionMap.put(THIRD_SIGN_TOKEN, "3-я подпись Данные Токена");
        descriptionMap.put(THIRD_SIGN_TYPE, "3-я подпись Тип подписи");
        descriptionMap.put(THIRD_SIGN_IMSI, "3-я подпись IMSI");
        descriptionMap.put(THIRD_SIGN_CERT_ID, "3-я подпись Идентификатор сертификата");
        descriptionMap.put(THIRD_SIGN_PHONE, "3-я подпись Номер телефона");
        descriptionMap.put(THIRD_SIGN_EMAIL, "3-я подпись Адрес электронной почты");
        descriptionMap.put(THIRD_SIGN_SOURCE, "3-я подпись Канал");
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
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    private final DataspaceCorePacketClient packetClient;
    private final DataspaceCoreSearchClient searchClient;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;

    private final String endPoint;

    public PaymentProcessor(DataspaceCorePacketClient packetClient, DataspaceCoreSearchClient searchClient, RestTemplate restTemplate,
                            ObjectMapper objectMapper, HttpHeaders httpHeaders, @Value("${fpis.endpoint}") String endPoint) {
        this.packetClient = packetClient;
        this.searchClient = searchClient;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.httpHeaders = httpHeaders;
        this.endPoint = endPoint;
    }

    @Override
    public RequestId saveOrUpdate(@Valid PaymentOperation request) throws SdkJsonRpcClientException {
        logger.info("Processing payment operation request. PaymentOperation docId: {}", request.getDocument().getId());
        request.setMappedSigns(SignMapper.convertSigns(request.getSigns()));
        PaymentModelValidator.validate(request);

        GraphCollection<PaymentOperationGet> collection = searchClient.searchPaymentOperation(payment ->
                payment
                        .withRequestId()
                        .setWhere(where -> where.docIdEq(request.getDocument().getId().toString()))
                        .setLimit(1));
        Packet packet = Packet.createPacket();

        RequestId requestId;
        if (collection.isEmpty()) {
            requestId = PaymentPacketCommandAdder.addCreateCommandToPaket(packet, request);
        } else {
            requestId = new RequestId(UUID.fromString(collection.get(0).getRequestId()));
            PaymentPacketCommandAdder.addUpdateCommandToPacket(packet, request, collection.get(0).getObjectId());
        }

        packetClient.execute(packet);
        return requestId;
    }

    @Override
    public AnalyzeResponse send(@Valid PaymentSendRequest request) throws SdkJsonRpcClientException {
        logger.info("Sending payment operation to analyze. PaymentOperation docId: {}", request.getDocId());
        AnalyzeRequest paymentAnalyzeRequest = createPaymentAnalyzeRequest(request.getDocId());
        try {
            String jsonRequest = objectMapper.writeValueAsString(paymentAnalyzeRequest);
            logger.debug("Payment analyze request: {}", jsonRequest);
            String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
            logger.debug("Payment full analyze response: {}", jsonResponse);
            FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
            return convertToPaymentAnalyzeResponse(fullAnalyzeResponse);
        } catch (HttpStatusCodeException e) {
            String message = "Payment analyze error: statusCodeValue=" + e.getRawStatusCode() +
                    ", error='" + e.getResponseBodyAsString() + "'";
            logger.error(message);
            throw new AnalyzeException(message, e);
        } catch (JsonProcessingException e) {
            String message = "Anti fraud aggregator internal error: " + e.getMessage();
            logger.error(message);
            throw new ApplicationException(message, e);
        }
    }

    private AnalyzeRequest createPaymentAnalyzeRequest(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<PaymentOperationGet> collection = searchClient.searchPaymentOperation(payment -> {
            payment
                    .withRequestId()
                    .withTimeStamp()
                    .withEpkId()
                    .withDigitalId()
                    .withUserGuid()
                    .withTbCode()
                    .withHttpAccept()
                    .withHttpReferer()
                    .withHttpAcceptChars()
                    .withHttpAcceptEncoding()
                    .withHttpAcceptLanguage()
                    .withIpAddress()
                    .withUserAgent()
                    .withDevicePrint()
                    .withMobSdkData()
                    .withChannelIndicator()
                    .withTimeOfOccurrence()
                    .withDocId()
                    .withDocNumber()
                    .withDocDate()
                    .withAmount()
                    .withCurrency()
                    .withExecutionSpeed()
                    .withOtherAccBankType()
                    .withAccountNumber()
                    .withOtherAccName()
                    .withBalAccNumber()
                    .withOtherBicCode()
                    .withOtherAccOwnershipType()
                    .withOtherAccType()
                    .withTransferMediumType()
                    .withReceiverInn()
                    .withDestination()
                    .withPayerInn()
                    .withClientDefinedChannelIndicator();
            withSigns(payment)
                    .setWhere(where -> where.docIdEq(docId.toString()))
                    .setLimit(1);
        });
        if (collection.isEmpty()) {
            throw new ApplicationException("PaymentOperation with docId=" + docId + " not found");
        }
        return convertToPaymentAnalyzeRequest(collection.get(0));
    }

    private PaymentOperationCollectionWith<PaymentOperationGrasp> withSigns(PaymentOperationCollectionWith<PaymentOperationGrasp> payment) {
        payment
                .withFirstSignTime()
                .withFirstSignIp()
                .withFirstSignLogin()
                .withFirstSignCryptoprofile()
                .withFirstSignCryptoprofileType()
                .withFirstSignChannel()
                .withFirstSignToken()
                .withFirstSignType()
                .withFirstSignImsi()
                .withFirstSignCertId()
                .withFirstSignPhone()
                .withFirstSignEmail()
                .withFirstSignSource()
                .withPrivateIpAddress()
                .withSenderSignTime()
                .withSenderIp()
                .withSenderLogin()
                .withSenderCryptoprofile()
                .withSenderCryptoprofileType()
                .withSenderSignChannel()
                .withSenderToken()
                .withSenderSignType()
                .withSenderSignImsi()
                .withSenderCertId()
                .withSenderPhone()
                .withSenderEmail()
                .withSenderSource()
                .withSecondSignTime()
                .withSecondSignIp()
                .withSecondSignLogin()
                .withSecondSignCryptoprofile()
                .withSecondSignCryptoprofileType()
                .withSecondSignChannel()
                .withSecondSignToken()
                .withSecondSignType()
                .withSecondSignImsi()
                .withSecondSignCertId()
                .withSecondSignPhone()
                .withSecondSignEmail()
                .withSecondSignSource()
                .withThirdSignTime()
                .withThirdSignIp()
                .withThirdSignLogin()
                .withThirdSignCryptoprofile()
                .withThirdSignCryptoprofileType()
                .withThirdSignChannel()
                .withThirdSignToken()
                .withThirdSignType()
                .withThirdSignImsi()
                .withThirdSignCertId()
                .withThirdSignPhone()
                .withThirdSignEmail()
                .withThirdSignSource();
        return payment;
    }

    private AnalyzeRequest convertToPaymentAnalyzeRequest(PaymentOperationGet paymentGet) {
        AnalyzeRequest request = new AnalyzeRequest();
        request.setMessageHeader(new MessageHeader(paymentGet.getTimeStamp()));
        request.setIdentificationData(new IdentificationData());
        request.getIdentificationData().setClientTransactionId(paymentGet.getDocId());
        request.getIdentificationData().setOrgName(paymentGet.getTbCode());
        request.getIdentificationData().setUserName("");
        request.getIdentificationData().setDboOperation(supportedDboOperation());
        request.getIdentificationData().setRequestId(UUID.fromString(paymentGet.getRequestId()));
        request.setDeviceRequest(new DeviceRequest());
        request.getDeviceRequest().setDevicePrint(paymentGet.getDevicePrint());
        request.getDeviceRequest().setMobSdkData(paymentGet.getMobSdkData());
        request.getDeviceRequest().setHttpAccept(paymentGet.getHttpAccept());
        request.getDeviceRequest().setHttpAcceptChars(paymentGet.getHttpAcceptChars());
        request.getDeviceRequest().setHttpAcceptEncoding(paymentGet.getHttpAcceptEncoding());
        request.getDeviceRequest().setHttpAcceptLanguage(paymentGet.getHttpAcceptLanguage());
        request.getDeviceRequest().setHttpReferrer(paymentGet.getHttpReferer());
        request.getDeviceRequest().setIpAddress(paymentGet.getIpAddress());
        request.getDeviceRequest().setUserAgent(paymentGet.getUserAgent());
        request.setChannelIndicator(paymentGet.getChannelIndicator());
        request.setEventDataList(new EventData());
        EventDataHeader eventData = new EventDataHeader(supportedDboOperation().getEventType(), supportedDboOperation().getEventDescription(),
                supportedDboOperation().getClientDefinedEventType(paymentGet.getChannelIndicator()), paymentGet.getTimeOfOccurrence());
        request.getEventDataList().setEventDataHeader(eventData);
        request.getEventDataList().setTransactionData(new TransactionData());
        request.getEventDataList().getTransactionData().setAmount(new Amount(paymentGet.getAmount(), paymentGet.getCurrency()));
        request.getEventDataList().getTransactionData().setExecutionSpeed(paymentGet.getExecutionSpeed());
        request.getEventDataList().getTransactionData().setOtherAccountBankType(paymentGet.getOtherAccBankType());
        request.getEventDataList().getTransactionData().setMyAccountData(new PayerAccount(paymentGet.getAccountNumber()));
        request.getEventDataList().getTransactionData().setOtherAccountData(new ReceiverAccount());
        request.getEventDataList().getTransactionData().getOtherAccountData().setAccountName(paymentGet.getOtherAccName());
        request.getEventDataList().getTransactionData().getOtherAccountData().setAccountNumber(paymentGet.getBalAccNumber());
        request.getEventDataList().getTransactionData().getOtherAccountData().setRoutingCode(paymentGet.getOtherBicCode());
        request.getEventDataList().getTransactionData().getOtherAccountData().setOtherAccountOwnershipType(paymentGet.getOtherAccOwnershipType());
        request.getEventDataList().getTransactionData().getOtherAccountData().setOtherAccountType(paymentGet.getOtherAccType());
        request.getEventDataList().getTransactionData().getOtherAccountData().setTransferMediumType(paymentGet.getTransferMediumType());
        request.getEventDataList().setClientDefinedAttributeList(new ClientDefinedAttributeList(createClientDefinedAttributeList(paymentGet)));
        request.setClientDefinedChannelIndicator(paymentGet.getClientDefinedChannelIndicator());
        return request;
    }

    private List<Attribute> createClientDefinedAttributeList(PaymentOperationGet paymentGet) {
        List<Attribute> clientDefinedAttributeList = new ArrayList<>(CAPACITY);
        for (Map.Entry<String, String> entry : DESCRIPTION_MAP.entrySet()) {
            Object value = CRITERIA_MAP.get(entry.getKey()).apply(paymentGet);
            if (value != null) {
                Attribute attribute = new Attribute();
                attribute.setName(entry.getValue());
                attribute.setValue(value.toString());
                attribute.setDataType("STRING");
                clientDefinedAttributeList.add(attribute);
            }
        }
        return clientDefinedAttributeList;
    }

    private AnalyzeResponse convertToPaymentAnalyzeResponse(FullAnalyzeResponse fullAnalyzeResponse) {
        AnalyzeResponse paymentAnalyzeResponse = new AnalyzeResponse();
        if (fullAnalyzeResponse != null) {
            if (fullAnalyzeResponse.getIdentificationData() != null) {
                paymentAnalyzeResponse.setTransactionId(fullAnalyzeResponse.getIdentificationData().getTransactionId());
            }
            if (fullAnalyzeResponse.getRiskResult() != null && fullAnalyzeResponse.getRiskResult().getTriggeredRule() != null) {
                paymentAnalyzeResponse.setActionCode(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getActionCode());
                paymentAnalyzeResponse.setComment(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getComment());
                paymentAnalyzeResponse.setDetailledComment(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getDetailledComment());
                paymentAnalyzeResponse.setWaitingTime(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getWaitingTime());
            }
        }
        return paymentAnalyzeResponse;
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.PAYMENT_DT_0401060;
    }

}
