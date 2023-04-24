package org.example;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Retrive_Image
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
            PreparedStatement preparedstatement = connection.prepareStatement("select * from images");

            FileOutputStream fileOutputStream = new FileOutputStream("/root/IdeaProjects/JDBC/src/main/resources/cicheroes_db.png");

            ResultSet resultSet = preparedstatement.executeQuery();

            if(resultSet.next())
            {
                String name = resultSet.getString(1);

                InputStream inputStream = resultSet.getBinaryStream(2);

                byte[] buffer = new byte[1024];

                while (inputStream.read(buffer) > 0)
                {
                    fileOutputStream.write(buffer);
                }

                fileOutputStream.flush();
            }

            System.out.println("Successful");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
