package org.example;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cached_RowSet
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

        try
        {
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa","");

            Statement st = connection.createStatement();

            ResultSet rs =st.executeQuery("select * from employees");

            RowSetFactory rsf= RowSetProvider.newFactory();

            CachedRowSet crs=rsf.createCachedRowSet();

            crs.populate(rs);

            connection.close();

            System.out.println("ENO\tENAME\tESAL\tEADDR");

            while(crs.next())
            {
                System.out.println(crs.getInt(1)+"\t"+crs.getString(2)+"\t"+crs.getFloat(3)+"\t"+crs.getString(4));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
