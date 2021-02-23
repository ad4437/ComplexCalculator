package complex;

public class Decimal extends RealNumber {
    public double decimal;

    public Decimal(double decimal) {
        this.decimal = decimal;
    }

    public String print(boolean asFraction) {
        // asFraction is irrelevant in this program because if the user enters any
        // decimal, then the result is outputted as decimals
        if (decimal % 1 == 0)
            return Integer.toString((int) this.decimal);
        return Double.toString(this.decimal);
    }

    private Decimal typeCheck(Number n) throws IllegalArgumentException {
        if (n instanceof Decimal)
            return (Decimal) n;
        else if (n instanceof Fraction)
            return new Decimal(((Fraction) n).getDouble());
        else
            throw new IllegalArgumentException();
    }

    @Override
    public double getDouble() {
        return decimal;
    }

    @Override
    public RealNumber add(Number num) {
        Decimal d = typeCheck(num);
        return new Decimal(this.decimal + d.decimal);
    }

    @Override
    public RealNumber subtract(Number num) {
        Decimal d = typeCheck(num);
        return new Decimal(this.decimal - d.decimal);
    }

    @Override
    public RealNumber multiply(Number num) {
        Decimal d = typeCheck(num);
        return new Decimal(this.decimal * d.decimal);
    }

    @Override
    public RealNumber divide(Number num) {
        Decimal d = typeCheck(num);
        return new Decimal(this.decimal / d.decimal);
    }

}
