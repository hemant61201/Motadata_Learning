import java.math.BigDecimal;
public class FloatPitfalls {
    public static void main(String[] args) {
        System.out.println(1 - 0.9);
        System.out.println(0.1 + 0.2);
        // 0.1 ~ 0.00011001100110011001100110011001100110011001100110011001100110011

        double price = 1000;
        double discountPercent = 0.9;
        double discountAmount = price * discountPercent;
        System.out.println(price - discountAmount);
        System.out.println(price * (1 - discountPercent));

        BigDecimal first = new BigDecimal("0.1");
        BigDecimal second = new BigDecimal("0.2");
        System.out.println(first.add(second));
    }
}
