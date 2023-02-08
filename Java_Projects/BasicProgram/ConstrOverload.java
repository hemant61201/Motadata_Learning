
class ConstrOverload {
    static int studentCount;

    int id;
    String name;
    String gender;
    int age;
    long phone;
    double gpa;
    char degree;

    boolean international;
    double tuitionFees = 12000.0;
    double internationalFees = 5000.0;

    ConstrOverload(int newId, String newName, String newGender, int newAge, long newPhone, double newGpa,
            char newDegree) {
        this(newId, newName, newGender, newAge, newPhone, newGpa, newDegree, false);
    }

    ConstrOverload(int newId, String newName, String newGender, int newAge, long newPhone, double newGpa,
            char newDegree, boolean isInternational) {
        id = newId;
        name = newName;
        gender = newGender;
        age = newAge;
        phone = newPhone;
        gpa = newGpa;
        degree = newDegree;
        international = isInternational;

        studentCount = studentCount + 1;
        int nextId = id + 1;

        if (international) {
            tuitionFees = tuitionFees + internationalFees;
            //return;
        }

        System.out.println("\nid: " + id);
        System.out.println("nextId: " + nextId);
        System.out.println("name: " + name);
        System.out.println("gender: " + gender);
        System.out.println("age: " + age);
        System.out.println("phone: " + phone);
        System.out.println("gpa: " + gpa);
        System.out.println("degree: " + degree);
        System.out.println("tuitionFees: " + tuitionFees);
        System.out.println("studentCount: " + studentCount);
    }

    ConstrOverload() {}


    public static void main(String[] args) {
        ConstrOverload student1 = new ConstrOverload(1000, "John", "male", 18, 223_456_7890L, 3.8, 'B');

        ConstrOverload student2 = new ConstrOverload(1001, "Raj", "male", 21, 223_456_9999L, 3.4, 'M', true);

        ConstrOverload student3 = new ConstrOverload(1002, "Anita", "female", 20, 223_456_8888L, 4.0, 'M', true);

        System.out.println("Student.studentCount: " + ConstrOverload.studentCount);
    }
}