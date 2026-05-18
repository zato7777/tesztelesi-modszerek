package szakdolgozat.transaction.amount.text.field;

import javafx.application.Platform;
import javafx.beans.property.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import szakdolgozat.transaction.amount.text.field.text.TransactionAmountTextFieldText;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransactionAmountTextField {
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @AfterEach
    void tearDown() {
        TransactionAmountTextFieldText.getInstance().getTextProperty().setValue("");
    }

    @Test
    @DisplayName("Test TextProperty and bindBidirectional with text parameter")
    void testTextProperty() {
        TransactionAmountTextField textField = new TransactionAmountTextField();
        Property<String> textProperty = TransactionAmountTextFieldText.getInstance().getTextProperty();

        textField.setText("12.12");
        assertEquals("12.12", textProperty.getValue());

        textProperty.setValue("12.12");
        assertEquals("12.12", textField.getText());
    }
}
