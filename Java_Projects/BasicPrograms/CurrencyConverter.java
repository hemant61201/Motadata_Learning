public class CurrencyConverter {

    int rupee = 63;
    int dirham = 3; // UAE
    int real = 3;  // brazilian
    int chilean_peso = 595;
    int mexican_peso = 18;
    int _yen = 107;
    int $australian = 2;  // australian dollar
    int dollar;
    int Rupee = 63;

    double[] exchangeRates = {63.0,3.0,3.0,595.0,18.0,107.0,2.0,63.0};

    void printCurrencies() {

        System.out.println("rupee: " + rupee);
        System.out.println("$australian: " + $australian);
        System.out.println("real: " + real);
        System.out.println("chilean_peso: " + chilean_peso);
        System.out.println("mexican_peso: " + mexican_peso);
        System.out.println("_yen: " + _yen);
        System.out.println("$australian: " + $australian);
        System.out.println("dollar: " + dollar);
        System.out.println("Rupee: " + Rupee);
        System.out.println("rupee: " + exchangeRates[0]);
        System.out.println("dirham: " + exchangeRates[1]);
        System.out.println("real: " + exchangeRates[2]);
        System.out.println("chilean_peso: " + exchangeRates[3]);
        System.out.println("mexican_peso: " + exchangeRates[4]);
        System.out.println("_yen: " + exchangeRates[5]);
        System.out.println("last element: " + exchangeRates[exchangeRates.length - 1]);

    }
    public static void main(String[] args) {
        CurrencyConverter cc = new CurrencyConverter();
        cc.printCurrencies();
    }
}