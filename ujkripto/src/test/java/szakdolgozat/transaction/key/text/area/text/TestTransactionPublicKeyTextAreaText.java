package szakdolgozat.transaction.key.text.area.text;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionPublicKeyTextAreaText {
    @AfterEach
    void tearDown() {
        TransactionPublicKeyTextAreaText.getInstance().set("");
    }

    @Test
    @DisplayName("Test properties, getter and setter methods")
    void testGetterAndSetterMethods() {
        TransactionPublicKeyTextAreaText instance1 = TransactionPublicKeyTextAreaText.getInstance();
        TransactionPublicKeyTextAreaText instance2 = TransactionPublicKeyTextAreaText.getInstance();

        String testPublicKey = "public_key";
        instance1.set(testPublicKey);

        assertAll("Test getter and Property methods",
                () -> assertEquals(testPublicKey, instance1.get()),
                () -> assertEquals(testPublicKey, instance1.getTextProperty().getValue()),
                () -> assertEquals(testPublicKey, instance2.get()),
                () -> assertEquals(instance1, instance2)
        );
    }
}
