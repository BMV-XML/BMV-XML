package xml.authorship.service.authorship.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xml.authorship.service.authorship.service.jaxb.LoaderValidation;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

	public static String IMAGE_DIR;
	public static String AUTHORSHIP = "AUTHORSHIP";
	public static String OFFICIAL = "ALL";

    public static void main(String[] args) throws IOException {
		IMAGE_DIR = new File(".").getCanonicalPath() + "\\src\\main\\resources\\static\\";

		SpringApplication.run(Application.class, args);

	}

}
