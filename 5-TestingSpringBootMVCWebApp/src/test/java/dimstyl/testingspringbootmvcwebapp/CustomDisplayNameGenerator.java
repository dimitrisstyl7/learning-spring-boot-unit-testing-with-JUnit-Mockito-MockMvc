package dimstyl.testingspringbootmvcwebapp;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.DisplayNameGenerator.Simple;

class CustomDisplayNameGenerator {

    static class ReplaceCamelCase extends Simple {

        @Override
        public String generateDisplayNameForClass(Class<?> testClass) {
            return replaceUnderscore(replaceCamelCase(super.generateDisplayNameForClass(testClass)));
        }

        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return replaceUnderscore(replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass)));
        }

        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            return replaceUnderscore(replaceCamelCase(super.generateDisplayNameForMethod(testClass, testMethod)));
        }

        private String replaceUnderscore(String input) {
            return input.replace("_", " -");
        }

        private String replaceCamelCase(String camelCase) {
            StringBuilder result = new StringBuilder();
            result.append(camelCase.charAt(0));
            for (int i = 1; i < camelCase.length(); i++) {
                if (Character.isUpperCase(camelCase.charAt(i))) {
                    result.append(' ');
                    result.append(Character.toLowerCase(camelCase.charAt(i)));
                } else {
                    result.append(camelCase.charAt(i));
                }
            }
            return result.toString();
        }

    }

}
