package szakdolgozat.key.selector.choice.box;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.key.selector.choice.box.values.KeySelectChoiceBoxOptions;

import static org.junit.jupiter.api.Assertions.*;

public class TestKeySelectorChoiceBox {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }
    @Test
    @DisplayName("Test KeySelectorChoiceBox initialization")
    void testKeySelectorChoiceBoxInitilization() {
        KeySelectorChoiceBox keySelectorChoiceBox = new KeySelectorChoiceBox();
        KeySelectChoiceBoxOptions options = KeySelectChoiceBoxOptions.getInstance();

        assertEquals(options.getKeySelectorChoiceBoxOptions(), keySelectorChoiceBox.getItems());
    }
}

