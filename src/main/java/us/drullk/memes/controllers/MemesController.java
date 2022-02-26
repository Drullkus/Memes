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
@RequestMapping("memes")
public class MemesController {
    @Autowired
    private MemeRepository memeRepository;

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

    @GetMapping("/data/{id:.+}")
    public ResponseEntity<Meme> sendJson(@PathVariable Long id) {
        try {
            var meme = this.memeRepository.getById(id);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(meme);
        } catch (Exception e) {
            MemesApplication.LOGGER.error("Could not send image to client!", e);

            return ResponseEntity.internalServerError().build(); // TODO Further feedback about error
        }
    }

    @GetMapping("/count")
    public ResponseEntity<String> countMemes() {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body("" + this.memeRepository.count());
    }

    //@GetMapping("/")
    //public String listUploadedFiles(Model model) {
    //    model.addAttribute("meme_count", this.memeRepository.count());
    //    return "uploadForm";
    //}

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        var meme = Meme.attemptBuild(file);

        if (meme == null) return "/";

        this.memeRepository.save(meme);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
