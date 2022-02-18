package us.drullk.memes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.drullk.memes.database.UserRepository;
import us.drullk.memes.database.entity.Memer;

@SpringBootApplication
@RestController
public class MemesApplication extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MemesApplication.class);

	@Autowired
	private UserRepository repository;

	public static void main(String... args) {
		SpringApplication.run(MemesApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List<Memer> allUsers = this.repository.findAll();
		logger.info("User count: " + allUsers.size());

		Memer drullkusUser = new Memer();
		drullkusUser.setDisplayName("Drullkus");
		drullkusUser.setGithubUserID("Drullkus");
		logger.info("Adding Drullkus user...");
		this.repository.save(drullkusUser);

		allUsers = this.repository.findAll();
		logger.info("User count re-queried: " + allUsers.size());
	}

	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		return Collections.singletonMap("name", principal.getAttribute("login"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(a -> a
						.antMatchers("/", "/error", "/webjars/**").permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(e -> e
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
				.csrf(c -> c
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				)
				.logout(l -> l
						.logoutSuccessUrl("/").permitAll()
				)
				.oauth2Login();
	}
}
