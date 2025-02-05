package org.example.carebridge.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job userReportJob;


    @Scheduled(cron ="0 0 0 * * ?")
//    @Scheduled(cron ="0 * * * * ?") 테스트용 1분마다
    public void runBatchJob() {
        try {
            System.out.println("Running Batch Job : " + LocalDateTime.now());

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 중복 실행을 방지하기 위해 파라미터를 다르게 설정
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(userReportJob, jobParameters);

            System.out.println("Ending Batch Job : " + execution.getStatus());
        } catch (Exception e) {
            System.out.println("Error ! Job running : " + e.getMessage());
        }
    }
}
