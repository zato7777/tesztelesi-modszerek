package szakdolgozat.transaction.key.text.area;

import javafx.application.Platform;
import javafx.beans.property.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.texts.Texts;
import szakdolgozat.transaction.key.text.area.text.TransactionPublicKeyTextAreaText;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionPublicKeyTextArea {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @AfterEach
    void tearDown() {
        TransactionPublicKeyTextAreaText.getInstance().getTextProperty().setValue("");
    }

    @Test
    @DisplayName("Test TextArea and bidirectional binding")
    void testTextAreaAndBidirectionalBinding() {
        TransactionPublicKeyTextArea textArea = new TransactionPublicKeyTextArea();
        Property<String> textProperty = TransactionPublicKeyTextAreaText.getInstance().getTextProperty();

        assertAll("Test TextArea values",
                () -> assertEquals(Texts.RECIEVER_PUBLIC_KEY_TEXT_AREA_PROMPT_TEXT, textArea.getPromptText()),
                () -> assertTrue(textArea.isWrapText()),
                () -> assertEquals(100, textArea.getMaxHeight())
        );

        textArea.setText("Test");
        assertEquals("Test", textProperty.getValue());

        textProperty.setValue("Test2");
        assertEquals("Test2", textArea.getText());
    }
}
