package scorp.socialmedia.post.model.dto;

public class ResponseGetPosts {
    private Integer id;
    private String description;
    private GetPostsUserDto owner;
    private String image;
    private Long created_at;
    private Boolean liked;

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

    public GetPostsUserDto getOwner() {
        return owner;
    }

    public void setOwner(GetPostsUserDto owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
