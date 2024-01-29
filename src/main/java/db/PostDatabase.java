package db;

import com.google.common.collect.Maps;
import model.Post;

import java.util.Collection;
import java.util.Map;

public class PostDatabase {
    private static Map<String, Post> posts = Maps.newHashMap();

    public static void addPost(Post post) {
        posts.put(post.getWriter(), post);
    }

    public static Collection<Post> findAllPosts() {
        return posts.values();
    }

}
