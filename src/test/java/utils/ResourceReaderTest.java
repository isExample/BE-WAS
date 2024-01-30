package utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ResourceReaderTest {

    private static ResourceReader resourceReader;

    @BeforeAll
    static void setup() {
        resourceReader = ResourceReader.getInstance();
    }

    @Test
    @DisplayName("HTML 파일을 정상적으로 읽어와야 한다")
    void getFileTemplate_ShouldReturnHtmlFileContents() throws IOException {
        // Given
        String testHtmlPath = "/index.html";

        // When
        String content = resourceReader.getFileTemplate(testHtmlPath);

        // Then
        assertNotNull(content);
        assertTrue(content.contains("<html lang=\"kr\">") && content.contains("</html>"));
    }

    @Test
    @DisplayName("존재하지 않는 파일을 요청하면 null을 반환한다")
    void getFileTemplate_ShouldReturnNullForNonexistentFile() throws IOException {
        // Given
        String nonexistentFilePath = "/nonexistent.html";

        // When
        String content = resourceReader.getFileTemplate(nonexistentFilePath);

        // Then
        assertNull(content);
    }
}
