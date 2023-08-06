package com.logistics.torch.config;

import com.logistics.torch.model.CenternetFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfigration {

    @Bean
    public CenternetFactoryBean centernetFactoryBean(){
        return new CenternetFactoryBean();
    }
}
