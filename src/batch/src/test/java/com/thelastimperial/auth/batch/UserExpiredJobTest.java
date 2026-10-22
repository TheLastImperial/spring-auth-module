package com.thelastimperial.auth.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.test.JobOperatorTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import lombok.extern.slf4j.Slf4j;

@SpringBatchTest
@SpringBootTest
@Slf4j
public class UserExpiredJobTest {
    @Autowired
    @Qualifier("credentialExpiredJob")
    Job credentialExpiredJob;
    @Autowired
    @Qualifier("accountExpiredJob")
    Job accountExpiredJob;
    @Autowired
    JobOperatorTestUtils jobOperatorTestUtils;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JobRepositoryTestUtils jobRepositoryTestUtils;

    // @Test
    public void credentialExpiredJobTests() throws Exception {
        log.info("JobName: {}", credentialExpiredJob.getName());
        jobOperatorTestUtils.setJob(credentialExpiredJob);
        JobExecution jobExecution = jobOperatorTestUtils.startJob();
        assertEquals(ExitStatus.COMPLETED, jobExecution);
        assertEquals(1,
            JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "user_audits", "action='CREDENTIALS_EXPIRED'"
            )
        );
        assertEquals(
            1,
            JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate, "users", "credentials_non_expired='f'"
            )
        );
    }

    // @Test
    public void accountExpiredJobTest() throws Exception {
        jobOperatorTestUtils.setJob(accountExpiredJob);
        JobExecution jobExecution = jobOperatorTestUtils.startJob();
        assertEquals(ExitStatus.COMPLETED, jobExecution);
        assertEquals(1,
            JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "user_audits", "action='ACCOUNT_EXPIRED'"
            )
        );
        assertEquals(
            1,
            JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate, "users", "account_non_expired='f'"
            )
        );
    }
}
