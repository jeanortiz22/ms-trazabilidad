package com.pragma.plazoleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class TrazabilidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrazabilidadApplication.class, args);
	}

}
