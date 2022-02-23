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
    private long id;

    //@Column(name = "poster")
    //@Getter
    //@Setter
    //private long poster;

    @Column(name = "image_bytes", columnDefinition = "bytea")
    @Getter
    @Setter
    private byte[] imageBytes;

    public boolean copyFromFile(MultipartFile file) {
        try {
            Resource resource = file.getResource();

            if (resource.isFile() && resource.isReadable())
                this.imageBytes = file.getBytes().clone();
        } catch (IOException e) {
            MemesApplication.logger.error("Could not clone image!", e);

            return false;
        }

        return true;
    }

    @Nullable
    public static Meme attemptBuild(MultipartFile file) {
        Resource resource = file.getResource();

        if (resource.isFile() && resource.isReadable()) {
            return null;
        } else {

            try {
                Meme meme = new Meme();

                meme.imageBytes = file.getBytes().clone();

                return meme;
            } catch (IOException e) {
                MemesApplication.logger.error("Could not build meme!", e);

                return null;
            }
        }
    }
}
