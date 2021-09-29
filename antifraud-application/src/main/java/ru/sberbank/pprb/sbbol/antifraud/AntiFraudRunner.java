package ru.sberbank.pprb.sbbol.antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class AntiFraudRunner {

    public static void main(String[] args) {
        SpringApplication.run(AntiFraudRunner.class, args);
    }

}
