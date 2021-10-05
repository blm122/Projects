public class PhoneNumber {

    private int register, areaCode, lastFour;

    public PhoneNumber(int reg, int area, int last) {
        register = reg;
        areaCode = area;
        lastFour = last;
    }

    public String toString() {
        return "(" + register + ") " + areaCode + "-" + lastFour;
    }

    public int hashCode() {
        return register * 10000000 + areaCode * 10000 + lastFour;
    }

    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof PhoneNumber)) return false;
        PhoneNumber pnOther = (PhoneNumber) other;
        boolean result = pnOther.register == register &&
                pnOther.areaCode == areaCode &&
                pnOther.lastFour == lastFour;
        if (result) assert other.hashCode() == hashCode();
        return result;
    }

}

