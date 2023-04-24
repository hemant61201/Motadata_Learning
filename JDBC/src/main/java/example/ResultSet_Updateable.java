package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSet_Updateable
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
            Statement st =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            ResultSet rs=st.executeQuery("select emp_id,emp_name,emp_salary,emp_addr from employees1");

            rs.last();

            rs.deleteRow();

            System.out.println("deleted rows");

            rs.absolute(2);

            rs.updateString(2,"KTR");

            rs.updateInt(3,10000);

            rs.updateRow();

            System.out.println("Row updated");

            rs.moveToInsertRow();//creates an empty record

            rs.updateInt(1,900);

            rs.updateString(2,"katrina");

            rs.updateInt(3,3000);

            rs.updateString(4,"Hyd");

            rs.insertRow();

            System.out.println("Insert row");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
