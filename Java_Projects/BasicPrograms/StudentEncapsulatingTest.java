public class StudentEncapsulatingTest {
    public static void main(String[] args) {
        StudentEncapsuling student1 = new StudentEncapsuling(1000, "Joan", "male", 18, 223_456_7890L, 3.8, 'B');

        StudentEncapsuling student2 = new StudentEncapsuling(1001, "Raj", "male", 21, 223_456_9999L, 3.4, 'M', true);

        StudentEncapsuling student3 = new StudentEncapsuling(1002, "Anita", "female", 20, 223_456_8888L, 4.0, 'M', true);

        System.out.println("\nStudent.studentCount: " + StudentEncapsuling.studentCount);

        System.out.println("\nName of student1: " + student1.getName());
        System.out.println("Name of student2: " + student2.getName());
        System.out.println("Name of student3: " + student3.getName());

        student1.updateProfile("John");
        System.out.println("Updated name of student1: " + student1.getName());
    }
}
