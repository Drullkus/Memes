package us.drullk.memes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.drullk.memes.MemesApplication;
import us.drullk.memes.database.UserRepository;
import us.drullk.memes.database.entity.UserData;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    //@GetMapping("/login")
    //public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal/*, RedirectAttributes redirectAttributes*/) {
    //	// TODO Rebuild into registering users into DB
    //	return Collections.singletonMap("name", principal.getAttribute("login"));
    //}

	@GetMapping(value = "/id/{serial:.+}")
	public ResponseEntity<UserData> searchWithID(@PathVariable Long serial) {
		return this.userRepository.findById(serial).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/name/{name:.+}")
	public ResponseEntity<UserData> searchWithName(@PathVariable String name) {
		return this.userRepository.findByDisplayName(name).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Long> countUsers() {
		return ResponseEntity.ok(this.userRepository.count());
	}

	@GetMapping(value = "/all")
	public ResponseEntity<UserData[]> allUsers() {
		return ResponseEntity.ok(this.userRepository.findAll().toArray(UserData[]::new));
	}

    // TODO: Rebuild into a test
	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List<UserData> allUsers = this.userRepository.findAll();
		MemesApplication.LOGGER.info("User count: " + allUsers.size());

		UserData drullkusUser = new UserData();
		drullkusUser.setDisplayName("drullkus");
		drullkusUser.setGithubUserID("drullkus");
        MemesApplication.LOGGER.info("Adding Drullkus user...");
		this.userRepository.save(drullkusUser);

		allUsers = this.userRepository.findAll();
        MemesApplication.LOGGER.info("User count re-queried: " + allUsers.size());
	}
}
