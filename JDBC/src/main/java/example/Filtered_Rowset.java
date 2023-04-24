package org.example;

import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;

class EmployeeSalaryFilter implements Predicate
{
    float low;
    float high;
    public EmployeeSalaryFilter(float low,float high)
    {
        this.low=low;

        this.high=high;
    }

    public boolean evaluate(RowSet rs)
    {
        boolean eval=false;

        try
        {
            FilteredRowSet frs = (FilteredRowSet)rs;

            float esal=frs.getFloat(3);

            if((esal>=low) && (esal<=high))
            {
                eval=true;
            }

            else
            {
                eval=false;
            }

        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return eval;
    }

    @Override
    public boolean evaluate(Object value, int column) throws SQLException {
        return false;
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {
        return false;
    }
}
    public class Filtered_Rowset
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

            try
            {
                RowSetFactory rsf= RowSetProvider.newFactory();

                FilteredRowSet rs=rsf.createFilteredRowSet();

                rs.setUrl("jdbc:h2:tcp://localhost/~/test");

                rs.setUsername("sa");

                rs.setPassword("");

                rs.setCommand("select * from employees");

                rs.execute();

                System.out.println("Data Before Filtering");

                System.out.println("ENO\tENAME\tESAL\tEADDR");

                while(rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
                }

                EmployeeSalaryFilter f = new EmployeeSalaryFilter(100,2000);

                rs.setFilter((javax.sql.rowset.Predicate) f);

                rs.beforeFirst();

                System.out.println("Data After Filtering");

                System.out.println("ENO\tENAME\tESAL\tEADDR");

                while(rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
