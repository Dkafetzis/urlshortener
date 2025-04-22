package xyz.palamari.services;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MiscService}
 */
@QuarkusTest
@TestProfile(MiscServiceTest.TestProfile.class)
public class MiscServiceTest {

    /**
     * Test profile that sets a specific application version for testing
     */
    public static class TestProfile implements QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            Map<String, String> config = new HashMap<>();
            config.put("quarkus.application.version", "1.2.3-test");
            return config;
        }
    }

    /**
     * Test that {@link MiscService#getVersion()} returns the correct application version
     */
    @Test
    public void testGetVersion() {
        // When
        String version = MiscService.getVersion();

        // Then
        Assertions.assertEquals("1.2.3-test", version);
    }

    /**
     * Test that {@link MiscService#getVersion()} returns the same value as directly accessing ConfigProvider
     */
    @Test
    public void testGetVersionMatchesConfigProvider() {
        // When
        String versionFromService = MiscService.getVersion();
        String versionFromConfig = ConfigProvider.getConfig().getValue("quarkus.application.version", String.class);

        // Then
        Assertions.assertEquals(versionFromConfig, versionFromService);
    }
}
