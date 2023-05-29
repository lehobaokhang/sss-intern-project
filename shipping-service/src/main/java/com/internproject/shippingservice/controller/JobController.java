package com.internproject.shippingservice.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
    private JobLauncher jobLauncher;
    private Job provinceJob;

    private Job districtJob;

    @Autowired
    public JobController(JobLauncher jobLauncher,
                         @Qualifier("provinceJob") Job provinceJob,
                         @Qualifier("districtJob") Job districtJob) {
        this.jobLauncher = jobLauncher;
        this.provinceJob = provinceJob;
        this.districtJob = districtJob;
    }

    @PostMapping("/province/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> importProvinces() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt: ", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(provinceJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }

    @PostMapping("/district/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> importDistrict() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt: ", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(districtJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }
}
