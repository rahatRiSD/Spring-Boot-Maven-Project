package com.javatechie;

import com.javatechie.di.UserApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DependencyInjectionApplication implements CommandLineRunner {

//approach : 2
//	@PostConstruct
//	public void preInitialize(){
//		System.out.println("jdbc connection");
//	}

	public static void main(String[] args) {
	 SpringApplication.run(DependencyInjectionApplication.class, args);
		System.out.println("spring boot run method executed .....");
//		UserApp app = context.getBean(UserApp.class);
//		app.loadUserFeeds();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("execute CommandLineRunner run() method");
		System.out.println("establish JDBC connection");
	}
}
