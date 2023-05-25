package com.internproject.productservice.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
    private JobLauncher jobLauncher;
    private Job categoryJob;
    private Job productJob;

    @Autowired
    public JobController(JobLauncher jobLauncher,
                         @Qualifier("categoryJob") Job categoryJob,
                         @Qualifier("productJob") Job productJob) {
        this.jobLauncher = jobLauncher;
        this.categoryJob = categoryJob;
        this.productJob = productJob;
    }

    @PostMapping("/category/job")
    @PreAuthorize("hasRole('ADMIN')")
    public void importCategoryToDB() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt: ", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(categoryJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/product/job")
    @PreAuthorize("hasRole('SELLER')")
    public void importProductToDB() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt: ", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(productJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
