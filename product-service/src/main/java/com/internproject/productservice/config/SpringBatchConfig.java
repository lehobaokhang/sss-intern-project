package com.internproject.productservice.config;

import com.internproject.productservice.dto.ProductCsv;
import com.internproject.productservice.entity.Category;
import com.internproject.productservice.entity.Product;
import com.internproject.productservice.repository.ICategoryRepository;
import com.internproject.productservice.repository.IProductRepository;
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
    private ICategoryRepository categoryRepository;
    private IProductRepository productRepository;
    private ProductProcessor productProcessor;

    @Autowired
    public SpringBatchConfig(JobBuilderFactory jobBuilderFactory,
                             StepBuilderFactory stepBuilderFactory,
                             ICategoryRepository categoryRepository,
                             IProductRepository productRepository,
                             ProductProcessor productProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productProcessor = productProcessor;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    // Import Category Job
    @Bean
    public FlatFileItemReader<Category> categoryReader() {
        FlatFileItemReader<Category> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/categories.csv"));
        flatFileItemReader.setName("categoryReader");
        flatFileItemReader.setLineMapper(categoryLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public CategoryProcessor categoryProcessor() {
        return new CategoryProcessor();
    }

    @Bean
    public RepositoryItemWriter<Category> categoryWriter() {
        RepositoryItemWriter<Category> writer = new RepositoryItemWriter<>();
        writer.setRepository(categoryRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step categoryStep1() {
        return stepBuilderFactory.get("csv-step").<Category, Category>chunk(10)
                .reader(categoryReader())
                .processor(categoryProcessor())
                .writer(categoryWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "categoryJob")
    public Job runCategoryJob() {
        return jobBuilderFactory.get("importCategories")
                .flow(categoryStep1()).end().build();
    }

    private LineMapper<Category> categoryLineMapper() {
        DefaultLineMapper<Category> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "categoryName");
        BeanWrapperFieldSetMapper<Category> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Category.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    // Import Product Job
    @Bean
    public FlatFileItemReader<ProductCsv> productReader() {
        FlatFileItemReader<ProductCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/products.csv"));
        flatFileItemReader.setName("productReader");
        flatFileItemReader.setLineMapper(productLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public RepositoryItemWriter<Product> productWriter() {
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step productStep1() {
        return stepBuilderFactory.get("csv-step").<ProductCsv, Product>chunk(10)
                .reader(productReader())
                .processor(productProcessor)
                .writer(productWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "productJob")
    public Job runProductJob() {
        return jobBuilderFactory.get("importProducts")
                .flow(productStep1()).end().build();
    }

    private LineMapper<ProductCsv> productLineMapper() {
        DefaultLineMapper<ProductCsv> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "productName", "productCategory", "price", "quantity");
        BeanWrapperFieldSetMapper<ProductCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProductCsv.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
