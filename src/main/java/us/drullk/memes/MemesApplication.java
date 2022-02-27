package us.drullk.memes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class MemesApplication {
	public static final Logger LOGGER = LoggerFactory.getLogger(MemesApplication.class);

	public static void main(String... args) {
		SpringApplication.run(MemesApplication.class, args);
	}

	@GetMapping("/")
	public String showUploadForm(Model model) {
		// TODO convert method return, to be able to send the client the upload form through Thymeleaf
		return "uploadForm";
	}
}
