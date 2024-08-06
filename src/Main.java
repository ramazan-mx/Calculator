import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws Exception {
        String[] expression = input.split("\\s+");
        checkCorrectExpression(expression);

        if (isArabicExpression(expression)) {
            return String.valueOf(expressionValue(expression));
        } else {
            int result = expressionValue(expression);
            if (result < 1) {
                throw new Exception();
            } else return intToRoman(result);
        }
    }

    public static String intToRoman(int number) {
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                roman.append(symbols[i]);
            }
        }

        return roman.toString();
    }

    static int expressionValue(String[] expression) {
        int firstOperands = getNumber(expression[0]);
        int secondOperands = getNumber(expression[2]);
        String sign = expression[1];

        return switch (sign) {
            case "+" -> firstOperands + secondOperands;
            case "-" -> firstOperands - secondOperands;
            case "*" -> firstOperands * secondOperands;
            case "/" -> firstOperands / secondOperands;
            default -> 0;
        };
    }

    static void checkCorrectExpression(String[] expression) throws Exception {
        if (expression.length != 3) {
            throw new Exception("Некорректное выражение!");
        } else {
            if (isIncorrectNumber(expression[0]) || isIncorrectNumber(expression[2])) {
                throw new Exception("Некорректное выражение!");
            }
            if (isExpressionWithDifferentTypesOperands(expression[0], expression[2])) {
                throw new Exception("Числа разных тмпов!");
            }
            if (!isCorrectArithmeticSign(expression[1])) {
                throw new Exception("Некорректный знак!");
            }
        }
    }

    static boolean isIncorrectNumber(String operands) {
        return !isRomanNumber(operands) && !isArabicNumber(operands);
    }

    static boolean isExpressionWithDifferentTypesOperands(String firstOperands, String secondOperands) {
        return isRomanNumber(firstOperands) && isArabicNumber(secondOperands) ||
                isRomanNumber(secondOperands) && isArabicNumber(firstOperands);
    }

    static boolean isCorrectArithmeticSign(String sign) {
        return ArithmeticSigns.signs.contains(sign);
    }

    static boolean isArabicNumber(String number) {
        return ArabicNumerals.numerals.containsKey(number);
    }

    static boolean isRomanNumber(String number) {
        return RomanNumerals.numerals.containsKey(number);
    }

    static boolean isArabicExpression(String[] expression) {
        return isArabicNumber(expression[0]) && isArabicNumber(expression[2]);
    }

    static boolean isRomanExpression(String[] expression) {
        return isRomanNumber(expression[0]) && isRomanNumber(expression[2]);
    }

    static int getNumber(String number) {
        if (isArabicNumber(number)) {
            return ArabicNumerals.numerals.get(number);
        } else return RomanNumerals.numerals.get(number);
    }
}