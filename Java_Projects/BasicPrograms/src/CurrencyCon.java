public class CurrencyCon {
    static double[] exchangeRates = new double[] {63.0, 3.0, 3.0, 595.5, 18.0, 107.0, 2.0};

    void setExchangeRates(double[] rates) {
        exchangeRates = rates;
    }

    void updateExchangeRate(int arrayIndex, double newVal) {
        exchangeRates[arrayIndex] = newVal;
    }

    double getExchangeRate(int arrayIndex) {
        return exchangeRates[arrayIndex];
    }

    double computeTransferAmount(int arrayIndex, double amount) {
        return amount * getExchangeRate(arrayIndex);
    }

    static void printCurrencies() {
        System.out.println("\nrupee: " + exchangeRates[0]);
        System.out.println("dirham: " + exchangeRates[1]);
        System.out.println("real: " + exchangeRates[2]);
        System.out.println("chilean_peso: " + exchangeRates[3]);
        System.out.println("mexican_peso: " + exchangeRates[4]);
        System.out.println("_yen: " + exchangeRates[5]);
        System.out.println("$australian: " + exchangeRates[exchangeRates.length-1]);
    }
}
