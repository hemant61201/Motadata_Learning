package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTable
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

            try
            {
                System.out.println("View all records enter 1");

                System.out.println("View all records based on salary sorting order enter 2");

                System.out.println("Select records based on ID enter 3");

                System.out.println("Select records based on salary range enter 4");

                System.out.println("Select range of records based on employee initial character enter 5");

                int number = input.nextInt();

                switch (number)
                {
                    case 1:
                    {
                        String query = "select * from employees";

                        ResultSet resultSet = statement.executeQuery(query);

                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                            + resultSet.getString(4));
                        }
                    }

                    case 2:
                    {
                        String query = "select * from employees order by emp_salary asc";

                        ResultSet resultSet = statement.executeQuery(query);

                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                                    + resultSet.getString(4));
                        }
                    }

                    case 3:
                    {
                        System.out.println("Enter ID");

                        int id = input.nextInt();

                        String query = String.format("select * from employees where emp_id = %d", id);

                        ResultSet resultSet = statement.executeQuery(query);

                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                                    + resultSet.getString(4));
                        }
                    }

                    case 4:
                    {
                        System.out.println("Enter Salary range");

                        int beginsal = input.nextInt();

                        int endsal = input.nextInt();

                        String query = String.format("select * from employees where emp_salary >= %d and emp_salary <= %d", beginsal,endsal);

                        ResultSet resultSet = statement.executeQuery(query);

                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                                    + resultSet.getString(4));
                        }
                    }

                    case 5:
                    {
                        System.out.println("Enter initial Character");

                        String intialChar = input.next() + "%";

                        String query = String.format("select * from employees where emp_name like '%s'", intialChar);

                        ResultSet resultSet = statement.executeQuery(query);

                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                                    + resultSet.getString(4));
                        }
                    }

                    case 6:
                    {
                        String query = String.format("select emp_name,emp_salary from employees");

                        ResultSet resultSet = statement.executeQuery(query);

                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
                        }
                    }

                    case 7:
                    {
                        String query = "insert into employees values(1,'Hi',10000,'asd')";

                        statement.executeQuery(query);

//                        ResultSet resultSet = statement.executeQuery(query);
//
//                        while (resultSet.next())
//                        {
//                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
//                                    + resultSet.getString(4));
//                        }
                    }
                }
            }

            catch (Exception exception)
            {
//                System.out.println("Table is already Created");
                exception.printStackTrace();
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
