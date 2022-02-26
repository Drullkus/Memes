package us.drullk.memes.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.drullk.memes.MemesApplication;
import us.drullk.memes.database.UserRepository;
import us.drullk.memes.database.entity.Memer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal/*, RedirectAttributes redirectAttributes*/) {
    	// TODO Rebuild into registering users into DB
    	return Collections.singletonMap("name", principal.getAttribute("login"));
    }

    // TODO: Rebuild into a test
	/*@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List<Memer> allUsers = this.userRepository.findAll();
		MemesApplication.LOGGER.info("User count: " + allUsers.size());

		Memer drullkusUser = new Memer();
		drullkusUser.setDisplayName("Drullkus");
		drullkusUser.setGithubUserID("Drullkus");
        MemesApplication.LOGGER.info("Adding Drullkus user...");
		this.userRepository.save(drullkusUser);

		allUsers = this.userRepository.findAll();
        MemesApplication.LOGGER.info("User count re-queried: " + allUsers.size());
	}*/
}
