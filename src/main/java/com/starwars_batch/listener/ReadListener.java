package com.starwars_batch.listener;

import com.starwars_batch.model.People;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class ReadListener implements ItemReadListener<People>{

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(People people) {
        System.out.println("Gender read : "+people.getGender());
    }

    @Override
    public void onReadError(Exception e) {

    }
}
