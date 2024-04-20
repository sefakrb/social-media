package scorp.socialmedia.post.model.dto;

import java.util.List;

public class RequestGetPosts {
    private Integer user_id;
    private List<Integer> post_ids;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<Integer> getPost_ids() {
        return post_ids;
    }

    public void setPost_ids(List<Integer> post_ids) {
        this.post_ids = post_ids;
    }
}
