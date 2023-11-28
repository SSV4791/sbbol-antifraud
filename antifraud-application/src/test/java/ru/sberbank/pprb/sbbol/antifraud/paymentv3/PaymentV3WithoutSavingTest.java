package ru.sberbank.pprb.sbbol.antifraud.paymentv3;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3TypedSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
public class PaymentV3WithoutSavingTest extends AbstractIntegrationTest {

    private static final String SIGN = """
            {
                "signTime":"2023-05-02T13:57:25.561427",
                "signIp":"valid",
                "signLogin":"valid",
                "signCryptoprofile":"valid",
                "signCryptoprofileType":"valid",
                "signToken":"valid",
                "signType":"valid",
                "signImsi":"valid",
                "signCertId":"valid",
                "signPhone":"valid",
                "signEmail":"valid",
                "signChannel":"valid",
                "signSource":"valid",
                "signDigitalUserId":"valid",
                "signMacAddress":"valid",
                "signGeoLocation":"valid",
                "signPkProperty":"valid"
            }
            """;

    public PaymentV3WithoutSavingTest() {
        super("/antifraud/v3/payment/withoutsaving");
    }

    @Test
    @AllureId("140284")
    @DisplayName("Отправка данных на анализ без сохранения (РПП v3)")
    void analyzeWithoutSavingTest() throws Throwable {
        FullAnalyzeResponse expected = step("Подготавливаем эталонный ответ", () -> podamFactory().populatePojo(new FullAnalyzeResponse()));
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));
        PaymentOperationV3 request = step("Подготавливаем запрос", () -> podamFactory().populatePojo(new PaymentOperationV3()));
        step("Добавляем подпись", () ->
                request.setSigns(List.of(
                        new PaymentV3TypedSign(0, SIGN),
                        new PaymentV3TypedSign(3, SIGN))));
        AnalyzeResponse actual = step("Отправляем запрос в рест /antifraud/v3/payment/withoutsaving", () -> sendPaymentV3(request));
        step("Сравниваем эталонный ответ с полученным", () -> {
            assertAll(
                    () -> assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId()),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode()),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment()),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment()),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime())
            );
        });
    }

    @Test
    @AllureId("140322")
    @DisplayName("Ошибка от ФП ИС при отправке данных на анализ без сохранения (РПП v3)")
    void analyzeOperationWithoutSavingHttpStatusCodeErrorTest() {
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("ERROR"));
        PaymentOperationV3 request = step("Подготавливаем запрос", () -> podamFactory().populatePojo(new PaymentOperationV3()));
        step("Добавляем подпись", () ->
                request.setSigns(List.of(
                        new PaymentV3TypedSign(0, SIGN),
                        new PaymentV3TypedSign(3, SIGN)
                )));
        AnalyzeException ex = step("Отправляем запрос в рест /antifraud/v3/payment/withoutsaving и получаем ошибку", () ->
                assertThrows(AnalyzeException.class, () -> sendPaymentV3(request)));
        String msg = ex.getMessage();
        step("проверям корректность сообщения с ошибкой от ФП ИС", () ->
                assertEquals("ClientTransactionId=" + request.getClientTransactionId() + ", dboOperation=" + request.getDboOperation() + ". Analysis error. Status code: 500 INTERNAL_SERVER_ERROR. ERROR", msg));
    }

}
