package com.example.letmovie.domain.payment.batch;

import com.example.letmovie.domain.payment.entity.MonthlyPayment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import com.example.letmovie.domain.payment.repository.MonthlyPaymentRepository;
import com.example.letmovie.domain.payment.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class MonthlyPaymentConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformtransactionManager;
    private final MonthlyPaymentRepository monthlyPaymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @Bean
    public Job MonthlyPaymentJob() {
        return new JobBuilder("MonthlyPaymentJob", jobRepository)
                .start(MonthlyPaymentStep())
                .build();
    }

    @Bean
    public Step MonthlyPaymentStep() {
        return new StepBuilder("MonthlyPaymentStep",jobRepository)
                .<PaymentHistory, MonthlyPayment>chunk(10, platformtransactionManager)
                .reader(MonthlyReader())
                .processor(MonthlyProcessor())
                .writer(paymentWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<PaymentHistory> MonthlyReader() {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("approvedAt", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<PaymentHistory>()
                .name("MonthlyReader()")
                .pageSize(10)
                .repository(paymentHistoryRepository)
                .methodName("findByPaymentStatus")
                .arguments(PaymentStatus.PAYMENT_SUCCESS)
                .pageSize(10)
                .sorts(sorts)
                .build();
    }

    @Bean
    public ItemProcessor<PaymentHistory, MonthlyPayment> MonthlyProcessor() {
        Map<LocalDate, Long> dailyCountMap = new HashMap<>();

        return new ItemProcessor<PaymentHistory, MonthlyPayment>() {
            @Override
            public MonthlyPayment process(PaymentHistory history) {
                LocalDateTime paymentDateTime = history.getApprovedAt();
                LocalDate paymentDate = paymentDateTime.toLocalDate();

                // 해당 날짜의 카운트를 증가
                dailyCountMap.merge(paymentDate, 1L, Long::sum);

                return MonthlyPayment.builder()
                        .year(paymentDate.getYear())
                        .month(paymentDate.getMonthValue())
                        .day(paymentDate.getDayOfMonth())
                        .totalCount(dailyCountMap.get(paymentDate))
                        .build();
            }
        };
    }

    @Bean
    public ItemWriter<MonthlyPayment> paymentWriter() {
        return items -> monthlyPaymentRepository.saveAll(items);
    }
}
