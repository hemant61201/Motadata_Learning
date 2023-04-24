package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Metadata_DB
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
            DatabaseMetaData dbmd= connection.getMetaData();

            System.out.println("Database Product Name:"+dbmd.getDatabaseProductName());

            System.out.println("DatabaseProductVersion:"+dbmd.getDatabaseProductVersion());
            System.out.println("DatabaseMajorVersion:"+dbmd.getDatabaseMajorVersion());
            System.out.println("DatabaseMinorVersion:"+dbmd.getDatabaseMinorVersion());
            System.out.println("JDBCMajorVersion:"+dbmd.getJDBCMajorVersion());
            System.out.println("JDBCMinorVersion:"+dbmd.getJDBCMinorVersion());
            System.out.println("DriverName:"+dbmd.getDriverName());
            System.out.println("DriverVersion:"+dbmd.getDriverVersion());
            System.out.println("URL:"+dbmd.getURL());
            System.out.println("UserName:"+dbmd.getUserName());
            System.out.println("MaxColumnsInTable:"+dbmd.getMaxColumnsInTable());
            System.out.println("MaxRowSize:"+dbmd.getMaxRowSize());
            System.out.println("MaxStatementLength:"+dbmd.getMaxStatementLength());
            System.out.println("MaxTablesInSelect"+dbmd.getMaxTablesInSelect());
            System.out.println("MaxTableNameLength:"+dbmd.getMaxTableNameLength());
            System.out.println("SQLKeywords:"+dbmd.getSQLKeywords());
            System.out.println("NumericFunctions:"+dbmd.getNumericFunctions());
            System.out.println("StringFunctions:"+dbmd.getStringFunctions());
            System.out.println("SystemFunctions:"+dbmd.getSystemFunctions());
            System.out.println("supportsFullOuterJoins:"+dbmd.supportsFullOuterJoins());
            System.out.println("supportsStoredProcedures:"+dbmd.supportsStoredProcedures());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
