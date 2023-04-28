import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class Person_Info extends ActionSupport
{

    private String First_name;
    private String Last_name;

    private int age;

//    private String result;
//
//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }


    public HashMap<Integer, Object> getResult() {
        return result;
    }

    public void setResult(HashMap<Integer, Object> result) {
        this.result = result;
    }

    private HashMap<Integer, Object> result = new HashMap<>();

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        this.Last_name = last_name;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        this.First_name = first_name;
    }


    public void db_Info()
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
            PreparedStatement preparedStatement = connection.prepareStatement("insert into person_info values(?,?,?)");

            preparedStatement.setString(1,First_name);

            preparedStatement.setString(2,Last_name);

            preparedStatement.setInt(3,age);

            preparedStatement.execute();

            System.out.println("Records inserted Successfully");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public String db_get()
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person_info");

            ResultSet resultSet = preparedStatement.executeQuery();

            String output = null;

            int count = 0;

            while (resultSet.next())
            {
                output = resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getInt(3);

                result.put(++count, output);
            }

            return "SUCCESS";
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }
}
