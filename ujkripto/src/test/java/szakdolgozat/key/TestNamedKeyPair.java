package szakdolgozat.key;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNamedKeyPair {
    @Test
    @DisplayName("Test contructor and getter, setter methods")
    void testGetterSetter() {
        NamedKeyPair keyPair = new NamedKeyPair();

        keyPair.setName("test");
        keyPair.setPrivateKey("private");
        keyPair.setPublicKey("public");

        assertAll("Test Getter, Setter methods results",
                () -> assertEquals("test", keyPair.getName()),
                () -> assertEquals("private", keyPair.getPrivateKey()),
                () -> assertEquals("public", keyPair.getPublicKey())
        );
    }

    @Test
    @DisplayName("Test toString method")
    void testToString() {
        NamedKeyPair keyPair = new NamedKeyPair("Zalan", "private_Zalan", "public_Zalan");
        String expected = "NamedKeyPair{name='Zalan', privateKey='private_Zalan', publicKey='public_Zalan'}";
        assertEquals(expected, keyPair.toString());
    }

    @Test
    @DisplayName("Test equals and hashCode methods")
    void testEqualsAndHashCode() {
        NamedKeyPair keyPair = new NamedKeyPair("Zalan", "private_Zalan", "public_Zalan");
        NamedKeyPair keyPair2 = new NamedKeyPair("Zalan", "private_Zalan", "public_Zalan");
        NamedKeyPair keyPair3 = new NamedKeyPair("Laci", "private_Zalan", "public_Zalan");
        NamedKeyPair keyPair4 = new NamedKeyPair("Zalan", "private_Laci", "public_Zalan");
        NamedKeyPair keyPair5 = new NamedKeyPair("Zalan", "private_Zalan", "public_Laci");

        assertAll("Test equals",
                () -> assertTrue(keyPair.equals(keyPair)),
                () -> assertTrue(keyPair.equals(keyPair2)),
                () -> assertFalse(keyPair.equals(null)),
                () -> assertFalse(keyPair.equals(new Object())),
                () -> assertFalse(keyPair.equals(keyPair3)),
                () -> assertFalse(keyPair.equals(keyPair4)),
                () -> assertFalse(keyPair.equals(keyPair5))
        );

        assertEquals(keyPair.hashCode(), keyPair2.hashCode());
    }
}
