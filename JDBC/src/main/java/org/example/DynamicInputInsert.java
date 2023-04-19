package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class DynamicInputInsert
{
    public static void main(String[] args)
    {
        try
        {
            Class.forName("org.h2.Driver");
        }

        catch (Exception exception)
        {
            System.out.println("Class Not Found");
        }

        try(Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa",""))
        {
            Statement statement = connection.createStatement();

            Scanner input = new Scanner(System.in);

            if(connection.getAutoCommit())
            {
                try
                {
                    while (true)
                    {
                        System.out.println("Enter Employee ID");

                        int emp_id = input.nextInt();

                        System.out.println("Enter Employee Name");

                        String emp_name = input.next();

                        System.out.println("Enter Employee Salary");

                        int emp_salary = input.nextInt();

                        System.out.println("Enter Your Address");

                        String emp_addr = input.next();

                        String query = String.format("insert into employees values(%d,'%s',%d,'%s')", emp_id,emp_name,emp_salary,emp_addr);

                        int rowUpdate = statement.executeUpdate(query);

                        System.out.println("Updated Rows" + rowUpdate);

                        System.out.println("Want to enter more Records [Yes/No]");

                        String option = input.next();

                        if(option.equalsIgnoreCase("no"))
                        {
                            break;
                        }
                    }
                }

                catch (Exception exception)
                {
//                    System.out.println("There is no Row Updated");
                    exception.printStackTrace();
                }
            }

            else
            {
                try
                {
                    while (true)
                    {
                        System.out.println("Enter Employee ID");

                        int emp_id = input.nextInt();

                        System.out.println("Enter Employee Name");

                        String emp_name = input.nextLine();

                        System.out.println("Enter Employee Salary");

                        int emp_salary = input.nextInt();

                        System.out.println("Enter Your Address");

                        String emp_addr = input.nextLine();

                        String query = String.format("insert into employee values(%d,'%s',%d,'%s')", emp_id,emp_name,emp_salary,emp_addr);

                        int rowUpdate = statement.executeUpdate(query);

                        System.out.println("Updated Rows" + rowUpdate);

                        System.out.println("Want to enter more Records [Yes/No]");

                        String option = input.next();

                        if(option.equalsIgnoreCase("no"))
                        {
                            break;
                        }
                    }
                }

                catch (Exception exception)
                {
                    System.out.println("There is no Row Updated");
                }
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
