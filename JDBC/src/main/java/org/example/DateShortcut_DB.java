package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DateShortcut_DB
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
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter Person Name:");

            String uname=sc.next();

            System.out.println("Enter DOP(yyyy-MM-dd):");

            String dop=sc.next();

            java.sql.Date sdate=java.sql.Date.valueOf(dop);

            String sqlQuery="insert into users values(?,?)";

            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ps.setString(1,uname);

            ps.setDate(2,sdate);

            int rc =ps.executeUpdate();

            if(rc==0)
                System.out.println("Record Not inserted");

            else
                System.out.println("Record inserted");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
