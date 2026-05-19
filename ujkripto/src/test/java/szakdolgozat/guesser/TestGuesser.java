package szakdolgozat.guesser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGuesser {
    @Test
    @DisplayName("Test Guesser, constructor and nextGuess method")
    void testGuesser() {
        Guesser guesser = new Guesser();
        for (int i = 0; i < 100; i++) {
            String guess = guesser.nextGuess();
            assertNotNull(guess);

            int length = guess.length();
            assertTrue(length >= 128 && length <= 255);

            for (int j = 0; j < length; j++) {
                char c = guess.charAt(j);
                assertTrue(c >= 48 && c <= 124);
            }
        }
    }
}
