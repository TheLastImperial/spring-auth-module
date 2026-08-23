package com.thelastimperial.auth.domain.autoconfiguration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EntityScan(basePackages = "com.thelastimperial.auth.domain.entities")
@EnableJpaRepositories(basePackages = "com.thelastimperial.auth.domain.repositories")
public class AuthDomainAutoConfiguration {
}
