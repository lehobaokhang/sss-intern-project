package com.internproject.productservice.aop;

import com.internproject.productservice.dto.ProductCsv;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfig {
    @Before("execution(public * com.internproject.productservice.config.ProductProcessor.process(..)) ProductCsv productCsv")
    public void addSellerId(ProductCsv productCsv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        productCsv.setSellerId(userId);
        System.out.println(userId);
    }
}
