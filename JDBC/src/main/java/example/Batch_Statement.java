package org.example;

import java.sql.*;
import java.sql.DriverManager;

public class Batch_Statement
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
            System.out.println(connection.getAutoCommit());

            Statement st = connection.createStatement();

            st.addBatch("insert into employees values(600,'Mallika',6000,'Chennai')");

            Thread.sleep(10000);

            st.addBatch("insert into employee values(600,'Mallika',6000,'Chennai')");

            st.addBatch("insert into employees values(601,'Mallika',6000,'Chennai')");

            st.addBatch("update employees set esal=esal+1000 where esal<4000");

            st.addBatch("delete from employees where esal>5000");

            int[] count=st.executeBatch();

            int updateCount=0;

            for(int x: count)
            {
                updateCount=updateCount+x;
            }

             System.out.println("The number of rows updated :"+updateCount);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
