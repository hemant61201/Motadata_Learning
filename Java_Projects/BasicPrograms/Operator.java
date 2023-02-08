public class Operator {
    static void preAndPost() {
        System.out.println("\nInside preAndPost ...");

        int x = 5;

        //--x;
        //System.out.println("x: " + x);

        //int y = x--;
        //System.out.println("y: " + y + ", x: " +  x);

        int index = 0;
        int[] array = new int[3];
        array[index++] = 10;
        array[index++] = 20;
        array[index++] = 30;

        System.out.println(index);
    }


    static void compoundArithmeticAssignment() {
        int x = 100;

        System.out.println("x += 5: " + (x += 5));
        System.out.println("x -= 5: " + (x -= 5));
        System.out.println("x *= 5: " + (x *= 5));
        System.out.println("x /= 5: " + (x /= 5));
        System.out.println("x %= 5: " + (x %= 5));

        // Invalid
        System.out.println("x =+ 5: " + (x =+ 5)); // Unary plus ~ x = +5
        System.out.println("x =- 5: " + (x =- 5)); // Unary minus ~ x = -5
		/*System.out.println("x =* 5: " + (x =* 5));
		System.out.println("x =/ 5: " + (x =/ 5));
		System.out.println("x =% 5: " + (x =% 5));*/
    }
    static void isOddOrEven(int num) {
        System.out.println(num % 2);
    }

    static void charTypePromotion() {
        System.out.println("\nInside charTypePromotion ...");
        char char1 = 50; // Will be assigned corresponding UTF16 value 2
        System.out.println("char1: " + char1);
        System.out.println("(73 - char1): " + (73 - char1)); // char1 gets promoted to int, i.e., decimal equivalent 50 in UTF16 is used
        System.out.println("(char1 - '3'): " + (char1 - '3')); // char1 & '3' are promoted to ints
        System.out.println("('a' + 'b'): " + ('a' +'b')); // 'a' & 'b' are promoted to ints and the respective equivalents 97 & 98 are added
    }

    static void comparisonOperators() {
        int age = 20;
	    /*if (age > 21) {
			System.out.println("Graduate student");
		}*/
        System.out.println("age > 21: " + (age > 21));
        System.out.println("age >= 21: " + (age >= 21));
        System.out.println("age < 21: " + (age < 21));
        System.out.println("age <= 21: " + (age <= 21));
        System.out.println("age == 21: " + (age == 21)); // equal to (equality operator)
        System.out.println("age != 21: " + (age != 21)); // not equal to (equality operator)

        boolean isInternational = true;
        //System.out.println("isInternational <= true: " + (isInternational <= true));
        System.out.println("isInternational == true: " + (isInternational == true));
        System.out.println("isInternational != true: " + (isInternational != true));

    }

    static void logicalOperators() {
        System.out.println("\nInside logicalOperators ...");
        int age = 37;
        int salary = 85000;
        boolean hasBadCredit = false;
        if (age > 35 && salary > 90000 || !hasBadCredit) {
            System.out.println("Loan approved!");
        } else {
            System.out.println("Loan not approved!");
        }
        //    (a) Left-associative ~ Order of grouping
        //    (b) Associativity (a && b) && c = a && (b && c)
        //    Applies to both && and ||
        //    (a) Operator precedence of Logical Operators: Helps with ONLY grouping operations. Not order of execution. (! > && > ||)
        // Other Examples: A && B || C && D = (A && B) || (C && D)
        //                 A && B && C || D = ((A && B) && C) || D
        //    (b) Operator Precedence across logical, comparison and arithmetic
        //          ! > arithmetic > comparison > &&, ||
        //    See resources section for complete precedence rules
        // ALWAYS USE PARENTHESIS for READABILITY. Not everyone is aware of precedence rules
        // 4. Use && to avoid NullPointerException
    }

    static void bitwiseOperators() {
        System.out.println("\nInside bitwiseOperators ...");
        int x = 1;
        int y = 3;

        System.out.println("x & y: " + (x & y));
        System.out.println("x | y: " + (x | y));
        System.out.println("x ^ y: " + (x ^ y));
        System.out.println("~x: " + (~x));
        System.out.println("true & false: " + (true & false));

        char c1 = 'a'; // U+0061 --> 0110 0001
        char c2 = 'b'; // U+0062 --> 0110 0010
        System.out.println("c1 | c2: " + (c1 | c2)); // 0110 0011 --> 99 in decimal

        // Since bitwise work only on Integer types, following will not compile
        //double d1 = 3.14;
        //double d2 = 5.15;
        //System.out.println("d1 | d2: " + (d1 | d2));
    }

    public static void main(String[] args) {

        preAndPost();
        compoundArithmeticAssignment();
        isOddOrEven(51);
        charTypePromotion();
        comparisonOperators();
        logicalOperators();
        bitwiseOperators();
    }
}
