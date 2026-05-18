package szakdolgozat.transaction.submit.widget;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.send.button.SendButton;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionSubmitWidget {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test TransactionSubmitWidget on initialization")
    void testTransactionSubmitWidgetInitilization() {
        TransactionSubmitWidget widget = new TransactionSubmitWidget();

        assertEquals(1, widget.getChildren().size());

        Node child = widget.getChildren().getFirst();
        assertInstanceOf(SendButton.class, child);

        Insets margin = HBox.getMargin(child);
        assertNotNull(margin);

        assertAll("Check margin values",
                () -> assertEquals(5, margin.getTop()),
                () -> assertEquals(5, margin.getBottom()),
                () -> assertEquals(5, margin.getLeft()),
                () -> assertEquals(5, margin.getRight())
        );
    }
}
