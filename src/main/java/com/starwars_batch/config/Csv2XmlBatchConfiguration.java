package com.starwars_batch.config;


import com.starwars_batch.listener.BatchListener;
import com.starwars_batch.listener.ReadListener;
import com.starwars_batch.listener.StepListener;
import com.starwars_batch.model.People;
import com.starwars_batch.repository.PeopleRepository;
import com.starwars_batch.tasklet.BatchProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class Csv2XmlBatchConfiguration {


    @Bean
    public ItemReader<People> peopleItemReader() {
        FlatFileItemReader<People> itemReader = new FlatFileItemReader<People>();
        itemReader.setResource(new FileSystemResource("src/main/resources/people.csv"));

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name","birthYear","gender","height","mass","eyeColor","hairColor","skinColor");

        BeanWrapperFieldSetMapper<People> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(People.class);

        DefaultLineMapper<People> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);
        itemReader.setLineMapper(lineMapper);

        return itemReader;

    }

    @Bean
    public ItemWriter<People> peopleItemWriter(){
        StaxEventItemWriter<People> itemWriter = new StaxEventItemWriter<>();

        itemWriter.setResource(new FileSystemResource("src/main/resources/peopleOut.xml"));
        itemWriter.setRootTagName("peoples");
        itemWriter.setOverwriteOutput(true);

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(People.class);

        itemWriter.setMarshaller(marshaller);

        return itemWriter;
    }

    @Bean
    public RepositoryItemWriter<People> repositoryItemWriter(PeopleRepository peopleRepository){
        RepositoryItemWriter<People> repositoryItemWriter = new RepositoryItemWriter<>();

        repositoryItemWriter.setRepository(peopleRepository);
        repositoryItemWriter.setMethodName("save");

        return repositoryItemWriter;
    }



    @Bean
    public Step csvStep(StepBuilderFactory factory,
                        ItemReader itemReader,
                        RepositoryItemWriter itemWriter,
                        ReadListener readListener,
                        ItemProcessor batchProcessor,
                        StepListener stepListener){
        return factory
                .get("csvStep")
                .listener(stepListener)
                .chunk(10)
                .reader(itemReader)
                .listener(readListener)
                .writer(itemWriter)
                .processor(batchProcessor)
                .build();
    }

    @Bean
    public Job job(JobBuilderFactory factory, Step csvStep, BatchListener batchListener){
        return factory.get("job")
                .incrementer(new RunIdIncrementer())
                .listener(batchListener)
                .start(csvStep).build();
    }
}
