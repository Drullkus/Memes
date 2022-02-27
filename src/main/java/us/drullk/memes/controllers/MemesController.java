package us.drullk.memes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import us.drullk.memes.MemesApplication;
import us.drullk.memes.database.MemeRepository;
import us.drullk.memes.database.entity.Meme;

@RestController
@RequestMapping("api/memes")
public class MemesController {
    @Autowired
    private MemeRepository memeRepository;

    @PostMapping("/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        Meme memeUploaded = Meme.attemptBuild(file);

        if (memeUploaded != null)
            this.memeRepository.save(memeUploaded);
    }

    @GetMapping("/image/{id:.+}")
    public ResponseEntity<byte[]> sendImage(@PathVariable Long id) {
        try {
            var meme = this.memeRepository.getById(id);

            MediaType type = MediaType.parseMediaType(meme.getImageType());

            return ResponseEntity.ok().contentType(type).header(HttpHeaders.CONTENT_DISPOSITION).body(meme.getImageBytes());
        } catch (Exception e) {
            MemesApplication.LOGGER.error("Could not send image to client!", e);

            return ResponseEntity.internalServerError().build(); // TODO Further feedback about error
        }
    }

    @GetMapping("/id/{serial:.+}")
    public ResponseEntity<Meme> searchWithID(@PathVariable Long serial) {
        return this.memeRepository.findById(serial).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public ResponseEntity<String> countMemes() {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body("" + this.memeRepository.count());
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Meme[]> allMemes() {
        return ResponseEntity.ok(this.memeRepository.findAll().toArray(Meme[]::new));
    }
}
