public class ControlFlowIf {
    static boolean ifStatement() {
        boolean approved = false;

        int age = 27;
        int salary = 60000;
        boolean hasBadCredit = false;

        if (age >= 25 && age <= 35 && salary >= 50000) {
            approved = true;
            System.out.println("age >= 25 && age <= 35 && salary >= 50000");
        } else if (age > 35 && age <= 45 && salary >= 70000) {
            approved = true;
            System.out.println("age > 35 && age <= 45 && salary >= 70000");
        } else if (age > 45 && age <= 55 && salary >= 90000) {
            approved = true;
            System.out.println("age > 45 && age <= 55 && salary >= 90000");
        } else {
            if (age > 55 && !hasBadCredit) {
                approved = true;
                System.out.println("age > 55 && !hasBadCredit");
            }
            System.out.println("else block");
        }


        System.out.println("outside if");
        return approved;
    }
    public static void main(String[] args){
        ifStatement();
    }
}
