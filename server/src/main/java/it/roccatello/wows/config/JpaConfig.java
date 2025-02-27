package it.roccatello.wows.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "it.roccatello.wows.repository")
public class JpaConfig {
  private static final String MODEL_DB_PACKAGES = "it.roccatello.wows.model.db";

  @Autowired
  private Environment env;

  @Autowired
  private ConfigurableListableBeanFactory beanFactory;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(false);

    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.getJpaPropertyMap().put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(beanFactory));
    em.setPackagesToScan(MODEL_DB_PACKAGES);
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());

    return em;
  }

  @Bean
  public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(env.getProperty("spring.datasource.url"));
    dataSource.setUsername(env.getProperty("spring.datasource.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.password"));
    return dataSource;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  public static PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  private Properties additionalProperties() {
    final Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));

    properties.setProperty("hibernate.current_session_context_class",
        env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));

    properties.setProperty("hibernate.jdbc.lob.non_contextual_creation",
        env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));

    properties.setProperty("hibernate.show_sql",
        env.getProperty("spring.jpa.show-sql"));

    properties.setProperty("hibernate.hbm2ddl.schema-generation.script.append",
        env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.schema-generation.script.append"));

    properties.setProperty("hibernate.format_sql",
        env.getProperty("spring.jpa.properties.hibernate.format_sql"));

    properties.setProperty("hibernate.default_schema",
        env.getProperty("spring.jpa.properties.hibernate.default_schema"));

    properties.setProperty("hibernate.implicit_naming_strategy",
        env.getProperty("spring.jpa.hibernate.naming.implicit-strategy"));

    properties.setProperty("hibernate.physical_naming_strategy",
        env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));

    properties.setProperty("hibernate.connection.CharSet",
        env.getProperty("spring.jpa.properties.hibernate.connection.CharSet"));

    properties.setProperty("hibernate.connection.useUnicode",
        env.getProperty("spring.jpa.properties.hibernate.connection.useUnicode"));

    properties.setProperty("hibernate.connection.characterEncoding",
        env.getProperty("spring.jpa.properties.hibernate.connection.characterEncoding"));

    properties.setProperty("hibernate.globally_quoted_identifiers",
        env.getProperty("spring.jpa.properties.hibernate.globally_quoted_identifiers"));

    properties.setProperty("jakarta.persistence.schema-generation.scripts.action",
        env.getProperty("spring.jpa.properties.jakarta.persistence.schema-generation.scripts.action"));

    properties.setProperty("jakarta.persistence.schema-generation.scripts.create-target",
        env.getProperty("spring.jpa.properties.jakarta.persistence.schema-generation.scripts.create-target"));

    properties.setProperty("jakarta.persistence.schema-generation.scripts.create-source",
        env.getProperty("spring.jpa.properties.jakarta.persistence.schema-generation.scripts.create-source"));

    return properties;
  }
}
