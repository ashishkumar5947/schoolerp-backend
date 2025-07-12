package com.schoolerp.mysaas.config;

import com.schoolerp.mysaas.common.tenant.SchemaMultiTenantConnectionProvider;
import com.schoolerp.mysaas.common.tenant.TenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final SchemaMultiTenantConnectionProvider provider;
    private final TenantIdentifierResolver resolver;

    @Autowired
    public HibernateConfig(SchemaMultiTenantConnectionProvider provider, TenantIdentifierResolver resolver) {
        this.provider = provider;
        this.resolver = resolver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, EntityManagerFactoryBuilder builder) {
        Map<String, Object> props = new HashMap<>();

        props.put("hibernate.multiTenancy", "SCHEMA");
        props.put("hibernate.multi_tenant_connection_provider", provider);
        props.put("hibernate.tenant_identifier_resolver", resolver);
        props.put("hibernate.show_sql", true);
        props.put("hibernate.format_sql", true);
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return builder
                .dataSource(dataSource)
                .packages("com.schoolerp.mysaas") // replace with your entity base package
                .properties(props)
                .build();
    }
}
