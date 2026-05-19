package szakdolgozat.property.loader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class TestPropertyLoader {
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Test PropertyLoader constructor")
    void propertyLoaderConstructor() {
        PropertyLoader propertyLoader = new PropertyLoader();
        assertNotNull(propertyLoader);
    }

    @Test
    @DisplayName("Test PropertyLoader load and store methods")
    void testLoadAndStore() {
        File tempfile = tempDir.resolve("temp.properties").toFile();
        String path = tempfile.getAbsolutePath();

        Properties properties = new Properties();
        properties.setProperty("port", "8080");
        properties.setProperty("keysJson", "keys.json");
        properties.setProperty("blockChainJson", "blockChain.json");

        PropertyLoader.store(path, properties);
        Properties loadedProperties = PropertyLoader.load(path);

        assertAll("Check loaded properties",
                () -> assertEquals(3, loadedProperties.size()),
                () -> assertEquals("8080", loadedProperties.getProperty("port")),
                () -> assertEquals("keys.json", loadedProperties.getProperty("keysJson")),
                () -> assertEquals("blockChain.json", loadedProperties.getProperty("blockChainJson"))
        );
    }

    @Test
    @DisplayName("Test load and store catch blocks")
    void testLoadAndStoreCatchBlocks() {
        String loadPath = tempDir.resolve("non_existent_folder.properties").toFile().getAbsolutePath();
        Properties properties = PropertyLoader.load(loadPath);

        assertTrue(properties.isEmpty());

        String storePath = tempDir.toFile().getAbsolutePath();
        Properties properties2 = new Properties();
        properties2.setProperty("port", "8080");
        properties2.setProperty("keysJson", "keys.json");
        properties2.setProperty("blockChainJson", "blockChain.json");

        assertDoesNotThrow(() -> PropertyLoader.store(storePath, properties2));
    }

}
