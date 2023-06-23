package ru.sberbank.pprb.sbbol.antifraud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import ru.sbrf.journal.standin.ResourceNotAllowedException;
import ru.sbrf.journal.standin.StandinResourceHelper;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class SystemInfoRoute {

    @Bean
    public RouterFunction<ServerResponse> systemInfo(StandinResourceHelper<String> standinResourceHelper) {
        return route(GET("/system-info"),
                response -> {
                    String mode;
                    try {
                        mode = standinResourceHelper.getResource();
                    } catch (ResourceNotAllowedException e) {
                        // в случае STOP кидается ResourceNotAllowedException
                        mode = "stop";
                    }

                    return ServerResponse.ok().body(new SystemInfo(mode));
                });
    }

    public static class SystemInfo {

        private final String dataSourceMode;

        public SystemInfo(String dataSourceMode) {
            this.dataSourceMode = dataSourceMode;
        }

        public String getDataSourceMode() {
            return dataSourceMode;
        }
    }

}
