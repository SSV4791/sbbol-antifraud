package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Типизированная подпись
 */
public class PaymentV3TypedSign implements Serializable {

    /**
     * Тип подписи
     * 0: отправивший
     * 1: первая подпись
     * 2: вторая подпись
     * 3: единственная подпись
     */
    @NotNull(message = "The attribute \"signs.sign.signNumber\" must be filled")
    @Min(value = 0, message = "Attribute \"signs.sign.signNumber\" cannot be less than 0")
    @Max(value = 3, message = "Attribute \"signs.sign.signNumber\" cannot be greater than 3")
    private Integer signNumber;

    /**
     * Данные подписи
     */
    @NotBlank(message = "The attribute \"signs.sign.sign\" must be filled")
    private String sign;

    @JsonCreator
    public PaymentV3TypedSign(@JsonProperty("signNumber") Integer signNumber,
                              @JsonProperty("sign") String sign) {
        this.signNumber = signNumber;
        this.sign = sign;
    }

    public PaymentV3TypedSign() {
    }

    public Integer getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(Integer signNumber) {
        this.signNumber = signNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "{" +
                "signNumber=" + signNumber +
                ", sign='" + sign + '\'' +
                '}';
    }

}
