package ru.sberbank.pprb.sbbol.antifraud.common;

import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class AllureRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";

    public AllureRestTemplateInterceptor() {
    }

    public AllureRestTemplateInterceptor setRequestTemplate(String templatePath) {
        this.requestTemplatePath = templatePath;
        return this;
    }

    public AllureRestTemplateInterceptor setResponseTemplate(String templatePath) {
        this.responseTemplatePath = templatePath;
        return this;
    }

    public ClientHttpResponse intercept(@NonNull HttpRequest request, byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();

        String requestUri = request.getURI().toString();
        String requestMethod = request.getMethodValue();
        Map<String, String> requestHeaders = toMapConverter(request.getHeaders());
        String requestBody = body.length != 0 ? new String(body, StandardCharsets.UTF_8) : null;

        HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder
                .create("Request", requestUri)
                .setMethod(requestMethod)
                .setHeaders(requestHeaders);
        if (nonNull(requestBody)) {
            requestAttachmentBuilder.setBody(requestBody);
        }
        HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();
        processor.addAttachment(requestAttachment, new FreemarkerAttachmentRenderer(this.requestTemplatePath));

        ClientHttpResponseDecorator clientHttpResponse = new ClientHttpResponseDecorator(execution.execute(request, body));

        int responseStatusCode = clientHttpResponse.getRawStatusCode();
        Map<String, String> responseHeaders = toMapConverter(clientHttpResponse.getHeaders());
        String responseBody = clientHttpResponse.getBodyAsString();

        HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder
                .create("Response")
                .setResponseCode(responseStatusCode)
                .setHeaders(responseHeaders)
                .setBody(responseBody)
                .build();
        processor.addAttachment(responseAttachment, new FreemarkerAttachmentRenderer(this.responseTemplatePath));

        return clientHttpResponse;
    }

    private static Map<String, String> toMapConverter(Map<String, List<String>> items) {
        Map<String, String> result = new HashMap<>();
        items.forEach((key, value) -> {
            result.put(key, String.join("; ", value));
        });
        return result;
    }

    static class ClientHttpResponseDecorator extends AbstractClientHttpResponse {

        private final ClientHttpResponse original;

        private byte[] body = null;

        public ClientHttpResponseDecorator(ClientHttpResponse original) {
            this.original = original;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return this.original.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.original.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.original.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            processGetBodyInvocation();
            return new ByteArrayInputStream(this.body);
        }

        public String getBodyAsString() throws IOException {
            processGetBodyInvocation();
            return new String(this.body, StandardCharsets.UTF_8);
        }

        @Override
        public void close() {
            this.original.close();
        }

        private void processGetBodyInvocation() throws IOException {
            if (Objects.isNull(this.body)) {
                this.body = StreamUtils.copyToByteArray(this.original.getBody());
            }
        }

    }

}
