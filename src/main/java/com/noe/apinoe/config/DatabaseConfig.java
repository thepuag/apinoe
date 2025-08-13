package com.noe.apinoe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.noe.apinoe.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // Configuraciones adicionales de base de datos si son necesarias
}
