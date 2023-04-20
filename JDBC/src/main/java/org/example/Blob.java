package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Blob
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
            PreparedStatement preparedstatement = connection.prepareStatement("create table images(name varchar(10), image BLOB)");

            preparedstatement.execute();

            File file = new File("/root/IdeaProjects/JDBC/src/main/resources/cricheroes.png");

            FileInputStream fileInputStream = new FileInputStream(file);

            String query = "insert into images values(?,?)";

            PreparedStatement preparedstatement1 = connection.prepareStatement(query);

            preparedstatement1.setString(1,"Hemant");

            preparedstatement1.setBinaryStream(2,fileInputStream);

            int x = preparedstatement1.executeUpdate();

            if(x != 0)
            {
                System.out.println("Successful");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
