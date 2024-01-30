package user;

import db.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @AfterEach
    public void tearDown() {
        Database.clearUsers();
    }

    @Test
    @DisplayName("유효한 사용자 데이터가 주어지면 가입 시 true를 반환한다")
    public void signUpSuccess() {
        // Given
        Map<String, String> validUserData = new HashMap<>();
        validUserData.put("userId", "isExample");
        validUserData.put("password", "password123");
        validUserData.put("name", "isExample");
        validUserData.put("email", "isExample@naver.com");

        // When
        boolean result = userService.signUp(validUserData);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("유효한 사용자 데이터라도 userId가 중복되면 false를 반환한다")
    public void signUpFailureDuplicateUserId() {
        // Given
        Map<String, String> validUserData1 = new HashMap<>();
        validUserData1.put("userId", "isExample");
        validUserData1.put("password", "password123");
        validUserData1.put("name", "isExample");
        validUserData1.put("email", "isExample@naver.com");

        Map<String, String> validUserData2 = new HashMap<>();
        validUserData2.put("userId", "isExample");
        validUserData2.put("password", "testing13");
        validUserData2.put("name", "testUser");
        validUserData2.put("email", "testUser@test.com");

        // When
        userService.signUp(validUserData1);
        boolean result = userService.signUp(validUserData2);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("회원가입 폼의 파라미터값이 누락되면 false를 반환한다")
    public void signUpFailureMissingParams() {
        // Given: name과 email 누락
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("userId", "user");
        bodyParams.put("password", "password123");

        // When
        boolean result = userService.signUp(bodyParams);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("유효한 로그인 자격 증명 시 null이 아닌 sessionId를 반환한다")
    void loginSuccess() {
        // Given
        Map<String, String> userData = new HashMap<>();
        userData.put("userId", "testUser");
        userData.put("password", "testPassword");
        userData.put("name", "testUser");
        userData.put("email", "test@test.com");
        userService.signUp(userData);

        Map<String, String> loginData = new HashMap<>();
        loginData.put("userId", "testUser");
        loginData.put("password", "testPassword");

        // When
        String sessionId = userService.login(loginData);

        // Then
        assertNotNull(sessionId);
    }

    @Test
    @DisplayName("유효하지 않은 로그인 자격 증명이 주어지면 로그인 시 null을 반환한다")
    void testLoginFailure() {
        // Given
        Map<String, String> invalidLoginData = new HashMap<>();
        invalidLoginData.put("userId", "testUser");
        invalidLoginData.put("password", "testPassword");

        // When
        String sessionId = userService.login(invalidLoginData);

        // Then
        assertNull(sessionId);
    }
}
