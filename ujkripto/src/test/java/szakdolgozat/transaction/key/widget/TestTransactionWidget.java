package szakdolgozat.transaction.key.widget;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.centralized.label.CentralizedLabel;
import szakdolgozat.transaction.amount.widget.TransactionAmountWidget;
import szakdolgozat.transaction.key.text.area.TransactionPublicKeyTextArea;
import szakdolgozat.transaction.submit.widget.TransactionSubmitWidget;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionWidget {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test TransactionWidget on initialization")
    void testTransactionWidgetInitilization() {
        TransactionWidget widget = new TransactionWidget();
        assertEquals(4, widget.getChildren().size());

        assertAll("Test child elements",
                () -> assertInstanceOf(CentralizedLabel.class, widget.getChildren().get(0)),
                () -> assertInstanceOf(TransactionPublicKeyTextArea.class, widget.getChildren().get(1)),
                () -> assertInstanceOf(TransactionAmountWidget.class, widget.getChildren().get(2)),
                () -> assertInstanceOf(TransactionSubmitWidget.class, widget.getChildren().get(3))
        );

        for (int i = 0; i < widget.getChildren().size(); i++) {
            Node child = widget.getChildren().get(i);
            Insets margin = VBox.getMargin(child);
            assertNotNull(margin);

            assertAll("Margin value on" + i + " index element",
                    () -> assertEquals(5, margin.getTop()),
                    () -> assertEquals(5, margin.getRight()),
                    () -> assertEquals(5, margin.getBottom()),
                    () -> assertEquals(5, margin.getLeft())
            );
        }
    }
}
