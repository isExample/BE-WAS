package controller;

import db.Database;
import model.User;
import service.PostService;
import utils.HtmlBuilder;
import utils.SessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseBuilder;
import webserver.http.response.enums.HttpStatus;

import java.io.IOException;
import java.util.Map;

public class PostController {

    private final PostService postService = new PostService();

    public HttpResponse getPostForm(HttpRequest request) throws IOException {
        String sessionId = request.getSessionId();

        // 로그인하지 않은 사용자 처리
        if(!SessionManager.isLoggedInUser(sessionId)){
            return HttpResponseBuilder.getInstance().createRedirectResponse(HttpStatus.FOUND, "/user/login.html");
        }

        // 로그인 사용자 처리
        User user = Database.findUserById(SessionManager.getUserId(sessionId));
        byte[] body = HtmlBuilder.loggedInNavBarHtml("/qna/write.html", user.getName()).getBytes();

        return HttpResponseBuilder.getInstance().createSuccessResponse(HttpStatus.OK, body);
    }

    public HttpResponse writePost(HttpRequest request) {
        Map<String, String> bodyParams = request.getBody();
        if(postService.writePost(bodyParams)){
            // 글작성 성공
            return HttpResponseBuilder.getInstance().createRedirectResponse(HttpStatus.FOUND, "/index.html");
        }
        // 글작성 실패
        return HttpResponseBuilder.getInstance().createErrorResponse(HttpStatus.BAD_REQUEST, "Failed to create post".getBytes());
    }
}
