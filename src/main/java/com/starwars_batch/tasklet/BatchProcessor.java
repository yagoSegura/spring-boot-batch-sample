package com.starwars_batch.tasklet;

import com.starwars_batch.model.People;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BatchProcessor implements ItemProcessor<People,People> {

    @Override
    public People process(People people) throws Exception {
        if(people.getGender().equals("n/a")){
            people.setGender("Droid");
        }
        return people;
    }
}
