package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DropTable
{
    public static void main(String[] args)
    {
        String query = "drop table employees";

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
                statement.executeUpdate(query);

                System.out.println("Table is Dropped");
            }

            catch (Exception exception)
            {
                System.out.println("There is no such Table");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
