package org.example;

import java.sql.*;
import java.util.*;

public class Preparedstatement
{
    public static void main(String[] args)
    {
        String s1 = "text with \"double quote\" \'single quote\'";

        String query = "insert into employees values(?,?,?,?)";
//        String query = "select * from employee";

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
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);

            if(connection.getAutoCommit())
            {
                try
                {
                    preparedStatement.setInt(1,2);

                    preparedStatement.setString(2,"Hemu");

                    preparedStatement.setInt(3,10000);

                    preparedStatement.setString(4,"text with \"double quote\" \'single quote\'");

                    preparedStatement.executeUpdate();

                    System.out.println("Updated Rows");
                }

                catch (Exception exception)
                {
                    System.out.println("There is no Row Updated");
                }
            }

            else
            {
                try
                {
                    preparedStatement.setInt(1,2);

                    preparedStatement.setString(2,"Hemu");

                    preparedStatement.setInt(3,10000);

                    preparedStatement.setString(4,"text with \"double quote\" \'single quote\'");

                    preparedStatement.executeUpdate();

                    System.out.println("Updated Rows");
                }

                catch (Exception exception)
                {
                    System.out.println("There is no Row Updated");
                }
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
