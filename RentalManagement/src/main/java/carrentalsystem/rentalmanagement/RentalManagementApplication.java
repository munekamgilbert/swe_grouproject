package carrentalsystem.rentalmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RentalManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalManagementApplication.class, args);
	}

}

