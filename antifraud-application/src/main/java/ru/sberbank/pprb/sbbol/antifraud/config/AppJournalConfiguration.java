package ru.sberbank.pprb.sbbol.antifraud.config;

import com.sbt.pprb.integration.hibernate.standin.StandinPlugin;
import com.sbt.pprb.integration.replication.OrderingControlStrategy;
import com.sbt.pprb.integration.replication.PartitionLockMode;
import com.sbt.pprb.integration.replication.PartitionMultiplyingMode;
import com.sbt.pprb.integration.replication.transport.JournalSubscriptionImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.sbrf.journal.client.JournalCreatorClientApi;
import ru.sbrf.journal.standin.StandinConfiguration;
import ru.sbrf.journal.standin.consumer.api.SubscriptionService;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@Import({StandinConfiguration.class})
public class AppJournalConfiguration {

    @Bean
    public SubscriptionService subscriptionService(@Value("${standin.cloud.client.zoneId}") String zoneId) {
        return new JournalSubscriptionImpl(zoneId);
    }

    @Bean
    public StandinPlugin standinPlugin(
            @Value("${appjournal.moduleId}") String moduleId,
            @Qualifier("mainDataSource") DataSource masterDataSource,
            @Qualifier("standInDataSource") DataSource standinDataSource,
            StandInPluginConfig pluginConfig,
            EntityManagerFactory entityManagerFactory,
            JournalCreatorClientApi journalClient
    ) {
        StandinPlugin.Configurator configurator = StandinPlugin.configurator(entityManagerFactory);
        configurator.setMasterDataSource(masterDataSource);
        configurator.setStandinDataSource(standinDataSource);
        configurator.setJournalClient(journalClient);

        // HashKey функция
        configurator.setJournalHashKeyResolver(pluginConfig.getJournalHashKeyResolver().instance());
        // Идентификатор модуля
        configurator.setModuleIdProvider(() -> moduleId);
        // Стратегия репликации - с блокировками или без
        configurator.setReplicationStrategy(pluginConfig.getReplicationStrategy());
        // Тип сериализатора
        configurator.setSerializerType(pluginConfig.getSerializerType());
        // Сериализация отправки в ПЖ по hashKey
        configurator.setPartitionLockMode(PartitionLockMode.NONE);
        // Стратегия контроля порядка применения векторов
        configurator.setOrderingControlStrategy(OrderingControlStrategy.OPTIMISTIC_LOCK_VERSION_CONTROL);
        // Стратегия работы с несколькими hashKey в одной транзакции
        configurator.setPartitionMultiplyingMode(PartitionMultiplyingMode.FORBIDDEN);

        return configurator.configure();
    }

}
