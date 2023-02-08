public class ForStatement {

    static void labeledBreak() {
        System.out.println("\nInside labeledBreak ...");
        int num = 0;

        outermost: for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 5 && j == 5) {
                    break outermost;
                }
                num++;
            }
        }

        System.out.println("num: " + num); // prints 55
    }

    static void labeledContinue() {
        System.out.println("\nInside labeledContinue ...");
        int num = 0;

        outermost: for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 5 && j == 5) {
                    continue outermost;
                }
                num++;
            }
        }

        System.out.println("num: " + num); // prints 55
    }

    public static void main(String[] args){

        labeledBreak();
        labeledContinue();
        int[] iArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = iArray.length-1; i >= 0; i--) {
            System.out.print(iArray[i] + " ");
        }


        System.out.println("\n\nReversing Array ... ");
        for (int i = 0, j = iArray.length-1, middle = iArray.length >>> 1; i < middle; i++, j--) {
            int temp = iArray[i];
            iArray[i] = iArray[j];
            iArray[j] = temp;
        }

        for (int i = 0; i < iArray.length; i++) {
            System.out.print(iArray[i] + " ");
        }

        System.out.println("\n\nCounting divisors ...");
        int x = 24;
        int divisorCount = 0;
        for (int i = 1; i <= x; i++) {
            if (x % i == 0) {
                System.out.print(i + " ");
                divisorCount++;
            }
        }
        System.out.println("\nDivisor Count: " + divisorCount);

        System.out.println("\nDisplaying Student Grades ...");
        int[][] studentGrades = {{76, 52, 69, 83, 45, 90}, {22, 71, 67, 69, 40}, {53, 87, 91, 25}};
        for (int i = 0; i < studentGrades.length; i++) {
            System.out.print("\nDisplaying grades of students from class " + i + ": ");
            int max = 0;
            for (int j = 0; j < studentGrades[i].length; j++) {
                if (studentGrades[i][j] > max) {
                    max = studentGrades[i][j];
                }
                System.out.print(studentGrades[i][j] + " ");
            }
            System.out.println("\nmax: " + max);
        }

    }
}

