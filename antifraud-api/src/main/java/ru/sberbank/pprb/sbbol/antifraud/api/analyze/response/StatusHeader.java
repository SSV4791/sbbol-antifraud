package ru.sberbank.pprb.sbbol.antifraud.api.analyze.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusHeader implements Serializable {

    private Integer reasonCode;

    private String reasonDescription;

    private String statusCode;

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "{" +
                "reasonCode=" + reasonCode +
                ", reasonDescription='" + reasonDescription + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}