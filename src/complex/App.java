// App.java

package complex;

import java.util.Scanner;

public class App {
    static boolean asFractions = true;

    public static RealNumber parseRealNum(String f) {
        try {
            return new Fraction(Integer.parseInt(f));
        } catch (NumberFormatException e) {
            try {
                double d = Double.parseDouble(f);
                asFractions = false;
                return new Decimal(d);
            } catch (NumberFormatException e2) {
                int numerator = Integer.parseInt(f.substring(0, f.indexOf("/")));
                int denominator = Integer.parseInt(f.substring(f.indexOf("/") + 1));
                return new Fraction(numerator, denominator);
            }
        }
    }

    public static complexNumber parseComplex(String num) {
        String a, b;
        if (num.contains("i")) {
            if (num.contains("+") || num.contains("-")) {
                int signIndex = (num.indexOf("+") > num.indexOf("-")) ? num.indexOf("+") : num.indexOf("-");
                a = num.substring(0, signIndex);
                b = num.substring(signIndex, num.length() - 1);
                if (a.equals(""))
                    a = "0";
                if (b.equals("+"))
                    b = "1";
                if (b.equals("-"))
                    b = "-1";

            } else {
                a = "0";
                b = num.substring(0, num.length() - 1);
                if (b.equals(""))
                    b = "1";
                if (b.equals("-"))
                    b = "-1";
            }
        } else {
            a = num;
            b = "0";
        }

        return new complexNumber(parseRealNum(a), parseRealNum(b));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String repeat = "y";
        System.out.println("Possible Input Strings:\n");
        System.out.println("Whole Numbers / Decimals: (a+bi) + (c+di)");
        System.out.println("Fractions: (a/b-c/di) + (e/f-g/hi)\n");
        System.out.println("Valid operations between real & imaginary components are + or -");
        System.out.println("Valid operations between both numbers are +, -, *, or /");
        System.out.println("If no imaginary or real part for a certain number, do not include it");
        System.out.println("i.e. a+bi (with no imaginary) is just a\n");

        while (repeat.equals("y")) {
            System.out.print("\nEnter Expression: ");
            String exp = in.nextLine();

            // Parse String
            int space1 = exp.indexOf(" ");
            int space2 = space1 + 2; // based on format ... + ...
            String operation;
            complexNumber num1, num2;

            try {
                operation = exp.substring(space1 + 1, space2);
                String num1Exp = exp.substring(1, space1 - 1);
                String num2Exp = exp.substring(space2 + 2, exp.length() - 1);
                num1 = parseComplex(num1Exp);
                num2 = parseComplex(num2Exp);
                if (operation.equals("/") && num2.a.getDouble() == 0) {
                    throw new ArithmeticException();
                }
            } catch (ArithmeticException e) {
                System.out.println("Cannot Divide by Zero");
                System.out.print("Repeat? (y/n): ");
                repeat = (in.next()).toLowerCase();
                in.nextLine();
                continue;
            } catch (Exception e) {
                System.out.println("Invalid Format");
                System.out.print("Repeat? (y/n): ");
                repeat = (in.next()).toLowerCase();
                in.nextLine();
                continue;
            }

            switch (operation) {
                case "+":
                    System.out.println(num1.add(num2).print(asFractions));
                    break;
                case "-":
                    System.out.println(num1.subtract(num2).print(asFractions));
                    break;
                case "*":
                    System.out.println(num1.multiply(num2).print(asFractions));
                    break;
                case "/":
                    System.out.println(num1.divide(num2).print(asFractions));
                    break;
                default:
                    System.out.println("Invalid Operation");
                    System.out.print("Repeat? (y/n): ");
                    repeat = (in.next()).toLowerCase();
                    in.nextLine();
                    continue;
            }

            System.out.print("Repeat? (y/n): ");
            repeat = (in.nextLine()).toLowerCase();
            asFractions = true;
        }
        in.close();
    }
}
