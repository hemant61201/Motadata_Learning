public class TypeCasting {
    static void typeCasting() {
        System.out.println("\nInside typeCasting ...");
        // Explicit casting
        long y = 42;
        //int x = y;
        int x = (int)y;


        byte narrowdByte = (byte)123456;
        System.out.println("narrowdByte: " + narrowdByte);

        // Truncation
        int iTruncated = (int)0.99;
        System.out.println("iTruncated: " + iTruncated);


        y = x;


        char cChar = 'A';
        int iInt = cChar;
        System.out.println("iInt: " + iInt);


        byte bByte = 65;
        cChar = (char)bByte;
        System.out.println("cChar: " + cChar);
    }

    public static void main(String[] args) {
        typeCasting();
    }
}
