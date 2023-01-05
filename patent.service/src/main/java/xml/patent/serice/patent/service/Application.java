package xml.patent.serice.patent.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		/*
		LoaderValidation l = new LoaderValidation();
		l.unmarshalling("./data/pat/patent.xml");
		l.changeRecepient();
		l.marshalling("./data/res/new-patent.xml");
		*/
		try {
			//PDFTransformer PDFTransformer = new PDFTransformer();
			//PDFTransformer.generatePDF();
			//XHTMLTransformer xhtmlTransformer = new XHTMLTransformer();
			//xhtmlTransformer.generateMYHTML();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
