package utils;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlBuilderTest {

    private String navTemplate;
    @BeforeEach
    void setUp() {
        navTemplate = "<div class=\"container\" id=\"main\">\n" +
                "   <div class=\"col-md-10 col-md-offset-1\">\n" +
                "      <div class=\"panel panel-default\">\n" +
                "          <table class=\"table table-hover\">\n" +
                "              <thead>\n" +
                "                <tr>\n" +
                "                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>\n" +
                "                </tr>\n" +
                "              </thead>\n" +
                "              <tbody>\n" +
                "                <!-- UserList -->\n" +
                "              </tbody>\n" +
                "          </table>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
    }

    @Test
    @DisplayName("비어있는 사용자 목록이 주어졌을 때, userListHtml은 원본 템플릿의 주석만을 제거하고 반환해야 한다")
    void userListHtmlWithEmptyUserList() {
        // Given
        List<User> users = Collections.emptyList();

        // When
        String resultHtml = HtmlBuilder.userListHtml(navTemplate, users);

        // Then
        assertFalse(resultHtml.contains("<!-- UserList -->"));
    }

    @Test
    @DisplayName("비어있지 않은 사용자 목록이 주어졌을 때, userListHtml은 사용자 데이터가 포함된 템플릿을 반환해야 한다")
    void userListHtmlWithNonEmptyUserList() {
        // Given
        List<User> users = Arrays.asList(
                new User("user1", "pw1", "userOne", "user1@example.com"),
                new User("user2", "pw2", "userTwo", "user2@example.com")
        );

        // When
        String resultHtml = HtmlBuilder.userListHtml(navTemplate, users);

        // Then
        assertTrue(resultHtml.contains("userOne") && resultHtml.contains("userTwo"));
    }

    @Test
    @DisplayName("로그인한 사용자에 대해 loggedInNavBarHtml은 플레이스홀더를 로그인한 사용자에 맞게 대체해야 한다")
    void loggedInNavBarHtmlSuccess() throws Exception {
        // Given
        String userName = "TestUser";

        // When
        String resultHtml = HtmlBuilder.loggedInNavBarHtml("/index.html", userName);

        // Then
        assertTrue(resultHtml.contains(userName) && resultHtml.contains("로그아웃") && !resultHtml.contains("로그인") && !resultHtml.contains("회원가입"));
    }

    @Test
    @DisplayName("게스트 사용자에 대해 guestNavBarHtml은 플레이스홀더를 게스트 사용자에 맞게 대체해야 한다")
    void guestNavBarHtmlSuccess() throws Exception {
        // When
        String resultHtml = HtmlBuilder.guestNavBarHtml("/index.html");

        // Then
        assertTrue(resultHtml.contains("로그인") && resultHtml.contains("회원가입") && !resultHtml.contains("로그아웃") && !resultHtml.contains("개인정보수정"));
    }

}
