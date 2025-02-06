package org.example.carebridge.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class UserReportBatchConfig {

    private final DataSource dataSource;

    //Job create
    @Bean
    public Job UserReportJob(JobRepository jobRepository, Step userReportStep) {
        return new JobBuilder("userReportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(userReportStep)
                .build();
    }

    @Bean
    public Step userReportStep(JobRepository jobRepository, //처리 중(커밋 전) JobRepository 에 주기적으로 StepExecution 을 저장함
                               PlatformTransactionManager transactionManager) { // 처리 중 트랜잭션을 시작하고 커밋함
        return new StepBuilder("userReportStep", jobRepository)
                .<Long, Long>chunk(10, transactionManager) // 항목 기반 단계임을 나타내는 이름, 트랜잭션이 커밋 되기 전 처리해야 할 항목 수
                .reader(reportUserCountReader())
                .processor(reportUserCountProcessor())
                .writer(reportUserUpdateStatusWriter())
                .build();
    }

    //Reader
    @Bean
    public JdbcCursorItemReader<Long> reportUserCountReader() {

        return new JdbcCursorItemReaderBuilder<Long>()
                .dataSource(dataSource)
                .name("userReportReader")
                .sql("SELECT reported_user_id FROM user_report GROUP BY reported_user_id HAVING COUNT(*) >= 30")
                .rowMapper((rs, rowNum) -> rs.getLong("reported_user_id"))
//                .rowMapper(new SingleColumnRowMapper<Integer>(Integer.class))
                .build();
    }


    //Processor
    @Bean
    public ItemProcessor<Long, Long> reportUserCountProcessor() {
        return reportedUserId -> reportedUserId;
    }

    //Writer
    @Bean
    public JdbcBatchItemWriter<Long> reportUserUpdateStatusWriter() {
        return new JdbcBatchItemWriterBuilder<Long>()
                .dataSource(dataSource)
                .sql("UPDATE user SET user_status = ? WHERE id = ?")
                .itemPreparedStatementSetter((reportedUserId, ps) -> {
                    // Enum 타입을 String으로 변환
                    ps.setString(1, "REPORT");
                    ps.setLong(2, reportedUserId);
                })
                .build();
    }
}