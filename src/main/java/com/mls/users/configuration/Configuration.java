package com.mls.users.configuration;

import com.mls.users.tools.ExceptionTools;
import com.mls.users.tools.impl.ExceptionToolsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Class with app configurations
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
@Component
@Configurable
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager",
		basePackages = {"com.mls.users"})
public class Configuration {

	/**
	 * JPA
	 */
	@Autowired
	JpaVendorAdapter jpaVendorAdapter;

	/**
	 * Data Source
	 */
	@Autowired
	DataSource dataSource;

	/**
	 * Password salt for MD5-encoding
	 */
	public static final String PASSWORD_SALT = "qwerty123";

	/**
	 * Create Entity Manager
	 * @return Entity Manager
	 */
	@Bean(name = "entityManager")
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	/**
	 * Entity Manager Factory
	 * @return EntityManagerFactory
	 */
	@Primary
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setPackagesToScan("com.mls.users.model");
		emf.setPersistenceUnitName("PersistenceUnit");
		emf.afterPropertiesSet();
		return emf.getObject();
	}

	/**
	 * Platform Transaction Manager
	 * @return Platform Transaction Manager
	 */
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(entityManagerFactory());
		return tm;
	}

	/**
	 * Create Entity Manager
	 * @return Entity Manager
	 */
	@Bean(name = "exceptionTools")
	public ExceptionTools exceptionTools() {
		return new ExceptionToolsImpl();
	}

}
