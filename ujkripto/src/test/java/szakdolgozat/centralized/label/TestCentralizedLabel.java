package szakdolgozat.centralized.label;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCentralizedLabel {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @Test
    @DisplayName("Test CentralizedLabel")
    void testCentralizedLabel() {
        String testLabel = "Test";
        CentralizedLabel label = new CentralizedLabel(testLabel);
        assertEquals(testLabel, label.textProperty().getValue());
        assertEquals(1, label.getChildren().size());
        assertInstanceOf(Label.class, label.getChildren().get(0));
        assertEquals(testLabel, ((Label) label.getChildren().get(0)).getText());

        StringProperty property = label.textProperty();
        property.setValue("Test2");
        assertEquals("Test2", label.textProperty().getValue());
    }
}
