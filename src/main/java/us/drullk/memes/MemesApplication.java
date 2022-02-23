package us.drullk.memes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import us.drullk.memes.database.MemeRepository;
import us.drullk.memes.database.entity.Meme;

import java.io.IOException;
import java.util.stream.Collectors;

@SpringBootApplication
@Controller
public class MemesApplication/* extends WebSecurityConfigurerAdapter*/ {
	public static final Logger logger = LoggerFactory.getLogger(MemesApplication.class);

	//@Autowired
	//private UserRepository userRepository;
	@Autowired
	private MemeRepository memeRepository;

	public static void main(String... args) {
		SpringApplication.run(MemesApplication.class, args);
	}

	// TODO: Rebuild into a test
	/*@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List<Memer> allUsers = this.userRepository.findAll();
		logger.info("User count: " + allUsers.size());

		Memer drullkusUser = new Memer();
		drullkusUser.setDisplayName("Drullkus");
		drullkusUser.setGithubUserID("Drullkus");
		logger.info("Adding Drullkus user...");
		this.userRepository.save(drullkusUser);

		allUsers = this.userRepository.findAll();
		logger.info("User count re-queried: " + allUsers.size());
	}*/

	//@GetMapping("/user")
	//public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal/*, RedirectAttributes redirectAttributes*/) {
	//	// TODO Rebuild into registering users into DB
	//	return Collections.singletonMap("name", principal.getAttribute("login"));
	//}

	@GetMapping("/memes/{id:.+}.png")
	@ResponseBody
	public ResponseEntity<byte[]> serveFile(@PathVariable Long id) {
		byte[] file = this.memeRepository.getById(id).getImageBytes();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
	}

	@GetMapping("/memes/all")
	@ResponseBody
	public ResponseEntity<String> countMemes() {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body("" + this.memeRepository.count());
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("meme_count", this.memeRepository.count());

		//model.addAttribute("files", this.storageService.loadAll().map(
		//				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
		//						"serveFile", path.getFileName().toString()).build().toUri().toString())
		//		.collect(Collectors.toList()));

		return "uploadForm";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		var meme = Meme.attemptBuild(file);

		if (meme == null) return "/";

		this.memeRepository.save(meme);
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	//@Override
	//protected void configure(HttpSecurity http) throws Exception {
	//	http
	//			.authorizeRequests(a -> a
	//					.antMatchers("/", "/error", "/webjars/**").permitAll()
	//					.anyRequest().authenticated()
	//			)
	//			.exceptionHandling(e -> e
	//					.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
	//			)
	//			.csrf(c -> c
	//					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	//			)
	//			.logout(l -> l
	//					.logoutSuccessUrl("/").permitAll()
	//			)
	//			.oauth2Login();
	//}
}
