package szakdolgozat.key.text.area;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.key.text.area.text.PublicKeyTextAreaText;

import static org.junit.jupiter.api.Assertions.*;

public class TestPublicKeyTextArea {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test PublicKeyTextArea initialization")
    void testPublicKeyTextAreaInitialization() {
        PublicKeyTextArea textArea = new PublicKeyTextArea();

        assertAll("Check Properties",
                () -> assertTrue(textArea.isWrapText()),
                () -> assertFalse(textArea.isEditable()),
                () -> assertEquals(100, textArea.getMaxHeight())
        );

        PublicKeyTextArea textArea2 = new PublicKeyTextArea();
        PublicKeyTextAreaText instance = PublicKeyTextAreaText.getInstance();
        instance.getTextProperty().setValue("Test");
        assertEquals("Test", textArea2.getText());

        textArea2.setText("Test2");
        assertEquals("Test2", instance.getTextProperty().getValue());
    }
}
