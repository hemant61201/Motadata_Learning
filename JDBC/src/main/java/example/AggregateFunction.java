package org.example;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AggregateFunction
{
    public static void main(String[] args)
    {
        String query = "";

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
                System.out.println("Find no. of Rows enter 1");

                System.out.println("Find max salary record enter 2");

                System.out.println("Find min salary record enter 3");

                int number = input.nextInt();

                switch (number)
                {
                    case 1:
                    {
                        query = "select COUNT(*) from employee";

                        ResultSet resultSet = statement.executeQuery(query);

                        if(resultSet.next())
                        {
                            System.out.println("No. of records are " + resultSet.getInt(1));
                        }
                    }

                    case 2:
                    {
                        query = "select * from employee in (select max(emp_salary) from employee)";

                        ResultSet resultSet = statement.executeQuery(query);

                        if(resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                                    + resultSet.getString(4));
                        }
                    }

                    case 3:
                    {
                        query = "select * from employee in (select min(emp_salary) from employee)";

                        ResultSet resultSet = statement.executeQuery(query);

                        if(resultSet.next())
                        {
                            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                                    + resultSet.getString(4));
                        }
                    }
                }
            }

            catch (Exception exception)
            {
                System.out.println("Table is already Created");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
