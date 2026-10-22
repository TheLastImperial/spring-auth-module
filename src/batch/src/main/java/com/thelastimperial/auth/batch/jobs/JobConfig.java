package com.thelastimperial.auth.batch.jobs;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class JobConfig {
    private final JobRepository jobRepository;

    @Bean
    public Job credentialExpiredJob(Step credentialExpiredStep) {
        return new JobBuilder("CredentialExpiredJob", jobRepository)
            .start(credentialExpiredStep)
            .build();
    }

    @Bean
    public Job accountExpiredJob(Step accountExpiredStep) {
        return new JobBuilder("AccountExpiredJob", jobRepository)
            .start(accountExpiredStep)
            .build();
    }
}
