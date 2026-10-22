package com.thelastimperial.auth.batch.steps;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.thelastimperial.auth.batch.domain.User;

@Configuration
public class UserAuditWriterConfig {
    private final UUID batchUserId;
    private final DataSource dataSource;

    public UserAuditWriterConfig(
        @Value("${com.thelastimperial.auth.batch.userId}")
        UUID batchUserId,
        DataSource dataSource
    ) {
        this.batchUserId = batchUserId;
        this.dataSource = dataSource;
    }

    @Bean
    public ItemWriter<User> userAuditWriter() {
        String sql = """
            INSERT INTO user_audits(id, user_id, action_id, updated_by, created_at, updated_at)
            VALUES(gen_random_uuid(), :id, :action, :updatedBy, NOW(), NOW())
        """;
        return new JdbcBatchItemWriterBuilder<User>()
            .dataSource(dataSource)
            .sql(sql)
            .itemSqlParameterSourceProvider(item -> {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("id", item.id());
                params.addValue("action", item.action());
                params.addValue("updatedBy", batchUserId);
                return params;
            })
            .build();
    }
}
