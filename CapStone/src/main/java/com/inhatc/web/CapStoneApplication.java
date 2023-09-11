package com.inhatc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = {"com.inhatc.web.repository"}) // com.my.jpa.repository 하위에 있는 jpaRepository를 상속한 repository scan
@EntityScan(basePackages = {"com.inhatc.web.entity"}) // com.my.jpa.entity 하위에 있는 @Entity 클래스 scan
public class CapStoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapStoneApplication.class, args);
	}
	
	@Bean
	   public BCryptPasswordEncoder bCryptPasswordEncoder() {
	      return new BCryptPasswordEncoder();
	   }

}
