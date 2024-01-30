package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostServiceTest {
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostService();
    }

    @Test
    @DisplayName("유효한 게시물 파라미터로 글 작성을 시도하면 true를 반환한다")
    void writePostSuccess(){
        // Given
        Map<String, String> validParams = new HashMap<>();
        validParams.put("writer", "testUser");
        validParams.put("title", "testTitle");
        validParams.put("contents", "testContents");

        // When
        boolean result = postService.writePost(validParams);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("글 작성 파라미터값이 누락되면 false를 반환한다")
    void writePostFailureMissingParams() {
        // Given: writer 누락
        Map<String, String> invalidParams = new HashMap<>();
        invalidParams.put("writer", "");
        invalidParams.put("title", "Test Title");
        invalidParams.put("contents", "Test Contents");

        // When
        boolean result = postService.writePost(invalidParams);

        // Then
        assertFalse(result);
    }

}
