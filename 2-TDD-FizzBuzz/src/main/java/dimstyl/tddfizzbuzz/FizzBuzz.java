package dimstyl.tddfizzbuzz;

public class FizzBuzz {

    public static String compute(final int number) {
        // If number is divisible by 3 and 5, return FizzBuzz
        if (number % 3 == 0 && number % 5 == 0) return "FizzBuzz";

        // If number is divisible by 3, return Fizz
        if (number % 3 == 0) return "Fizz";

        // If number is divisible by 5, return Buzz
        if (number % 5 == 0) return "Buzz";

        // If number is not divisible by 3 or 5, return number
        return String.valueOf(number);
    }

}
