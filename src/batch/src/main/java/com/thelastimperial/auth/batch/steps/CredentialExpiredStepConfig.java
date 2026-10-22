package com.thelastimperial.auth.batch.steps;

import javax.sql.DataSource;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.infrastructure.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.JdbcTransactionManager;

import com.thelastimperial.auth.batch.domain.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class CredentialExpiredStepConfig {
    private final JobRepository jobRepository;
    private final JdbcTransactionManager jdbcTransactionManager;
    private final DataSource dataSource;

    @Bean
    public Step credentialExpiredStep(
        ItemReader<User> userCredentialExpiredReader,
        ItemWriter<User> userCredentialExpiredCompositeWriter
    ) {
        return new StepBuilder("CredentialExpiredStep", jobRepository)
        .<User, User>chunk(10)
        .transactionManager(jdbcTransactionManager)
        .reader(userCredentialExpiredReader)
        .writer(userCredentialExpiredCompositeWriter)
        .build();
    }

    @Bean
    public ItemReader<User> userCredentialExpiredReader() {
        String sql = """
            SELECT u.id, 'CREDENTIALS_EXPIRED' action
            FROM users u
            INNER JOIN user_expiries ue
                ON u.id = ue.user_id
            WHERE ue.credentials_expired_at < NOW()
                AND u.credentials_non_expired = 't'
        """;
        return new JdbcCursorItemReaderBuilder<User>()
            .name("UserCredentialExpiredReader")
            .dataSource(dataSource)
            .sql(sql)
            .rowMapper(new DataClassRowMapper<>(User.class))
            .build();
    }

    @Bean
    public ItemWriter<User> userCredentialExpiredCompositeWriter(
        ItemWriter<User> userCredentialExpiredWriter,
        ItemWriter<User> userAuditWriter
    ) {
        CompositeItemWriter<User> composite = new CompositeItemWriter<>(
            userCredentialExpiredWriter, userAuditWriter
        );
        return composite;
    }

    @Bean
    public ItemWriter<User> userCredentialExpiredWriter(){
        String sql = """
            UPDATE users
            SET credentials_non_expired = 'f'
            WHERE id = :id
        """;
        return new JdbcBatchItemWriterBuilder<User>()
            .dataSource(dataSource)
            .sql(sql)
            .itemSqlParameterSourceProvider((item) -> {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("id", item.id());
                return params;
            })
            .build();
    }
}
