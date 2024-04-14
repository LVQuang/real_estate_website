package edu.hqh.real_estate_website;

import edu.hqh.real_estate_website.model.Content;
import edu.hqh.real_estate_website.service.ContentService;
import edu.hqh.real_estate_website.util.DataSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;


@SpringBootApplication
public class App{

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Autowired
	private ContentService contentService;

	@Bean
	public CommandLineRunner run(){
		return args -> {
			addContents();
		};
	}

	public void addContents() {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			String description = "Description Of Content " + i;
			String address = "Address " + i;
			double price = (random.nextInt(91) + 10) * 1000;
			Content content = new Content(description, address, price);
			contentService.addAndUpdate(content);
		}
	}
}
