package org.example;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.ResultSet;

public class Jdbc_Rowset_CRUD
{
    public static void main(String[] args)
    {
        try
        {
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();

            JdbcRowSet rs = rowSetFactory.createJdbcRowSet();

            rs.setUrl("jdbc:h2:tcp://localhost/~/test");

            rs.setUsername("sa");

            rs.setPassword("");

            rs.setCommand("select emp_id,emp_name,emp_salary,emp_addr from employees1");

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
