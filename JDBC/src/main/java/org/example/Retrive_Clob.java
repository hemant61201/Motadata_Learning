package org.example;

import javax.print.DocFlavor;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Retrive_Clob
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
            PreparedStatement preparedstatement = connection.prepareStatement("select * from textfiles");

            ResultSet resultSet = preparedstatement.executeQuery();

            FileWriter fileWriter = new FileWriter("/root/IdeaProjects/JDBC/src/main/resources/out.txt");

            if(resultSet.next())
            {
                String name = resultSet.getString(1);

                Reader reader = resultSet.getCharacterStream(2);

                int i = reader.read();

                while (i != -1)
                {
                    fileWriter.write(i);

                    i = reader.read();
                }

                fileWriter.flush();
            }

            System.out.println("Successful");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
