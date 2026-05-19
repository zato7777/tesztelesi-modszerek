package szakdolgozat.key.selector.widget;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.centralized.label.CentralizedLabel;
import szakdolgozat.key.selector.choice.box.KeySelectorChoiceBox;

import static org.junit.jupiter.api.Assertions.*;

public class TestKeySelectorWidget {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test KeySelectorWidget initialization")
    void testKeySelectorWidgetInitilization() {
        KeySelectorWidget widget = new KeySelectorWidget();
        assertEquals(2, widget.getChildren().size());

        Node firstChild = widget.getChildren().get(0);
        Node secondChild = widget.getChildren().get(1);

        assertInstanceOf(CentralizedLabel.class, firstChild);
        assertInstanceOf(KeySelectorChoiceBox.class, secondChild);

        Insets firstChildMargin = KeySelectorWidget.getMargin(firstChild);
        Insets secondChildMargin = KeySelectorWidget.getMargin(secondChild);
        assertNotNull(firstChildMargin);
        assertNotNull(secondChildMargin);

        assertAll("Test margins for elements",
                () -> assertEquals(5, firstChildMargin.getTop()),
                () -> assertEquals(5, firstChildMargin.getRight()),
                () -> assertEquals(5, firstChildMargin.getBottom()),
                () -> assertEquals(5, firstChildMargin.getLeft()),
                () -> assertEquals(5, secondChildMargin.getTop()),
                () -> assertEquals(5, secondChildMargin.getRight()),
                () -> assertEquals(5, secondChildMargin.getBottom()),
                () -> assertEquals(5, secondChildMargin.getLeft())
        );
    }
}
