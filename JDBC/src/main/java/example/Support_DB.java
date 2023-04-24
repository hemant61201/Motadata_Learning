package org.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class Support_DB
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
            DatabaseMetaData dbmd = connection.getMetaData();

            System.out.println(dbmd.supportsResultSetConcurrency(1003,1007));

            System.out.println(dbmd.supportsResultSetConcurrency(1003,1008));

            System.out.println(dbmd.supportsResultSetType(1003));

            System.out.println(dbmd.supportsResultSetType(1004));

            System.out.println(dbmd.supportsResultSetType(1005));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
