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
public class AccountExpiredStepConfig {
    private final JobRepository jobRepository;
    private final JdbcTransactionManager jdbcTransactionManager;
    private final DataSource dataSource;

    @Bean
    public Step accountExpiredStep(
        ItemReader<User> userAccountExpiredReader,
        ItemWriter<User> userAccountExpiredCompositeWriter
    ) {
        return new StepBuilder(jobRepository)
            .<User, User>chunk(10)
            .transactionManager(jdbcTransactionManager)
            .reader(userAccountExpiredReader)
            .writer(userAccountExpiredCompositeWriter)
            .build();
    }

    @Bean
    public ItemReader<User> userAccountExpiredReader(){
        String sql = """
            SELECT u.id, 'ACCOUNT_EXPIRED' action
            FROM users u
            INNER JOIN user_expiries ue
                ON u.id = ue.user_id
            WHERE ue.account_expired_at < NOW()
                AND u.account_non_expired = 't';
        """;
        return new JdbcCursorItemReaderBuilder<User>()
            .name("UserAccountExpiredReader")
            .dataSource(dataSource)
            .sql(sql)
            .rowMapper(new DataClassRowMapper<>(User.class))
            .build();
    }

    @Bean
    public ItemWriter<User> userAccountExpiredCompositeWriter(
        ItemWriter<User> userAccountExpiredWriter,
        ItemWriter<User> userAuditWriter
    ) {
        CompositeItemWriter<User> composite = new CompositeItemWriter<>(
            userAccountExpiredWriter,
            userAuditWriter
        );
        return composite;
    }

    @Bean
    public ItemWriter<User> userAccountExpiredWriter() {
        String sql = """
            UPDATE users
            SET account_non_expired = 'f'
            WHERE id = :id
        """;
        return new JdbcBatchItemWriterBuilder<User>()
            .dataSource(dataSource)
            .itemSqlParameterSourceProvider(item -> {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("id", item.id());
                return params;
            })
            .sql(sql)
            .build();
    }
}
