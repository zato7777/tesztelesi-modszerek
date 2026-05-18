package szakdolgozat.transaction.amount.widget;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.centralized.label.CentralizedLabel;
import szakdolgozat.transaction.amount.text.field.TransactionAmountTextField;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionAmountWidget {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test TransactionAmountWidget on initialization")
    void testTransactionAmountWidgetInitilization() {
        TransactionAmountWidget widget = new TransactionAmountWidget();

        assertEquals(2, widget.getChildren().size());

        assertAll("Check child elements types",
                () -> assertInstanceOf(CentralizedLabel.class, widget.getChildren().getFirst()),
                () -> assertInstanceOf(TransactionAmountTextField.class, widget.getChildren().get(1))
        );

        for (int i = 0; i < widget.getChildren().size(); i++) {
            Node child = widget.getChildren().get(i);
            Insets margin = HBox.getMargin(child);

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
