package com.example.letmovie.domain.payment.batch;

import com.example.letmovie.domain.payment.entity.DailyPayment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import com.example.letmovie.domain.payment.repository.MonthlyPaymentRepository;
import com.example.letmovie.domain.payment.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MonthlyPaymentConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final MonthlyPaymentRepository monthlyPaymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentBatchListener paymentBatchListener;
    private final PaymentLoggerListener paymentLoggerListener;

    @Bean
    public Job MonthlyPaymentJob() {
        return new JobBuilder("MonthlyPaymentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(paymentBatchListener)
                .listener(paymentLoggerListener)
                .start(MonthlyPaymentStep())
                .build();
    }

    @Bean
    public Step MonthlyPaymentStep() {
        return new StepBuilder("MonthlyPaymentStep", jobRepository)
                .<PaymentHistory, DailyPayment>chunk(10, platformTransactionManager)
                .reader(MonthlyReader())
                .processor(MonthlyProcessor())
                .writer(paymentWriter())
                .faultTolerant()
                .retry(Exception.class)
                .retryLimit(3)
                .retryPolicy(customRetryPolicy())
                .skip(Exception.class)
                .skipPolicy(customSkipPolicy())
                .build();
    }

    @Bean
    public RetryPolicy customRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(Exception.class, true);
        return new SimpleRetryPolicy(3, retryableExceptions);
    }

    @Bean
    public SkipPolicy customSkipPolicy() {
        return new SkipPolicy() {
            @Override
            public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
                if (t instanceof DataIntegrityViolationException) {
                    log.error("예외발생 customSkipPolicy");
                    return false;
                }
                return skipCount < 3;
            }
        };
    }

    @Bean
    public RepositoryItemReader<PaymentHistory> MonthlyReader() {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("approvedAt", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<PaymentHistory>()
                .name("MonthlyReader")
                .pageSize(10)
                .repository(paymentHistoryRepository)
                .methodName("findByPaymentStatus")
                .arguments(PaymentStatus.PAYMENT_SUCCESS)
                .sorts(sorts)
                .build();
    }

    @Bean
    public ItemProcessor<PaymentHistory, DailyPayment> MonthlyProcessor() {
        Map<LocalDate, Long> dailyCountMap = new HashMap<>();
        Map<LocalDate, Integer> dailyAmountMap = new HashMap<>();

        return new ItemProcessor<PaymentHistory, DailyPayment>() {
            @Override
            public DailyPayment process(PaymentHistory history) throws Exception {
                try {
                    LocalDateTime paymentDateTime = history.getApprovedAt();
                    if (paymentDateTime == null) {
                        log.error("관련아이디 오류 에러 : {}", history.getId());
                        throw new IllegalStateException("결제 DateTime이 널타입으로 체크");
                    }

                    LocalDate paymentDate = paymentDateTime.toLocalDate();
                    dailyCountMap.merge(paymentDate, 1L, Long::sum);
                    dailyAmountMap.merge(paymentDate, history.getAmount(), Integer::sum);

                    return DailyPayment.builder()
                            .year(paymentDate.getYear())
                            .month(paymentDate.getMonthValue())
                            .day(paymentDate.getDayOfMonth())
                            .totalCount(dailyCountMap.get(paymentDate))
                            .totalAmount(dailyAmountMap.get(paymentDate))
                            .build();
                } catch (Exception e) {
                    log.error("결제내역ID처리 도중 오류: {}", history.getId(), e);
                    throw e;
                }
            }
        };
    }

    @Bean
    public ItemWriter<DailyPayment> paymentWriter() {
        return items -> {
            try {
                monthlyPaymentRepository.saveAll(items);
            } catch (Exception e) {
                log.error("일일정산Writer작업중 오류", e);
                throw e;
            }
        };
    }
}

//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager platformtransactionManager;
//    private final MonthlyPaymentRepository monthlyPaymentRepository;
//    private final PaymentHistoryRepository paymentHistoryRepository;
//
//    @Bean
//    public Job MonthlyPaymentJob() {
//        return new JobBuilder("MonthlyPaymentJob", jobRepository)
//                .start(MonthlyPaymentStep())
//                .build();
//    }
//
//    @Bean
//    public Step MonthlyPaymentStep() {
//        return new StepBuilder("MonthlyPaymentStep",jobRepository)
//                .<PaymentHistory, MonthlyPayment>chunk(10, platformtransactionManager)
//                .reader(MonthlyReader())
//                .processor(MonthlyProcessor())
//                .writer(paymentWriter())
//                .build();
//    }
//
//    @Bean
//    public RepositoryItemReader<PaymentHistory> MonthlyReader() {
//        Map<String, Sort.Direction> sorts = new HashMap<>();
//        sorts.put("approvedAt", Sort.Direction.DESC);
//        return new RepositoryItemReaderBuilder<PaymentHistory>()
//                .name("MonthlyReader()")
//                .pageSize(10)
//                .repository(paymentHistoryRepository)
//                .methodName("findByPaymentStatus")
//                .arguments(PaymentStatus.PAYMENT_SUCCESS)
//                .pageSize(10)
//                .sorts(sorts)
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<PaymentHistory, MonthlyPayment> MonthlyProcessor() {
//        Map<LocalDate, Long> dailyCountMap = new HashMap<>();
//
//        return new ItemProcessor<PaymentHistory, MonthlyPayment>() {
//            @Override
//            public MonthlyPayment process(PaymentHistory history) {
//                LocalDateTime paymentDateTime = history.getApprovedAt();
//                LocalDate paymentDate = paymentDateTime.toLocalDate();
//
//                // 해당 날짜의 카운트를 증가
//                dailyCountMap.merge(paymentDate, 1L, Long::sum);
//
//                return MonthlyPayment.builder()
//                        .year(paymentDate.getYear())
//                        .month(paymentDate.getMonthValue())
//                        .day(paymentDate.getDayOfMonth())
//                        .totalCount(dailyCountMap.get(paymentDate))
//                        .build();
//            }
//        };
//    }
//
//    @Bean
//    public ItemWriter<MonthlyPayment> paymentWriter() {
//        return items -> monthlyPaymentRepository.saveAll(items);
//    }

