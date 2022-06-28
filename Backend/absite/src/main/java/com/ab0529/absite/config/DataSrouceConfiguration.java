package com.ab0529.absite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSrouceConfiguration {
    @Value("${SPRING_DATASOURCE_URL}")
    private String datasoruceURL;
    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String datasourceUsername;
    @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String datasourcePassword;

    @Bean
    public DataSource getDataSrouce() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(datasoruceURL);
        dataSourceBuilder.username(datasourceUsername);
        dataSourceBuilder.password(datasourcePassword);
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        return dataSourceBuilder.build();
    }
}
