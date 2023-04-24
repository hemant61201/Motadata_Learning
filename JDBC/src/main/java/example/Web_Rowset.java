package org.example;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;
import java.io.FileWriter;
import java.io.IOException;

public class Web_Rowset
{
    public static void main(String[] args) throws IOException
    {
        FileWriter fileWriter = null;

        try
        {
            Class.forName("org.h2.Driver");
        }

        catch (Exception exception)
        {
            System.out.println("Class Not Found");
        }

        try
        {
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();

            WebRowSet webRowSet = rowSetFactory.createWebRowSet();

            webRowSet.setUrl("jdbc:h2:tcp://localhost/~/test");

            webRowSet.setUsername("sa");

            webRowSet.setPassword("");

            webRowSet.setCommand("select * from employees");

            webRowSet.execute();

            fileWriter = new FileWriter("/root/IdeaProjects/JDBC/src/main/resources/emp.xml");

            webRowSet.writeXml(fileWriter);

            webRowSet.acceptChanges();

            System.out.println("Employee Data transfered to emp.xml file");

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            fileWriter.close();
        }

    }

}
