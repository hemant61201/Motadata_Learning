import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class PersonsInfo extends ActionSupport
{
    private String First_name;
    private String Last_name;

    private String insert_result;

    private int age;


    public String getInsert_result() {
        return insert_result;
    }

    public void setInsert_result(String insert_result) {
        this.insert_result = insert_result;
    }

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


    public String insertDb()
    {
        try(Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa",""))
        {
            if (connection != null)
            {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into person_info values(?,?,?)");

                preparedStatement.setString(1,First_name);

                preparedStatement.setString(2,Last_name);

                preparedStatement.setInt(3,age);

                preparedStatement.execute();

                System.out.println("Records inserted Successfully");

                insert_result = "Records inserted Successfully";

                return "SUCCESS";
            }

            else
            {
                System.out.println("Connection NOT Received");

                insert_result = "Records Not inserted Successfully";
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    public String getDb()
    {
        try(Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa",""))
        {
            if (connection  != null)
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

            else
            {
                System.out.println("Connection NOT Received");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }
}
