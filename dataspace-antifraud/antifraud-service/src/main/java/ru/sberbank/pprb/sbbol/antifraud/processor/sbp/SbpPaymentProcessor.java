package ru.sberbank.pprb.sbbol.antifraud.processor.sbp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.analyze.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.analyze.common.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.PaymentAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.Amount;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.DeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.EventDataHeader;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.MessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.PayerAccount;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.PaymentAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.ReceiverAccount;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request.TransactionData;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.sbp.SbpPaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.data.sbp.SbpPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.processor.SignMapper;
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
 * Обработчик платежных поручений СБП. Добавляет запись в таблицу SbpPaymentOperation.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class SbpPaymentProcessor implements Processor<SbpPaymentOperation, SbpPaymentSendRequest> {

    private static final Logger logger = LoggerFactory.getLogger(SbpPaymentProcessor.class);

    public static final String DOC_NUMBER = "docNumber";
    public static final String DOC_DATE = "docDate";
    public static final String DESTINATION = "destination";
    public static final String PAYER_FINANCIAL_NAME = "payerFinancialName";
    public static final String PAYER_OSB_NUM = "payerOsbNum";
    public static final String PAYER_VSP_NUM = "payerVspNum";
    public static final String PAYER_ACC_BALANCE = "payerAccBalance";
    public static final String PAYER_ACC_CREATE_DATE = "payerAccCreateDate";
    public static final String PAYER_BIC = "payerBic";
    public static final String PAYER_DOCUMENT_NUMBER = "payerDocumentNumber";
    public static final String PAYER_DOCUMENT_TYPE = "payerDocumentType";
    public static final String PAYER_SEGMENT = "payerSegment";
    public static final String PAYER_INN = "payerInn";
    public static final String RECEIVER_INN = "receiverInn";
    public static final String RECEIVER_BANK_NAME = "receiverBankName";
    public static final String RECEIVER_BANK_COUNTRY_CODE = "receiverBankCountryCode";
    public static final String RECEIVER_BANK_CORR_ACC = "receiverBankCorrAcc";
    public static final String RECEIVER_BANK_ID = "receiverBankId";
    public static final String RECEIVER_DOCUMENT = "receiverDocument";
    public static final String RECEIVER_DOCUMENT_TYPE = "receiverDocumentType";
    public static final String RECEIVER_ACCOUNT = "receiverAccount";
    public static final String RECEIVER_PHONE_NUMBER = "receiverPhoneNumber";
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

    private static final Map<String, Function<SbpPaymentOperationGet, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    private static final int CAPACITY = 49;
    
    static {
        Map<String, Function<SbpPaymentOperationGet, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(DOC_NUMBER, SbpPaymentOperationGet::getDocNumber);
        criteriaMap.put(DOC_DATE, SbpPaymentOperationGet::getDocDate);
        criteriaMap.put(DESTINATION, SbpPaymentOperationGet::getDestination);
        criteriaMap.put(PAYER_FINANCIAL_NAME, SbpPaymentOperationGet::getPayerFinancialName);
        criteriaMap.put(PAYER_OSB_NUM, SbpPaymentOperationGet::getPayerOsbNum);
        criteriaMap.put(PAYER_VSP_NUM, SbpPaymentOperationGet::getPayerVspNum);
        criteriaMap.put(PAYER_ACC_BALANCE, SbpPaymentOperationGet::getPayerAccBalance);
        criteriaMap.put(PAYER_ACC_CREATE_DATE, SbpPaymentOperationGet::getPayerAccCreateDate);
        criteriaMap.put(PAYER_BIC, SbpPaymentOperationGet::getPayerBic);
        criteriaMap.put(PAYER_DOCUMENT_NUMBER, SbpPaymentOperationGet::getPayerDocumentNumber);
        criteriaMap.put(PAYER_DOCUMENT_TYPE, SbpPaymentOperationGet::getPayerDocumentType);
        criteriaMap.put(PAYER_SEGMENT, SbpPaymentOperationGet::getPayerSegment);
        criteriaMap.put(PAYER_INN, SbpPaymentOperationGet::getPayerInn);
        criteriaMap.put(RECEIVER_INN, SbpPaymentOperationGet::getReceiverInn);
        criteriaMap.put(RECEIVER_BANK_NAME, SbpPaymentOperationGet::getReceiverBankName);
        criteriaMap.put(RECEIVER_BANK_COUNTRY_CODE, SbpPaymentOperationGet::getReceiverBankCountryCode);
        criteriaMap.put(RECEIVER_BANK_CORR_ACC, SbpPaymentOperationGet::getReceiverBankCorrAcc);
        criteriaMap.put(RECEIVER_BANK_ID, SbpPaymentOperationGet::getReceiverBankId);
        criteriaMap.put(RECEIVER_DOCUMENT, SbpPaymentOperationGet::getReceiverDocument);
        criteriaMap.put(RECEIVER_DOCUMENT_TYPE, SbpPaymentOperationGet::getReceiverDocumentType);
        criteriaMap.put(RECEIVER_ACCOUNT, SbpPaymentOperationGet::getReceiverAccount);
        criteriaMap.put(RECEIVER_PHONE_NUMBER, SbpPaymentOperationGet::getReceiverPhoneNumber);
        criteriaMap.put(PRIVATE_IP_ADDRESS, SbpPaymentOperationGet::getPrivateIpAddress);
        criteriaMap.put(FIRST_SIGN_TIME, SbpPaymentOperationGet::getFirstSignTime);
        criteriaMap.put(FIRST_SIGN_IP, SbpPaymentOperationGet::getFirstSignIp);
        criteriaMap.put(FIRST_SIGN_LOGIN, SbpPaymentOperationGet::getFirstSignLogin);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE, SbpPaymentOperationGet::getFirstSignCryptoprofile);
        criteriaMap.put(FIRST_SIGN_CRYPTOPROFILE_TYPE, SbpPaymentOperationGet::getFirstSignCryptoprofileType);
        criteriaMap.put(FIRST_SIGN_CHANNEL, SbpPaymentOperationGet::getFirstSignChannel);
        criteriaMap.put(FIRST_SIGN_TOKEN, SbpPaymentOperationGet::getFirstSignToken);
        criteriaMap.put(FIRST_SIGN_TYPE, SbpPaymentOperationGet::getFirstSignType);
        criteriaMap.put(FIRST_SIGN_IMSI, SbpPaymentOperationGet::getFirstSignImsi);
        criteriaMap.put(FIRST_SIGN_CERT_ID, SbpPaymentOperationGet::getFirstSignCertId);
        criteriaMap.put(FIRST_SIGN_PHONE, SbpPaymentOperationGet::getFirstSignPhone);
        criteriaMap.put(FIRST_SIGN_EMAIL, SbpPaymentOperationGet::getFirstSignEmail);
        criteriaMap.put(FIRST_SIGN_SOURCE, SbpPaymentOperationGet::getFirstSignSource);
        criteriaMap.put(SENDER_SIGN_TIME, SbpPaymentOperationGet::getSenderSignTime);
        criteriaMap.put(SENDER_IP, SbpPaymentOperationGet::getSenderIp);
        criteriaMap.put(SENDER_LOGIN, SbpPaymentOperationGet::getSenderLogin);
        criteriaMap.put(SENDER_CRYPTOPROFILE, SbpPaymentOperationGet::getSenderCryptoprofile);
        criteriaMap.put(SENDER_CRYPTOPROFILE_TYPE, SbpPaymentOperationGet::getSenderCryptoprofileType);
        criteriaMap.put(SENDER_SIGN_CHANNEL, SbpPaymentOperationGet::getSenderSignChannel);
        criteriaMap.put(SENDER_TOKEN, SbpPaymentOperationGet::getSenderToken);
        criteriaMap.put(SENDER_SIGN_TYPE, SbpPaymentOperationGet::getSenderSignType);
        criteriaMap.put(SENDER_SIGN_IMSI, SbpPaymentOperationGet::getSenderSignImsi);
        criteriaMap.put(SENDER_CERT_ID, SbpPaymentOperationGet::getSenderCertId);
        criteriaMap.put(SENDER_PHONE, SbpPaymentOperationGet::getSenderPhone);
        criteriaMap.put(SENDER_EMAIL, SbpPaymentOperationGet::getSenderEmail);
        criteriaMap.put(SENDER_SOURCE, SbpPaymentOperationGet::getSenderSource);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);
        
        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(DOC_NUMBER, DOC_NUMBER);
        descriptionMap.put(DOC_DATE, DOC_DATE);
        descriptionMap.put(DESTINATION, "Назначение платежа");
        descriptionMap.put(PAYER_FINANCIAL_NAME, "Полное наименование организации");
        descriptionMap.put(PAYER_OSB_NUM, "Номер ОСБ");
        descriptionMap.put(PAYER_VSP_NUM, "Номер ВСП");
        descriptionMap.put(PAYER_ACC_BALANCE, "Остаток на счете плательщика");
        descriptionMap.put(PAYER_ACC_CREATE_DATE, "Дата открытия счета плательщика");
        descriptionMap.put(PAYER_BIC, "БИК SWIFT плательщика");
        descriptionMap.put(PAYER_DOCUMENT_NUMBER, "Номер ДУЛ");
        descriptionMap.put(PAYER_DOCUMENT_TYPE, "Тип ДУЛ");
        descriptionMap.put(PAYER_SEGMENT, "Сегмент клиента ЮЛ");
        descriptionMap.put(PAYER_INN, "ИНН отправителя");
        descriptionMap.put(RECEIVER_INN, "ИНН получателя");
        descriptionMap.put(RECEIVER_BANK_NAME, "Наименование Банка получателя");
        descriptionMap.put(RECEIVER_BANK_COUNTRY_CODE, "Код страны банка получателя");
        descriptionMap.put(RECEIVER_BANK_CORR_ACC, "Корсчет Банка получателя");
        descriptionMap.put(RECEIVER_BANK_ID, "Идентификатор Банка Получателя");
        descriptionMap.put(RECEIVER_DOCUMENT, "Документ Получателя");
        descriptionMap.put(RECEIVER_DOCUMENT_TYPE, "Тип документа получателя");
        descriptionMap.put(RECEIVER_ACCOUNT, "Бал.счет получателя");
        descriptionMap.put(RECEIVER_PHONE_NUMBER, "Номер телефона получателя");
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
        descriptionMap.put(FIRST_SIGN_CERT_ID, "1-я подпись Идентификатор личного сертификата");
        descriptionMap.put(FIRST_SIGN_PHONE, "1-я подпись Номер телефона");
        descriptionMap.put(FIRST_SIGN_EMAIL, "1-я подпись Адрес электронной почты");
        descriptionMap.put(FIRST_SIGN_SOURCE, "1-я подпись Канал");
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
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;

    private final String endPoint;

    public SbpPaymentProcessor(DataspaceCorePacketClient packetClient, DataspaceCoreSearchClient searchClient, RestTemplate restTemplate,
                               HttpHeaders httpHeaders, ObjectMapper objectMapper, @Value("${fpis.endpoint}") String endPoint) {
        this.packetClient = packetClient;
        this.searchClient = searchClient;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.objectMapper = objectMapper;
        this.endPoint = endPoint;
    }

    @Override
    public RequestId saveOrUpdate(@Valid SbpPaymentOperation record) throws SdkJsonRpcClientException {
        logger.info("Processing SBP payment operation request. PaymentOperation docId: {}", record.getDocument().getId());
        record.setMappedSigns(SignMapper.convertSigns(record.getSigns()));
        SbpPaymentModelValidator.validate(record);

        GraphCollection<SbpPaymentOperationGet> collection = searchClient.searchSbpPaymentOperation(sbpPayment ->
                sbpPayment
                        .withRequestId()
                        .setWhere(where -> where.docIdEq(record.getDocument().getId().toString()))
                        .setLimit(1));
        Packet packet = Packet.createPacket();

        RequestId requestId;
        if (collection.isEmpty()) {
            requestId = SbpPaymentPacketCommandAdder.addCreateCommandToPacket(packet, record);
        } else {
            requestId = new RequestId(UUID.fromString(collection.get(0).getRequestId()));
            SbpPaymentPacketCommandAdder.addUpdateCommandToPacket(packet, record, collection.get(0).getObjectId());
        }

        packetClient.execute(packet);
        return requestId;
    }

    @Override
    public AnalyzeResponse send(@Valid SbpPaymentSendRequest request) throws SdkJsonRpcClientException {
        return null;
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.SBP_B2C;
    }
}
