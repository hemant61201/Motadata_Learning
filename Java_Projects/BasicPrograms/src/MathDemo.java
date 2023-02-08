public class MathDemo {
    private static void mathDemo() {
        // Lottery [1, 100]
        double random = Math.random(); // [0.0, 1.0)
        int winner = (int) (random * 100) + 1;

        System.out.println(random);
        System.out.println(winner);

        // round ~ rounding floating point to nearest integers
        long distance1 = Math.round(24.45);
        int distance2 = Math.round(24.5f);

        System.out.println(distance1);
        System.out.println(distance2);

        // ceil ~ round-up
        double ceil1 = Math.ceil(24.45);
        double ceil2 = Math.ceil(25.0);

        System.out.println(ceil1);
        System.out.println(ceil2);

        // floor ~ round down
        double floor1 = Math.floor(24.45);
        double floor2 = Math.floor(25.0);

        System.out.println(floor1);
        System.out.println(floor2);

        double max = Math.max(24.45, 24.5);
        System.out.println(max);

        double abs = Math.abs(-24.45);
        System.out.println(abs);

        double pow = Math.pow(2.0, 4.0);
        System.out.println(pow);

        double nan = 0.0/0.0;
        double sqrt = Math.sqrt(nan);
        System.out.println(nan);
        // Intro to Machine learning ~ "deep learning"
        double idf = Math.log(100/8);
        System.out.println(idf);

        //Math math = new Math();
    }

    public static void main(String[] args){
        mathDemo();
    }
}
