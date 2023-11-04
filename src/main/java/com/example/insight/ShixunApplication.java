package com.example.insight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

// @EnableCaching		// 开启基于注解的缓存
@SpringBootApplication
public class ShixunApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShixunApplication.class, args);
	}

}
