package scorp.socialmedia.like.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import scorp.socialmedia.common.model.entity.BaseModel;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.user.model.entity.User;

@Entity
@Table(name = "likes")
public class Like extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "post_id")
    private Integer post_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }
}
