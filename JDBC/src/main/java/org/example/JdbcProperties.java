package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class JdbcProperties
{
    public static void main(String[] args) throws IOException
    {
        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream("/root/IdeaProjects/JDBC/src/main/resources/jdbcAuthentication");

        properties.load(fileInputStream);

        String url = properties.getProperty("url");

        String user = properties.getProperty("user");

        String password = properties.getProperty("password");

        try(Connection connection = DriverManager.getConnection(url,user,password))
        {
            Statement statement = connection.createStatement();

            statement.executeUpdate("create table Stud3(id int, name varchar(10))");

            System.out.println("Successful");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
