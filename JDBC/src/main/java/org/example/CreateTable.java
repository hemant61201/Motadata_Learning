package org.example;

import java.sql.*;

public class CreateTable
{
    public static void main(String[] args)
    {
        String query = "create table employees(emp_id int, emp_name varchar2(10), emp_salary number(10,2), emp_addr varchar2(100))";

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

            try
            {
                statement.executeUpdate(query);

                System.out.println("Table is Created");
            }

            catch (Exception exception)
            {
                System.out.println("Table is already Created");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}