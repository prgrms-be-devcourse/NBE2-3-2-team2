package com.example.letmovie.domain.payment.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class PaymentLoggerListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step {} 시작", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step = {} 상태 {}",
            stepExecution.getStepName(), 
            stepExecution.getStatus());
        
        logStepStatistics(stepExecution);
        
        return stepExecution.getExitStatus();
    }

    private void logStepStatistics(StepExecution stepExecution) {
        log.info("Step 관련 통계");
        log.info("Read 건수: {}", stepExecution.getReadCount());
        log.info("Write 건수: {}", stepExecution.getWriteCount());
        log.info("Skip 건수: {}", stepExecution.getSkipCount());
        log.info("Commit 건수: {}", stepExecution.getCommitCount());
        log.info("총 정산 시간: {}ms", stepExecution.getEndTime().getMinute() -
            stepExecution.getStartTime().getMinute());
    }
}