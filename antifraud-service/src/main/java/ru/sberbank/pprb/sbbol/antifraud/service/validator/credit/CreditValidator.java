package ru.sberbank.pprb.sbbol.antifraud.service.validator.credit;

import org.apache.commons.lang3.StringUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис валидации запроса на анализ по продукту Кредит или Банковская гарантия
 */
public final class CreditValidator {

    public static void validate(CreditSendToAnalyzeRq request) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ClientDefinedEventType.BROWSER_REQUEST_GUARANTEE == request.getEventData().getClientDefinedEventType() ||
                ClientDefinedEventType.MOBSBBOL_REQUEST_GUARANTEE == request.getEventData().getClientDefinedEventType()) {
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getAuctionNumber())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.auctionNumber\" must be filled");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getGuaranteeType())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.guaranteeType\" must be filled");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getGuaranteeForm())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.guaranteeForm\" must be filled");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getGuaranteeDateStart())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.guaranteeDateStart\" must be filled");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getGuaranteeDateEnd())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.guaranteeDateEnd\" must be filled");
            }
        }
        if (!stringBuilder.isEmpty()) {
            throw new ModelArgumentException("ClientTransactionId=" + request.getClientTransactionId() + ". " + stringBuilder);
        }
    }

}
