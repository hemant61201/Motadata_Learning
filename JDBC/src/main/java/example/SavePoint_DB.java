package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

public class SavePoint_DB
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
            Statement statement = connection.createStatement();

            connection.setAutoCommit(false);

            statement.executeUpdate("insert into politicians values ('kcr','trs')");

            statement.executeUpdate("insert into politicians values ('babu','tdp')");

            Savepoint sp = connection.setSavepoint();

            statement.executeUpdate("insert into politicians values ('siddu','bjp')");

            Scanner scanner = new Scanner(System.in);

            String input = scanner.next();

            if(input.equalsIgnoreCase("no"))
            {
                connection.releaseSavepoint(sp);

                connection.commit();
            }

            else
            {
                System.out.println("oops ..wrong entry just rollback");

                connection.rollback(sp);

                System.out.println("Operations are roll back from Savepoint");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
