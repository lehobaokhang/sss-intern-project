package com.internproject.userservice.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JobController {
    private JobLauncher jobLauncher;
    private Job roleJob;
    private Job userJob;

    @Autowired
    public JobController(JobLauncher jobLauncher,
                         @Qualifier("roleJob") Job roleJob,
                         @Qualifier("userJob") Job userJob) {
        this.jobLauncher = jobLauncher;
        this.roleJob = roleJob;
        this.userJob = userJob;
    }

    @PostMapping("/importRoles")
    public void importRoleToDB() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt: ", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(roleJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/importUsers")
    public void importUserToDB() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt: ", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(userJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
