package szakdolgozat.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestConfig {
    @Test
    @DisplayName("Test Config, Properties constructors")
    void testConfigAndPropertiesConstructor() {
        Config config = new Config();
        Config.Properties properties = config.new Properties();

        assertNotNull(config);
        assertNotNull(properties);

        assertAll("Check constants",
                () -> assertEquals("target/classes/config.properties", Config.PATH),
                () -> assertEquals("keysJson", Config.Properties.KEYS),
                () -> assertEquals("block_chainJson", Config.Properties.BLOCK_CHAIN),
                () -> assertEquals("ipsJson", Config.Properties.IPS),
                () -> assertEquals("port", Config.Properties.PORT)
        );
    }

}
