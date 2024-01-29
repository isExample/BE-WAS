package service;

import db.PostDatabase;
import model.Post;

import java.util.Map;

public class PostService {

    public boolean writePost(Map<String, String> bodyParams){
        if (!validateParams(bodyParams)){
            return false;
        }
        Post newPost = createPost(bodyParams);
        PostDatabase.addPost(newPost);
        return true;
    }

    private Post createPost(Map<String, String> bodyParams) {
        String writer = bodyParams.get("writer");
        String title = bodyParams.get("title");
        String contents = bodyParams.get("contents");
        return new Post(writer, title, contents);
    }

    private boolean validateParams(Map<String, String> bodyParams){
        return bodyParams.containsKey("writer") && !bodyParams.get("writer").isEmpty()
                && bodyParams.containsKey("title") && !bodyParams.get("title").isEmpty()
                && bodyParams.containsKey("contents") && !bodyParams.get("contents").isEmpty();
    }
}
