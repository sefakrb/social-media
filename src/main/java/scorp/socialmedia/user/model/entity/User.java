package scorp.socialmedia.user.model.entity;

import jakarta.persistence.*;
import scorp.socialmedia.common.model.entity.BaseModel;


@Entity
@Table(name = "users")
public class User extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "full_name")
    private String full_name;
    @Column(name = "profile_picture")
    private String profile_picture;
    @Column(name = "bio")
    private String bio;

    public User() {
    }

    public User(Integer id, String username, String email, String full_name, String profile_picture, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.full_name = full_name;
        this.profile_picture = profile_picture;
        this.bio = bio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
