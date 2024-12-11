package dimstyl.tddfizzbuzz;

import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {
        IntStream.range(1, 101).forEach(i -> System.out.println(FizzBuzz.compute(i)));
    }

}
