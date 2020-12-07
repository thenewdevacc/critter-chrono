package com.udacity.jdnd.course3.critter.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username("udacity");
        dataSourceBuilder.password(securepasswordservice());
        return dataSourceBuilder.build();
    }

    private String securepasswordservice() {
        return "password";
    }
}
