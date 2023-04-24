package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Scorable_Sensetive
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
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            ResultSet rs=st.executeQuery("select * from employees");

            System.out.println("Records Before Updation");

            System.out.println("ENO\tENAME\tESAL\tEADDR");

            while(rs.next())
            {
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
            }

            System.out.println("Application is in pausing state,please update database..");

            System.in.read();

            rs.beforeFirst();

            System.out.println("Records After Updation");

            while(rs.next())
            {
                rs.refreshRow();

                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
