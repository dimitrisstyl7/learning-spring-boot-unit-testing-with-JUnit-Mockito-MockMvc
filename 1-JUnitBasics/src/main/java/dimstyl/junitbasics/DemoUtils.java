package dimstyl.junitbasics;

import java.util.List;

public class DemoUtils {

    private final String academy = "Academy";
    private final String academyDuplicate = academy;
    private final String[] firstThreeLettersOfAlphabet = {"A", "B", "C"};
    private final List<String> academyInList = List.of("Day", "East", "Football");

    public List<String> getAcademyInList() {
        return academyInList;
    }

    public String getAcademy() {
        return academy;
    }

    public String getAcademyDuplicate() {
        return academyDuplicate;
    }

    public String[] getFirstThreeLettersOfAlphabet() {
        return firstThreeLettersOfAlphabet;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public Object checkNull(Object obj) {
        return obj;
    }

    public Boolean isGreater(int n1, int n2) {
        return n1 > n2;
    }

    public String throwException(int a) throws Exception {
        if (a < 0) {
            throw new Exception("Value should be greater than or equal to 0");
        }
        return "Value is greater than or equal to 0";
    }

    public void checkTimeout() throws InterruptedException {
        System.out.println("I am going to sleep");
        Thread.sleep(2000);
        System.out.println("Sleeping over");
    }

}
