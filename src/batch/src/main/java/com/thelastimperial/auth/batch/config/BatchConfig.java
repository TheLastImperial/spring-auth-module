package com.thelastimperial.auth.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
@EnableBatchProcessing
@EnableJdbcJobRepository(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
public class BatchConfig {

    @Bean
    public DataSource dataSource(
        @Value("${spring.datasource.url}")
        String url,
        @Value("${spring.datasource.username}")
        String username,
        @Value("${spring.datasource.password}")
        String password
    ) {
        return DataSourceBuilder.create()
            .url(url)
            .username(username)
            .password(password)
            .build();
    }

    @Bean
    public DataSource batchDataSource(
        @Value("${spring.datasource.batch.url}")
        String url,
        @Value("${spring.datasource.batch.username}")
        String username,
        @Value("${spring.datasource.batch.password}")
        String password
    ) {
        return DataSourceBuilder.create()
            .url(url)
            .username(username)
            .password(password)
            .build();
    }

    @Bean
    public TransactionManager batchTransactionManager(DataSource batchDataSource) {
        return new JdbcTransactionManager(batchDataSource);
    }
}
