package szakdolgozat.transaction.amount.text.field.text;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionAmountTextFieldText {
    @AfterEach
    void tearDown() {
        TransactionAmountTextFieldText.getInstance().set("");
    }

    @Test
    @DisplayName("Test properties, getter and setter methods")
    void testGetterAndSetterMethods() {
        TransactionAmountTextFieldText instance1 = TransactionAmountTextFieldText.getInstance();
        TransactionAmountTextFieldText instance2 = TransactionAmountTextFieldText.getInstance();

        String testAmount = "101.10";
        instance1.set(testAmount);

        assertAll("Test getter and Property methods",
                () -> assertEquals(testAmount, instance1.get()),
                () -> assertEquals(testAmount, instance1.getTextProperty().getValue()),
                () -> assertEquals(testAmount, instance2.get()),
                () -> assertEquals(instance1, instance2)
        );
    }
}
