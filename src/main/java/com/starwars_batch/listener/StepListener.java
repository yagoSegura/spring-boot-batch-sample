package com.starwars_batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepListener implements StepExecutionListener{
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("before step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("after step");
        System.out.println(stepExecution.getSummary());
        return ExitStatus.COMPLETED;
    }
}
