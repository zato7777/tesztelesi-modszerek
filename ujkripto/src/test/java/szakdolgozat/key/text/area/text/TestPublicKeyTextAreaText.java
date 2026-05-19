package szakdolgozat.key.text.area.text;

import javafx.beans.property.Property;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPublicKeyTextAreaText {
    @Test
    @DisplayName("Test getters, setters")
    void testGettersSetters() {
        PublicKeyTextAreaText instance = PublicKeyTextAreaText.getInstance();
        PublicKeyTextAreaText instance2 = PublicKeyTextAreaText.getInstance();

        assertNotNull(instance);
        assertSame(instance, instance2);

        String publicKey = "public";
        instance.set(publicKey);
        Property<String> textProperty = instance.getTextProperty();

        assertNotNull(textProperty);
        assertEquals(publicKey, textProperty.getValue());
    }
}
