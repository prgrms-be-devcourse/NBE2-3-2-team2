package com.example.letmovie.domain.payment.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonthlyPaymentScheduler {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Scheduled(cron = "0 30 23 * * *", zone = "Asia/Seoul")
    public void runMonthlyPaymentJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("datetime", LocalDateTime.now().toString())
                    .addString("date", LocalDateTime.now().toString())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(
                    jobRegistry.getJob("MonthlyPaymentJob"), jobParameters
            );
        } catch (Exception e) {
            log.error("하루 정산 실패", e);
        }
    }
}
