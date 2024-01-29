package controller;

import db.Database;
import model.User;
import utils.HtmlBuilder;
import utils.SessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseBuilder;
import webserver.http.response.enums.HttpStatus;

import java.io.IOException;

public class QnaController {

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
}
