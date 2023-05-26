package com.internproject.productservice.aop;

import com.internproject.productservice.dto.ProductCsv;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfig {
    private final Logger logger = LoggerFactory.getLogger(AopConfig.class);
    @Before("execution(public * com.internproject.productservice.config.ProductProcessor.process(..)) && args(productCsv)")
    public void addSellerId(ProductCsv productCsv) {
        productCsv.setSellerId("99cfb772-53fd-4fa8-81bd-849decc4c241");
        logger.info("In AOP");
    }
}
