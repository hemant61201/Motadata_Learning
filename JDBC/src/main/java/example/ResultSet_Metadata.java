package org.example;

import java.sql.*;

public class ResultSet_Metadata
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
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("select * from employees");

            ResultSetMetaData rsmd = rs.getMetaData();

            int count = rsmd.getColumnCount();

            for(int i=1;i<= count;i++)
            {
                System.out.println("Column Number:" + i);

                System.out.println("Column Name:" + rsmd.getColumnName(i));

                System.out.println("Column Type:" + rsmd.getColumnTypeName(i));

                System.out.println("Column Size:" + rsmd.getColumnDisplaySize(i));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
