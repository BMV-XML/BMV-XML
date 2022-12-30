package xml.stamp.service.stamp.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xml.stamp.service.stamp.service.jaxb.JaxLoader;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		System.out.println("Pokret!");

	/*	JaxLoader l = new JaxLoader();
		l.unmarshalling("./data/instance1.xml");

		l.changeColors();

	//	l.marshalling("./data/stamp-new.xml");
	*/

	}
}
