package scorp.socialmedia.follow.model.entity;

import jakarta.persistence.*;
import scorp.socialmedia.common.model.entity.BaseModel;

@Entity
@Table(name = "follow")
public class Follow extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "follower_id")
    private Integer follower_id;

    @Column(name = "following_id")
    private Integer following_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(Integer follower_id) {
        this.follower_id = follower_id;
    }

    public Integer getFollowing_id() {
        return following_id;
    }

    public void setFollowing_id(Integer following_id) {
        this.following_id = following_id;
    }
}
