package com.example.bookmarking_demo.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.example.bookmarking_demo.repository" })
public class PersistenceConfig {

}
