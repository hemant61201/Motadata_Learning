package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Scorable_ResultSet
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
            Statement st =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            ResultSet rs=st.executeQuery("select * from employees");

            System.out.println("Records in Forward Direction");

            System.out.println("ENO\tENAME\tESAL\tEADDR");

            while(rs.next())
            {
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
            }

            System.out.println("Records in Backword Direction");

            System.out.println("ENO\tENAME\tESAL\tEADDR");

            while(rs.previous())
            {
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
