package scorp.socialmedia.post.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.post.model.dto.RequestCreatePost;
import scorp.socialmedia.post.model.dto.RequestMixByOwners;
import scorp.socialmedia.post.model.dto.ResponseCreatePost;
import scorp.socialmedia.post.model.dto.ResponseMixByOwners;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.post.model.mapper.IPostMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper implements IPostMapper {
    @Override
    public Post requestCreatePostToPost(RequestCreatePost requestCreatePost) {
        Post post = new Post();
        post.setDescription(requestCreatePost.getDescription());
        post.setImage(requestCreatePost.getImage());
        post.setUser_id(requestCreatePost.getUser_id());
        return post;
    }

    @Override
    public ResponseCreatePost postToResponseCreatePost(Post newPost) {
        ResponseCreatePost responseCreatePost = new ResponseCreatePost();
        responseCreatePost.setId(newPost.getId());
        responseCreatePost.setUser_id(newPost.getUser_id());
        return responseCreatePost;
    }

    @Override
    public List<ResponseMixByOwners> requestListToResponseList(RequestMixByOwners[] responseList) {
        List<ResponseMixByOwners> responseMixByOwnersList = new ArrayList<>();
        for (RequestMixByOwners request: responseList) {
            ResponseMixByOwners responseMixByOwners = new ResponseMixByOwners();
            responseMixByOwners.setOwner_id(request.getOwner_id());
            responseMixByOwners.setId(request.getId());
            responseMixByOwnersList.add(responseMixByOwners);
        }
        return responseMixByOwnersList;
    }


}
