package scorp.socialmedia.post.model.entity;

import jakarta.persistence.*;
import scorp.socialmedia.common.model.entity.BaseModel;


@Entity
@Table(name = "post")
public class Post extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "image")
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

}
