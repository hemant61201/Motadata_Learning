// Java Program to Illustrate Anonymous Inner classes
// Declaration Without any Name
// As a subclass of the specified type

// Importing required classes
import java.util.*;

// Class 1
// Helper class
class Demo {

    // Method of helper class
    void show()
    {
        // Print statement
        System.out.println(
                "i am in show method of super class");
    }
}
class Demo1 {

    // Method of helper class
    void show()
    {
        // Print statement
        System.out.println(
                "i am in show d1 method of super class");
    }
}

// Class 2
// Main class
class Flavor1Demo {

    // An anonymous class with Demo as base class
    static Demo d = new Demo() {
        //Flavor1Demo d2 = new Flavor1Demo();
        // Method 1
        // show() method
        void show()
        {
            // Calling method show() via super keyword
            // which refers to parent class
            super.show();

            // Print statement
            System.out.println("i am in Flavor1Demo class");
        }
    };

    static Demo1 d1 = new Demo1() {
        // Method 1
        // show() method
        void show()
        {
            // Calling method show() via super keyword
            // which refers to parent class
            super.show();

            // Print statement
            System.out.println("i am in Flavor1Demo class");
        }
    };


    // Method 2
    // Main driver method
    public static void main(String[] args)
    {
        // Calling show() method inside main() method
        d.show();
        d1.show();

    }
}
