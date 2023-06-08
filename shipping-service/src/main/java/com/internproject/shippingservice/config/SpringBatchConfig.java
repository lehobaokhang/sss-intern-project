package com.internproject.shippingservice.config;

import com.internproject.shippingservice.dto.DistrictDTO;
import com.internproject.shippingservice.entity.District;
import com.internproject.shippingservice.entity.Province;
import com.internproject.shippingservice.repository.IDistrictRepository;
import com.internproject.shippingservice.repository.IProvinceRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private IProvinceRepository provinceRepository;
    private IDistrictRepository districtRepository;

    @Autowired
    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory,
                             StepBuilderFactory stepBuilderFactory,
                             IProvinceRepository provinceRepository,
                             IDistrictRepository districtRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    // Import Role Job
    @Bean
    public FlatFileItemReader<Province> provinceReader() {
        FlatFileItemReader<Province> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/provinces.csv"));
        flatFileItemReader.setName("provinceReader");
        flatFileItemReader.setLineMapper(provinceLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public ProvinceProcessor provinceProcessor() {
        return new ProvinceProcessor();
    }

    @Bean
    public RepositoryItemWriter<Province> provinceWriter() {
        RepositoryItemWriter<Province> writer = new RepositoryItemWriter<>();
        writer.setRepository(provinceRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step provinceStep1() {
        return stepBuilderFactory.get("csv-step").<Province, Province>chunk(10)
                .reader(provinceReader())
                .processor(provinceProcessor())
                .writer(provinceWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "provinceJob")
    public Job runProvinceJob() {
        return jobBuilderFactory.get("importProvinces")
                .flow(provinceStep1()).end().build();
    }


    private LineMapper<Province> provinceLineMapper() {
        DefaultLineMapper<Province> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "provinceName", "provinceFullName");
        BeanWrapperFieldSetMapper<Province> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Province.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    // Import District Job
    @Bean
    public FlatFileItemReader<DistrictDTO> districtReader() {
        FlatFileItemReader<DistrictDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/districts.csv"));
        flatFileItemReader.setName("districtReader");
        flatFileItemReader.setLineMapper(districtLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public DistrictProcessor districtProcessor() {
        return new DistrictProcessor();
    }

    @Bean
    public RepositoryItemWriter<District> districtWriter() {
        RepositoryItemWriter<District> writer = new RepositoryItemWriter<>();
        writer.setRepository(districtRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step districtStep1() {
        return stepBuilderFactory.get("csv-step").<DistrictDTO, District>chunk(10)
                .reader(districtReader())
                .processor(districtProcessor())
                .writer(districtWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "districtJob")
    public Job runDistrictJob() {
        return jobBuilderFactory.get("importDistrict")
                .flow(districtStep1()).end().build();
    }

    private LineMapper<DistrictDTO> districtLineMapper() {
        DefaultLineMapper<DistrictDTO> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "districtName", "districtFullName", "provinceId");
        BeanWrapperFieldSetMapper<DistrictDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(DistrictDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
