package ru.sberbank.pprb.sbbol.antifraud.logging;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.InvocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class LoggedInvocationListener implements InvocationListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggedInvocationListener.class);

    @Override
    public void willInvoke(Method method, List<JsonNode> arguments) {
        logger.debug("Invoking method {} with params {}", method, arguments);
    }

    @Override
    public void didInvoke(Method method, List<JsonNode> arguments, Object result, Throwable t, long duration) {
        if (t != null) {
            logger.error("Error in JSON-RPC service", t);
        } else {
            logger.debug("Method {} invocation result is {}", method, result);
        }
    }

}
