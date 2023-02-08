class Student {
    int id = 1000;
    byte age = 18;
    short rank = 165;
    long phone = 223_456_7890L;

    float gpa = 3.4f;

    double ga = 2e45;

    char degree = 'B';

    boolean Fee = true;
    double fee = 1200.0;
    double examfee = 200.0;

    // Integer literals: int literal, long literal
    // Double literal

    int minValue = Integer.MIN_VALUE;
    int maxValue = Integer.MAX_VALUE;

    void compute() {
        // local veriable not have default value , must initialize
        int nextId = id + 1;

        if(Fee){
            fee += examfee;
        }

        System.out.println("id: " + id);
        System.out.println("nextId: " + nextId);
        System.out.println("age: " + age);
        System.out.println("phone: " + phone);
        System.out.println("minValue: " + minValue);
        System.out.println("maxValue: " + maxValue);
        System.out.println("gpa: " + gpa);
        System.out.println("ga: " + ga);
        System.out.println("degree: " + degree);
        System.out.println("Fee: " + fee);
    }

    public static void main(String[] args) {
        Student s = new Student();
        s.compute();
    }
}