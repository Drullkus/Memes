package us.drullk.memes.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import us.drullk.memes.MemesApplication;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "meme_entry")
public class Meme {
    @Id
    @GeneratedValue
    @Getter
    private long id; // Auto-enumerates

    // TODO
    @Column(name = "poster")
    @Getter
    @Setter
    private long poster;

    @Column(name = "filename")
    @Getter
    @Setter
    private String fileName;

    @Column(name = "image_type")
    @Getter
    @Setter
    private String imageType;

    @Column(name = "image_bytes", columnDefinition = "bytea")
    @Getter
    @Setter
    private byte[] imageBytes;

    @Nullable
    public static Meme attemptBuild(MultipartFile file) {
        Resource resource = file.getResource();

        if (resource.isFile() && resource.isReadable()) {
            return null;
        } else {

            try {
                Meme meme = new Meme();

                meme.setFileName(file.getOriginalFilename());
                meme.setImageType(file.getContentType());
                meme.setImageBytes(file.getBytes().clone());

                return meme;
            } catch (IOException e) {
                MemesApplication.LOGGER.error("Could not build meme!", e);

                return null;
            }
        }
    }
}
