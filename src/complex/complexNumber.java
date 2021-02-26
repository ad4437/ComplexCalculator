// complexNumber.java

package complex;

public class complexNumber implements Number {
    public RealNumber a, b;

    public complexNumber(RealNumber a, RealNumber b) {
        this.a = a;
        this.b = b;
    }

    public complexNumber(Number a, Number b) throws IllegalArgumentException {
        if (!(a instanceof RealNumber && b instanceof RealNumber))
            throw new IllegalArgumentException();
        this.a = (RealNumber) a;
        this.b = (RealNumber) b;
    }

    public String print(boolean asFractions) {
        String str = "";
        if (a.getDouble() != 0) {
            str += a.print(asFractions);
            if (b.getDouble() != 0) {
                if (b.getDouble() == -1.0)
                    str += "-i";
                else if (b.getDouble() == 1.0)
                    str += "+i";
                else {
                    if (b.getDouble() > 0)
                        str += "+";
                    str += b.print(asFractions) + "i";
                }
            }
        } else if (b.getDouble() != 0) {
            if (b.getDouble() == 1.0)
                str += "i";
            else if (b.getDouble() == -1.0)
                str += "-i";
            else
                str += b.print(asFractions) + "i";
        } else
            str = "0";

        return str;
    }

    private complexNumber typeCheck(Number n) throws IllegalArgumentException {
        if (!(n instanceof complexNumber))
            throw new IllegalArgumentException();
        return (complexNumber) n;
    }

    @Override
    public complexNumber add(Number n) {
        complexNumber num = typeCheck(n);
        return new complexNumber(this.a.add(num.a), this.b.add(num.b));
    }

    @Override
    public complexNumber subtract(Number n) {
        complexNumber num = typeCheck(n);
        num.a = num.a.multiply(new Fraction(-1, 1));
        num.b = num.b.multiply(new Fraction(-1, 1));
        return add(num);
    }

    @Override
    public complexNumber multiply(Number n) {
        complexNumber num = typeCheck(n);
        RealNumber realProduct = this.a.multiply(num.a).add(this.b.multiply(num.b).multiply(new Fraction(-1, 1)));
        RealNumber imagProduct = this.a.multiply(num.b).add(this.b.multiply(num.a));
        return new complexNumber(realProduct, imagProduct);
    }

    @Override
    public complexNumber divide(Number n) {
        complexNumber num = typeCheck(n);

        // RealQuotient = (a*c + b*d) / (c*c + d*d)
        // imagQuotient =((b*c-a*d) / (c*c + d*d)) i
        RealNumber realQuotient = this.a.multiply(num.a).add(this.b.multiply(num.b));
        realQuotient = realQuotient.divide(num.a.multiply(num.a).add(num.b.multiply(num.b)));
        RealNumber imagQuotient = this.b.multiply(num.a).subtract(this.a.multiply(num.b));
        imagQuotient = imagQuotient.divide(num.a.multiply(num.a).add(num.b.multiply(num.b)));
        return new complexNumber(realQuotient, imagQuotient);
    }
}
