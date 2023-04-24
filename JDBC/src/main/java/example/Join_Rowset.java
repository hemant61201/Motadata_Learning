package org.example;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;

public class Join_Rowset
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
            RowSetFactory rsf = RowSetProvider.newFactory();

            CachedRowSet rs1=rsf.createCachedRowSet();

            rs1.setCommand("select * from student");

            rs1.execute(connection);

            CachedRowSet rs2=rsf.createCachedRowSet();

            rs2.setCommand("select * from courses");

            rs2.execute(connection);

            JoinRowSet rs=rsf.createJoinRowSet();

            rs.addRowSet(rs1, 4);

            rs.addRowSet(rs2, 1);

            System.out.println("SID\tSNAME\tSADDR\tCID\tCNAME\tCCOST");

            while(rs.next())
            {
                System.out.print(rs.getString(1)+"\t");

                System.out.print(rs.getString(2)+"\t");

                System.out.print(rs.getString(3)+"\t");

                System.out.print(rs.getString(4)+"\t");

                System.out.print(rs.getString(5)+"\t");

                System.out.print(rs.getString(6)+"\n");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
