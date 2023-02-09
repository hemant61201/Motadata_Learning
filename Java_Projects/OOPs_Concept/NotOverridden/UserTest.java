public class UserTest {

    public void printUserType(User u) {
        u.printUserType();
    }

    public void approveReview(Staff s) {
        if (s instanceof Editor) {
            ((Editor) s).approveReview();
        } else {
            System.out.println("Invalid object passed!!");
        }
    }


    public static void main(String[] args) {
        // Overriding of instance variables demo
        User staff = new Staff();
        //System.out.println("User type: " + staff.userType); // early binding
        staff.displayUserInfo();// comparison of field hiding & field overriding effect
    }
}
