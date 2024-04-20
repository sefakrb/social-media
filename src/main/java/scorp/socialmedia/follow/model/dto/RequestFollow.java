package scorp.socialmedia.follow.model.dto;

public class RequestFollow {
    private Integer follower_id;
    private Integer following_id;

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
