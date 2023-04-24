package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Clob
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
            PreparedStatement preparedstatement = connection.prepareStatement("create table textfile(name varchar(10), files CLOB)");

            preparedstatement.execute();

            File file = new File("/root/IdeaProjects/JDBC/src/main/resources/A.txt");

            FileReader fileReader = new FileReader(file);

            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into textfiles values(?,?)");

            preparedStatement1.setString(1,"Hemant");

            preparedStatement1.setCharacterStream(2, fileReader);

            int x = preparedStatement1.executeUpdate();

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
