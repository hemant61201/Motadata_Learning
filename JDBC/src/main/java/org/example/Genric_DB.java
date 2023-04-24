package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Genric_DB
{
    static HashMap<Integer, String> columns_Names = new HashMap<>();

    static String crud_type;

    static String table_name;

    static String column_name;

    public static void show_Columns(DatabaseMetaData metaData, String table)
    {
        try
        {
            ResultSet rs = metaData.getColumns(null, null, table.toUpperCase(), null);

            int count = 0;

            while (rs.next())
            {
                System.out.println("Column Number: " + ++count);

                String name = rs.getString("COLUMN_NAME");

                String type = rs.getString("TYPE_NAME");

                int size = rs.getInt("COLUMN_SIZE");

                System.out.println("Column name: " + name);

                System.out.println("Column type: " + type);

                System.out.println("Column size: " + size);

                columns_Names.put(count, name);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    public static void show_Table(DatabaseMetaData metaData)
    {
        try
        {
            String[] types = {"TABLE"};

            ResultSet tables = metaData.getTables(null, null, "%", types);

            System.out.println("Tables in the database:");

            while (tables.next())
            {
                System.out.println(tables.getString("TABLE_NAME"));
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        Properties auth_Properties = new Properties();

        try(FileInputStream fileInputStream = new FileInputStream("/root/IdeaProjects/JDBC/src/main/resources/jdbcAuthentication"))
        {
            auth_Properties.load(fileInputStream);
        }

        catch (Exception exception)
        {
            System.out.println("Authentication File Not Found");
        }

        try(Connection connection = DriverManager.getConnection(auth_Properties.getProperty("url"),auth_Properties.getProperty("user"),auth_Properties.getProperty("password")); OutputStream output = new FileOutputStream("/root/IdeaProjects/JDBC/src/main/resources/Query_Properties"))
        {
            while (true)
            {
                System.out.println("Select number for Tasks");

                System.out.println("1. Select");

                System.out.println("2. Insert");

                System.out.println("3. Update");

                System.out.println("4. Delete");

                System.out.println("Enter Your Task No.");

                Scanner input = new Scanner(System.in);

                int number = input.nextInt();

                Statement statement = connection.createStatement();

                List<Map<String, Object>> rows = new ArrayList<>();

                ResultSet resultSet;

                DatabaseMetaData metaData = connection.getMetaData();

                switch (number)
                {
                    case 1:
                    {
                        crud_type = "select";

                        show_Table(metaData);

                        System.out.println("Please enter Your table name");

                        String table = input.next();

                        if(table != null)
                        {
                            table_name = table;

                            show_Columns(metaData, table);

                            System.out.println("Please enter Columns number to see OR enter * for all columns");

                            String columns = input.next();

                            if(columns != null)
                            {
                                if(columns.equals("*"))
                                {
                                    column_name = "*";

                                    System.out.println("You want to put any Condition");

                                    String choice = input.next();

                                    if(choice.equalsIgnoreCase("No"))
                                    {
                                        String query = crud_type + " " + column_name + " from " + table_name;

                                        resultSet = statement.executeQuery(query);

                                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                                        while (resultSet.next())
                                        {
                                            Map<String, Object> row = new HashMap<>();

                                            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                                            {
                                                row.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                                            }

                                            System.out.println(row);

                                            rows.add(row);
                                        }

                                        ObjectMapper mapper = new ObjectMapper();

                                        String json = mapper.writeValueAsString(rows);

                                        File file = new File("/root/IdeaProjects/JDBC/src/main/resources/Query_Result.json");

                                        mapper.writeValue(file, rows);

                                        break;
                                    }

                                    else
                                    {
                                        System.out.println("Enter Your Condition");

                                        String condition = input.next();

                                        String query = crud_type + " " + column_name + " from " + table_name + " where " + condition;

                                        resultSet = statement.executeQuery(query);

                                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                                        while (resultSet.next())
                                        {
                                            Map<String, Object> row = new HashMap<>();

                                            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                                            {
                                                row.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                                            }

                                            System.out.println(row);

                                            rows.add(row);
                                        }

                                        ObjectMapper mapper = new ObjectMapper();

                                        String json = mapper.writeValueAsString(rows);

                                        File file = new File("/root/IdeaProjects/JDBC/src/main/resources/Query_Result.json");

                                        mapper.writeValue(file, rows);

                                        break;
                                    }
                                }

                                else
                                {
                                    String[] column_name = columns.split(",");

                                    String column_names = "";

                                    for(String col : column_name)
                                    {
                                        if(columns_Names.containsKey(Integer.valueOf(col)))
                                        {
                                            column_names += columns_Names.get(Integer.valueOf(col)) + ",";
                                        }

                                        else
                                        {
                                            System.out.println("Enter valid numbers of Columns");
                                        }
                                    }

                                    System.out.println("You want to put any Condition");

                                    String choice = input.next();

                                    if(choice.equalsIgnoreCase("No"))
                                    {
                                        String query = crud_type + " " + column_names.substring(0, column_names.length() - 1) + " from " + table_name;

                                        resultSet = statement.executeQuery(query);

                                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                                        while (resultSet.next())
                                        {
                                            Map<String, Object> row = new HashMap<>();

                                            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                                            {
                                                row.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                                            }

                                            System.out.println(row);

                                            rows.add(row);
                                        }

                                        ObjectMapper mapper = new ObjectMapper();

                                        String json = mapper.writeValueAsString(rows);

                                        File file = new File("/root/IdeaProjects/JDBC/src/main/resources/Query_Result.json");

                                        mapper.writeValue(file, rows);

                                        break;
                                    }

                                    else
                                    {
                                        System.out.println("Enter Your Condition");

                                        String condition = input.next();

                                        String query = crud_type + " " + column_names.substring(0, column_names.length() - 1) + " from " + table_name + " where " + condition;

                                        resultSet = statement.executeQuery(query);

                                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                                        while (resultSet.next())
                                        {
                                            Map<String, Object> row = new HashMap<>();

                                            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                                            {
                                                row.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                                            }

                                            System.out.println(row);

                                            rows.add(row);
                                        }

                                        ObjectMapper mapper = new ObjectMapper();

                                        String json = mapper.writeValueAsString(rows);

                                        File file = new File("/root/IdeaProjects/JDBC/src/main/resources/Query_Result.json");

                                        mapper.writeValue(file, rows);

                                        break;
                                    }
                                }

                            }

                            else
                            {
                                System.out.println("Please enter Valid Input");
                            }

                        }

                        else
                        {
                            System.out.println("Please Enter Valid Name");
                        }
                    }

                    case 2:
                    {
                        crud_type = "insert";

                        show_Table(metaData);

                        System.out.println("Please enter Your table name");

                        String table = input.next();

                        if(table != null)
                        {
                            table_name = table;

                            show_Columns(metaData, table);

                            System.out.println("Please enter Columns number to see OR enter * for all columns");

                            String columns = input.next();

                            if(columns != null)
                            {

                            }

                            else
                            {
                                System.out.println("Please enter Valid Input");
                            }
                        }

                        else
                        {
                            System.out.println("Please Enter Valid Name");
                        }
                    }
                }
            }
        }

        catch (Exception exception)
        {
            System.out.println("Please Enter Valid Number");
        }
    }
}
