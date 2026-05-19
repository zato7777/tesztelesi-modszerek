package szakdolgozat.key.widget;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.centralized.label.CentralizedLabel;
import szakdolgozat.key.text.area.PublicKeyTextArea;

import static org.junit.jupiter.api.Assertions.*;

public class TestPublicKeyWidget {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test PublicKeyWidget initialization")
    void testPublicKeyWidgetInitilization() {
        PublicKeyWidget widget = new PublicKeyWidget();
        assertEquals(2, widget.getChildren().size());

        Node firstChild = widget.getChildren().get(0);
        Node secondChild = widget.getChildren().get(1);

        assertInstanceOf(CentralizedLabel.class, firstChild);
        assertInstanceOf(PublicKeyTextArea.class, secondChild);

        Insets firstChildMargin = PublicKeyWidget.getMargin(firstChild);
        Insets secondChildMargin = PublicKeyWidget.getMargin(secondChild);
        assertNotNull(firstChildMargin);
        assertNotNull(secondChildMargin);

        assertAll("Test margins for elements",
                () -> assertEquals(5, firstChildMargin.getTop()),
                () -> assertEquals(5, firstChildMargin.getRight()),
                () -> assertEquals(0, firstChildMargin.getBottom()),
                () -> assertEquals(5, firstChildMargin.getLeft()),
                () -> assertEquals(0, secondChildMargin.getTop()),
                () -> assertEquals(5, secondChildMargin.getRight()),
                () -> assertEquals(5, secondChildMargin.getBottom()),
                () -> assertEquals(5, secondChildMargin.getLeft())
        );
    }
}
