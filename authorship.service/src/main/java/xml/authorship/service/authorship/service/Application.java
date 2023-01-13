package xml.authorship.service.authorship.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xml.authorship.service.authorship.service.jaxb.LoaderValidation;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//Loader l = new Loader();
		LoaderValidation l = new LoaderValidation();
		l.unmarshalling();
	}

}
