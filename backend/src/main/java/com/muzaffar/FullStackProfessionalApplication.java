package com.muzaffar;

import com.github.javafaker.Faker;
import com.muzaffar.customer.Customer;
import com.muzaffar.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@SpringBootApplication
public class FullStackProfessionalApplication {



	public static void main(String[] args) {
		SpringApplication.run(FullStackProfessionalApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository) {
		return args -> {
			var faker = new Faker();
			var random = new Random();
			var name = faker.name();
			var firstName = name.firstName();
			var lastName = name.lastName();


			Customer customer = new Customer(
					firstName + " " + lastName,
					firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
					random.nextInt(16, 99));
			customerRepository.save(customer);
		};
	}

}
