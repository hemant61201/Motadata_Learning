import com.opensymphony.xwork2.ActionSupport;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PersonsInfo extends ActionSupport
{
    private String firstname;
    private String lastname;
    private String insertresult;

    public ArrayList<ArrayList<Object>> getResult() {
        return result;
    }

    private int age;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInsertresult() {
        return insertresult;
    }

    public void setInsertresult(String insertresult) {
        this.insertresult = insertresult;
    }
    public void setResult(ArrayList<ArrayList<Object>> result) {
        this.result = result;
    }

    private ArrayList<ArrayList<Object>> result = new ArrayList<>();

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String insertDb()
    {
        try(Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa",""))
        {
            if (connection != null)
            {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into person_info values(?,?,?)");

                preparedStatement.setString(1,firstname);

                preparedStatement.setString(2,lastname);

                preparedStatement.setInt(3,age);

                int insertedcount = preparedStatement.executeUpdate();

                if(insertedcount == 1)
                {
                    System.out.println("Records inserted Successfully");

                    insertresult = "Records inserted Successfully";
                }

                else
                {
                    System.out.println("Connection NOT Received");

                    insertresult = "Records Not inserted Successfully";
                }

                return "SUCCESS";
            }

            else
            {
                System.out.println("Connection NOT Received");

                insertresult = "Records Not inserted Successfully";

                return "SUCCESS";
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
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from person_info");

                result.clear();

                while (resultSet.next())
                {
                    ArrayList<Object> output = new ArrayList<>();

                    output.add(resultSet.getString(1));

                    output.add(resultSet.getString(2));

                    output.add(resultSet.getInt(3));

                    result.add(output);

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
