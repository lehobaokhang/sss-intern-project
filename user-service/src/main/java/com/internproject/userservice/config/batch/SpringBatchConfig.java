package com.internproject.userservice.config.batch;

import com.internproject.userservice.dto.request.UserCsv;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.repository.IUserRepository;
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
    private IUserRepository userRepository;
    private IRoleRepository roleRepository;

    @Autowired
    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory,
                             StepBuilderFactory stepBuilderFactory,
                             IRoleRepository roleRepository,
                             IUserRepository userRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    // Import Role Job
    @Bean
    public FlatFileItemReader<Role> roleReader() {
        FlatFileItemReader<Role> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/roles.csv"));
        flatFileItemReader.setName("roleReader");
        flatFileItemReader.setLineMapper(roleLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public RoleProcessor roleProcessor() {
        return new RoleProcessor();
    }

    @Bean
    public RepositoryItemWriter<Role> roleWriter() {
        RepositoryItemWriter<Role> writer = new RepositoryItemWriter<>();
        writer.setRepository(roleRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step roleStep1() {
        return stepBuilderFactory.get("csv-step").<Role, Role>chunk(10)
                .reader(roleReader())
                .processor(roleProcessor())
                .writer(roleWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "roleJob")
    public Job runRoleJob() {
        return jobBuilderFactory.get("importRoles")
                .flow(roleStep1()).end().build();
    }


    private LineMapper<Role> roleLineMapper() {
        DefaultLineMapper<Role> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "roleName");
        BeanWrapperFieldSetMapper<Role> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Role.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    // Import User Job
    @Bean
    public FlatFileItemReader<UserCsv> userReader() {
        FlatFileItemReader<UserCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
        flatFileItemReader.setName("userReader");
        flatFileItemReader.setLineMapper(userLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public UserProcessor userProcessor() {
        return new UserProcessor();
    }

    @Bean
    public RepositoryItemWriter<User> userWriter() {
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step userStep1() {
        return stepBuilderFactory.get("csv-step").<UserCsv, User>chunk(10)
                .reader(userReader())
                .processor(userProcessor())
                .writer(userWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "userJob")
    public Job runUserJob() {
        return jobBuilderFactory.get("importUsers")
                .flow(userStep1()).end().build();
    }

    private LineMapper<UserCsv> userLineMapper() {
        DefaultLineMapper<UserCsv> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "phone", "dob");
        BeanWrapperFieldSetMapper<UserCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(UserCsv.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
