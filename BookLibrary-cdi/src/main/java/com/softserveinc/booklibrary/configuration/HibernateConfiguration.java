package com.softserveinc.booklibrary.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	//TODO If left unspecified, the name of the bean is the name of the annotated method. If specified, the method name is ignored.
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(postgresqlDataSource());
		//TODO Set whether to use Spring-based scanning for entity classes in the classpath...
		entityManagerFactoryBean.setPackagesToScan("com.softserveinc.booklibrary");
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	//TODO why do you need postgresql prefix here?
	public DataSource postgresqlDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		//TODO these properties should be extracted to a property file
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/booklibrary");
		dataSource.setUsername("booklibrary");
		dataSource.setPassword("Qwerty12");
		dataSource.setMaximumPoolSize(5);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		//TODO you can autowire existing beans
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		//TODO extract to a property file
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		return hibernateProperties;
	}
}
