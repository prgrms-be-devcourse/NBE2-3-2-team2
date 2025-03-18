package com.example.letmovie.domain.payment.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class PaymentBatchListener implements JobExecutionListener {
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("하루 정산 작업을 시작합니다.");
        log.info("Job Parameter = : {}", jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        BatchStatus status = jobExecution.getStatus();
        if (status == BatchStatus.COMPLETED) {
            log.info("하루 정산 작업 성공 ");
        } else if (status == BatchStatus.FAILED) {
            log.error("하루 정산 작업 실패");
            logJobFailureDetails(jobExecution);
        }
    }

    private void logJobFailureDetails(JobExecution jobExecution) {
        log.error("상태: {}", jobExecution.getExitStatus().getExitDescription());
        jobExecution.getAllFailureExceptions()
                .forEach(ex -> log.error("예외: ", ex));
    }
}