package dimstyl.junitbasics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(CustomDisplayNamesTests.ReplaceCamelCase.class)
class AssertionsTests {

    private DemoUtils demoUtils;

    @BeforeEach
    void setUpBeforeEach() {
        demoUtils = new DemoUtils();
    }

    @Test
    void testEqualsAndNotEquals() {
        assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
        assertNotEquals(6, demoUtils.add(2, 9), "2+9 must not be 6");
    }

    @Test
    void testNullAndNotNull() {
        String str1 = null;
        String str2 = "string";
        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

    @Test
    void testSameAndNotSame() {
        String str = "test";
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Objects should be the same");
        assertNotSame(str, demoUtils.getAcademy(), "Objects should not be the same");
    }

    @Test
    void testTrueAndFalse() {
        assertTrue(demoUtils.isGreater(5, 2), "This should be true");
        assertFalse(demoUtils.isGreater(2, 6), "This should be false");
    }

    @Test
    void testArrayEquals() {
        String[] strings = {"A", "B", "C"};
        assertArrayEquals(strings, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be equal");
    }

    @Test
    void testIterableEquals() {
        List<String> strings = List.of("Day", "East", "Football");
        assertIterableEquals(strings, demoUtils.getAcademyInList(), "Lists should be equal");
    }

    @Test
    void testLineMatcher() {
        List<String> strings = List.of("D.*", "E.*", "F.*");
        assertLinesMatch(strings, demoUtils.getAcademyInList(), "Lists should match");
    }

    @Test
    void testThrowsAndDoesNotThrowException() {
        assertThrows(Exception.class, () -> demoUtils.throwException(-1), "Should throw an exception");
        assertDoesNotThrow(() -> demoUtils.throwException(2), "Should not throw an exception");
    }

    @Test
    void testTimeout() {
        assertTimeout(Duration.ofSeconds(3), () -> demoUtils.checkTimeout(), "Method should execute in 2 seconds");
    }

    @Test
    void testTimeoutPreemptively() {
        assertTimeout(Duration.ofSeconds(3), () -> demoUtils.checkTimeout(), "Method should execute in 2 seconds");
    }

}
