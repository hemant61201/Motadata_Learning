package org.example;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

public class Jdbc_RowSet
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
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();

            JdbcRowSet jdbcRowSet = rowSetFactory.createJdbcRowSet();

            jdbcRowSet.setUrl("jdbc:h2:tcp://localhost/~/test");

            jdbcRowSet.setUsername("sa");

            jdbcRowSet.setPassword("");

            jdbcRowSet.setCommand("select * from employees");

            jdbcRowSet.execute();

            System.out.println("Employee Details In Forward Direction");

            System.out.println("ENO\tENAME\tESAL\tEADDR");


            while(jdbcRowSet.next())
            {
                System.out.println(jdbcRowSet.getInt(1)+"\t"+jdbcRowSet.getString(2)+"\t"+jdbcRowSet.getFloat(3)+"\t"+jdbcRowSet.getString(4));
            }

            System.out.println("Employee Details In Backward Direction");

            System.out.println("ENO\tENAME\tESAL\tEADDR");

            while(jdbcRowSet.previous())
            {
                System.out.println(jdbcRowSet.getInt(1)+"\t"+jdbcRowSet.getString(2)+"\t"+jdbcRowSet.getFloat(3)+"\t"+jdbcRowSet.getString(4));
            }

            System.out.println("Accessing Randomly...");

            jdbcRowSet.absolute(3);

            System.out.println(jdbcRowSet.getRow()+"--- >"+jdbcRowSet.getInt(1)+"\t"+jdbcRowSet.getString(2)+"\t"+jdbcRowSet.getFloat(3)+"\t"+jdbcRowSet.getString(4));

            jdbcRowSet.first();

            System.out.println(jdbcRowSet.getRow()+"--- >"+jdbcRowSet.getInt(1)+"\t"+jdbcRowSet.getString(2)+"\t"+jdbcRowSet.getFloat(3)+"\t"+jdbcRowSet.getString(4));

            jdbcRowSet.last();

            System.out.println(jdbcRowSet.getRow()+"--- >"+jdbcRowSet.getInt(1)+"\t"+jdbcRowSet.getString(2)+"\t"+jdbcRowSet.getFloat(3)+"\t"+jdbcRowSet.getString(4));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
