package xml.patent.serice.patent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xml.patent.serice.patent.service.db.ExistManager;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {
	public static String IMAGE_DIR;

	//@Autowired
	//private static ExistManager existManager;

	public static void main(String[] args) throws IOException {
		IMAGE_DIR = new File(".").getCanonicalPath() + "\\src\\main\\resources\\static\\";
		SpringApplication.run(Application.class, args);
	}

}
