package com.mls.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Test configuration
 * 
 * Test main classes should sit in the same package as the original service's main class
 * 
 * @author djordi_bm
 * @since 22.06.17
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
public class TestUserServiceApplication {
	public static void main(String[] args) {	
		SpringApplication.run(TestUserServiceApplication.class, args);
	}
}
