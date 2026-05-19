package szakdolgozat.checker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestChecker {
    @Test
    @DisplayName("Test constructor")
    void testCheckerConstructor() {
        Checker checker = new Checker();
        assertNotNull(checker);
    }

    @Test
    @DisplayName("Test isResolution method")
    void testIsResolution() {
        Checker checker = new Checker();
        int currentBlockHash = 123456789;

        assertFalse(checker.isResolution("rossz_próbálkozás", currentBlockHash));

        String validGuess = null;

        for (int i = 0; i < 10000000; i++) {
            if (checker.isResolution(String.valueOf(i), currentBlockHash)) {
                validGuess = String.valueOf(i);
                break;
            }
        }

        assertNotNull(validGuess);

        assertTrue(checker.isResolution(validGuess, currentBlockHash));
    }
}
