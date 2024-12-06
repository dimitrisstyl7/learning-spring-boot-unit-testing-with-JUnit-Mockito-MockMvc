package dimstyl.junitbasics;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LifeCycleMethodsTests {

    private DemoUtils demoUtils;

    @BeforeAll
    static void setUpBeforeAll() {
        System.out.println("Running @BeforeAll\n");
    }

    @AfterAll
    static void tearDownAfterAll() {
        System.out.println("Running @AfterAll\n");
    }

    @BeforeEach
    void setUpBeforeEach() {
        System.out.println("Running @BeforeEach");
        demoUtils = new DemoUtils();
    }

    @AfterEach
    void tearDownAfterEach() {
        System.out.println("Running @AfterEach\n");
    }

    @Test
    void testEqualsAndNotEquals() {
        System.out.println("Running testEqualsAndNotEquals");
        assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
        assertNotEquals(6, demoUtils.add(2, 9), "2+9 must not be 6");
    }

    @Test
    void testNullAndNotNull() {
        System.out.println("Running testNullAndNotNull");
        String str1 = null;
        String str2 = "string";

        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

}
