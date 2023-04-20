package org.example;

import java.sql.*;
import javax.sql.*;
import org.h2.jdbcx.JdbcConnectionPool;
public class ConnectionPool
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

        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:tcp://localhost/~/test","sa","");

        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = jdbcConnectionPool.getConnection())
        {
            Statement statement = connection.createStatement();

            statement.executeUpdate("create table Stud2(id int, name varchar(10))");

            System.out.println("Successful");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
