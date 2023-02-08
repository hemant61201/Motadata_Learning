public class MoneyTransfer {

    CurrencyCon cc = new CurrencyCon();

    double computeTransferAmount(int countryIndex, double amount) {
        return cc.computeTransferAmount(countryIndex, amount);
    }

    double computeTransferFee(int countryIndex, double amount) {
        double transferAmount = computeTransferAmount(countryIndex, amount);
        double transferFee = transferAmount * 0.02;
        return transferFee;
    }

    public static void main(String[] args) {
        MoneyTransfer transferService = new MoneyTransfer();

        double transferAmount = transferService.computeTransferAmount(0, 1000);
        double transferFee = transferService.computeTransferFee(0, 1000);

        System.out.println("transferAmount: " + transferAmount);
        System.out.println("transferFee: " + transferFee);
    }

}