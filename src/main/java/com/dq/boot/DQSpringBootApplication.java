package com.dq.boot;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//Disable Auto Config DataSource & DataSourceTransactionManager
@EnableAutoConfiguration(exclude = { //
   DataSourceAutoConfiguration.class, //
   DataSourceTransactionManagerAutoConfiguration.class })
@ComponentScan("com.dq.*")
public class DQSpringBootApplication extends SpringBootServletInitializer {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DQSpringBootApplication.class);
	}*/

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/dq-utility");
		ApplicationContext ctx = SpringApplication.run(DQSpringBootApplication.class, args);
		 
        String[] beanNames = ctx.getBeanDefinitionNames();
         
        Arrays.sort(beanNames);
         
        for (String beanName : beanNames)
        {
            System.out.println(beanName);
        }
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(applicationClass);
	    }

	    private static Class<DQSpringBootApplication> applicationClass = DQSpringBootApplication.class;
}
