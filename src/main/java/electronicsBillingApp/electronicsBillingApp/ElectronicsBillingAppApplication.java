package ElectronicsBillingApp.ElectronicsBillingApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElectronicsBillingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicsBillingAppApplication.class, args);
		System.out.println("Electronics billing App started ......");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
