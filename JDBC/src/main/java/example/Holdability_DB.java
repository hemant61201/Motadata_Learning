package org.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Holdability_DB
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
//            connection.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
            System.out.println(connection.createStatement().getResultSetHoldability());

            DatabaseMetaData dbmd=connection.getMetaData();

            System.out.println("dbmd holdability" + dbmd.getConnection().getHoldability());

            System.out.println("dmdb rs holdability" + dbmd.getResultSetHoldability());

            if(dbmd.supportsResultSetHoldability(1))
            {
                System.out.println("HOLD_CURSORS_OVER_COMMIT");
            }



            if(dbmd.supportsResultSetHoldability(2))
            {
                System.out.println("CLOSE_CURSORS_AT_COMMIT");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
