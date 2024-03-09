package com.verygoodbank.tes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    private static final String DEFAULT_SPRING_BATCH_SCHEMA_SCRIPT = "/org/springframework/batch/core/schema-hsqldb.sql";

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript(DEFAULT_SPRING_BATCH_SCHEMA_SCRIPT)
                .build();
    }

    @Bean
    public JdbcTransactionManager transactionManager(final DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

}
