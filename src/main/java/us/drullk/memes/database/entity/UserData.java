package us.drullk.memes.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "meme_users")
public class UserData {
    @Id
    @GeneratedValue
    @Getter
    private long id;

    @Column(name = "github_userid")
    @Getter
    @Setter
    private String githubUserID;

    @Column(name = "display_name")
    @Getter
    @Setter
    private String displayName;

    // Breadcrumb trail for future features through Github API, especially profile pictures
    public String getAPIUserURL() {
        return "https://api.github.com/users/" + this.githubUserID;
    }

    // TODO Hyperlink usernames to direct to their Github profiles
    public String getHttpUserURL() {
        return "https://github.com/" + this.githubUserID;
    }
}
