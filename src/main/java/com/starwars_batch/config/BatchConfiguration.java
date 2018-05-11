package com.starwars_batch.config;

import com.starwars_batch.tasklet.HelloWorldTasklet;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableBatchProcessing
public class BatchConfiguration {


    @Bean
    public Step helloWorldStep(StepBuilderFactory stepBuilderFactory, HelloWorldTasklet helloWorldTasklet){
    return stepBuilderFactory
                        .get("helloWorld")
                        .tasklet(helloWorldTasklet)
                        .build();
    }


    public Job job(JobBuilderFactory jobBuilderFactory,Step helloWorldStep){
        return jobBuilderFactory.get("job").start(helloWorldStep).build();
    }
}
