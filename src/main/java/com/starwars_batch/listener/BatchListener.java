package com.starwars_batch.listener;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import javax.batch.runtime.JobExecution;

@Component
public class BatchListener implements JobExecutionListener{
    @Override
    public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
        System.out.println("before job");
    }

    @Override
    public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
        System.out.println("after job");
    }
}
