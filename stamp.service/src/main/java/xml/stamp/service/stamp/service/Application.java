package xml.stamp.service.stamp.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xml.stamp.service.stamp.service.jaxb.JaxLoader;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {
	public static String IMAGE_DIR;

	public static void main(String[] args) throws IOException {

		IMAGE_DIR = new File(".").getCanonicalPath() + "\\src\\main\\resources\\static\\";
		SpringApplication.run(Application.class, args);

		System.out.println("Pokret!");

	}
}
