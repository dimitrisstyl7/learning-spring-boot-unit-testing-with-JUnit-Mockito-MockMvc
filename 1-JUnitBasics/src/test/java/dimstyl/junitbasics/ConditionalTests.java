package dimstyl.junitbasics;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

@DisplayNameGeneration(CustomDisplayNamesTests.ReplaceCamelCase.class)
public class ConditionalTests {

    @Test
    @Disabled("Do not run until bug is fixed")
    void testDisabled() {
        // Some code
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindows() {
        // Some code
    }

    @Test
    @EnabledOnOs({OS.WINDOWS, OS.LINUX})
    void testForWindowsAndLinux() {
        // Some code
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testForJava17() {
        // Some code
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_17, max = JRE.JAVA_21)
    void testForJavaRange() {
        // Some code
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_17)
    void testForMinJava17() {
        // Some code
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_17)
    void testForMaxJava17() {
        // Some code
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "TYPE_ENV", matches = "DEV")
    void testOnlyForDevEnvironment() {
        // Some code
    }

    @Test
    @EnabledIfSystemProperty(named = "SYS_PROP", matches = "CI_CD_DEPLOY")
    void testOnlyForSystemProperty() {
        // Some code
    }

}
