package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;

public class Prameter_MetaData
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
            PreparedStatement pst = connection.prepareStatement("insert into employees values(?,?,?,?) ");

            ParameterMetaData pmd=pst.getParameterMetaData();

            int count=pmd.getParameterCount();

            for(int i=1;i<= count;i++)
            {
                System.out.println("Parameter Number:" + i);

                System.out.println("Parameter Mode:" + pmd.getParameterMode(i));

                System.out.println("Parameter Type:" + pmd.getParameterTypeName(i));

                System.out.println("Parameter Precision:" + pmd.getPrecision(i));

                System.out.println("Parameter Scale:" + pmd.getScale(i));

                System.out.println("Parameter isSigned:" + pmd.isSigned(i));

                System.out.println("Parameter isNullable:" + pmd.isNullable(i));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
