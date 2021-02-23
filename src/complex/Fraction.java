// Fraction.java

package complex;

public class Fraction extends RealNumber {
    private int numerator, denominator;

    public Fraction(int numerator, int denominator) {
        if (denominator == 0)
            throw new ArithmeticException();
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(int wholeNumber) {
        numerator = wholeNumber;
        denominator = 1;
    }

    // Setter Methods
    public void setNumerator(int newNumerator) {
        numerator = newNumerator;
    }

    public void setDenominator(int newDenominator) {
        denominator = newDenominator;
    }

    // Getter Methods
    private int getGCF(int num, int den) {
        // Get Greatest Common Factor
        num = Math.abs(num);
        den = Math.abs(den);
        if (num == 0)
            return den;
        if (num == 1 || den == 1)
            return 1;

        int gcf;

        if (num > den) {
            gcf = den;
        } else {
            gcf = num;
        }

        boolean found = false;

        while (!found) {
            int numRemainder = num % gcf;
            int denRemainder = den % gcf;
            if (numRemainder == 0 && denRemainder == 0)
                found = true;
            else
                gcf -= 1;
        }
        return gcf;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public double getDouble() {
        return (double) numerator / (double) denominator;
    }

    public int getReducedNumerator() {
        // Returns reduced numerator
        return numerator / getGCF(numerator, denominator);
    }

    public int getReducedDenominator() {
        // Returns reduced denominator
        return denominator / getGCF(numerator, denominator);
    }

    public String print(boolean asFraction) {
        // Returns fraction as String
        if (asFraction) {
            if (this.getReducedDenominator() == 1)
                return this.getReducedNumerator() + "";
            return this.getReducedNumerator() + "/" + this.getReducedDenominator();
        } else {
            return Double.toString(this.getDouble());
        }
    }

    private Fraction typeCheck(Number n) throws IllegalArgumentException {
        if (!(n instanceof Fraction))
            throw new IllegalArgumentException();
        return (Fraction) n;
    }

    // Operations
    @Override
    public RealNumber add(Number n) {
        if (n instanceof Decimal)
            return (Decimal) n.add(this);
        Fraction fraction = typeCheck(n);
        int resultNum = this.getNumerator() * fraction.getDenominator()
                + fraction.getNumerator() * this.getDenominator();
        int resultDen = this.getDenominator() * fraction.getDenominator();
        return new Fraction(resultNum, resultDen);
    }

    @Override
    public RealNumber subtract(Number n) {
        if (n instanceof Decimal)
            return (Decimal) n.subtract(this);
        Fraction fraction = typeCheck(n);
        return add(new Fraction(fraction.getNumerator() * -1, fraction.getDenominator()));
    }

    @Override
    public RealNumber multiply(Number n) {
        if (n instanceof Decimal)
            return (Decimal) n.multiply(this);
        Fraction fraction = typeCheck(n);
        int resultNum = this.getNumerator() * fraction.getNumerator();
        int resultDen = this.getDenominator() * fraction.getDenominator();
        return new Fraction(resultNum, resultDen);
    }

    @Override
    public RealNumber divide(Number n) {
        if (n instanceof Decimal)
            return (Decimal) n.divide(this);
        Fraction fraction = typeCheck(n);
        int resultNum = this.getNumerator() * fraction.getDenominator();
        int resultDen = this.getDenominator() * fraction.getNumerator();
        return new Fraction(resultNum, resultDen);
    }
}
