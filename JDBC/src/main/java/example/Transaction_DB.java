package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Transaction_DB
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

            System.out.println("Data before Transaction");

            ResultSet rs =st.executeQuery("select * from accounts");

            while(rs.next())
            {
                System.out.println(rs.getString(1)+"..."+rs.getInt(2));
            }

            System.out.println("Transaction begins...");

            connection.setAutoCommit(false);

            st.executeUpdate("update accounts set balance=balance10000 where name='durga'");

            st.executeUpdate("update accounts set balance=balance+10000 where name='sunny' ");

            System.out.println("Can you please confirm this transaction of 10000....[Yes|No]");

            Scanner sc = new Scanner(System.in);

            String option = sc.next();

            if(option.equalsIgnoreCase("yes"))
            {
                connection.commit();

                System.out.println("Transaction Commited");
            }

            else
            {
                connection.rollback();

                System.out.println("Transaction Rolled Back");
            }

            System.out.println("Data After Transaction");

            ResultSet rs1 =st.executeQuery("select * from accounts");

            while(rs1.next())
            {
                System.out.println(rs1.getString(1) + "..." + rs1.getInt(2));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
