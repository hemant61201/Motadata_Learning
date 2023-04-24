package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertTable
{
    public static void main(String[] args)
    {
        String s1 = "text with \"double quote\" \'single quote\'";

        String query = "insert into employees1 values(3,'Hemant',10000,'xyz')";
//        String query = "select * from employee";

        System.out.println(query);

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

            if(connection.getAutoCommit())
            {
                try
                {
                    int rowUpdate = statement.executeUpdate(query);

                    System.out.println("Updated Rows" + rowUpdate);
                }

                catch (Exception exception)
                {
                    System.out.println("There is no Row Updated");
                }
            }

            else
            {
                try
                {
                    int rowUpdate = statement.executeUpdate(query);

                    statement.executeUpdate("commit ");

                    System.out.println("Updated Rows" + rowUpdate);
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
