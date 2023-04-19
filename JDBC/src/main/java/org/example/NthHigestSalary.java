package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class NthHigestSalary
{
    public static void main(String[] args)
    {
        int n = 3;

        String query = "select * from(select emp_id,emp_name,emp_salary,emp_addr,rank() over(order by emp_salary DESC) ranking from employees) where ranking =" + n;

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

            try
            {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " "
                            + resultSet.getString(4));
                }
            }

            catch (Exception exception)
            {
//                System.out.println("No record Found");
                exception.printStackTrace();
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
