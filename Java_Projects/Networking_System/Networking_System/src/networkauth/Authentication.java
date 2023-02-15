package networkauth;

import java.util.Arrays;

import java.io.*;

public class Authentication implements UserVerification, AdminVerification{

    static String user;

    private static String password;

    public static void main(String[] args)
    {

        System.out.println("Welcome To Authentication Page");

        System.out.println("1. Authenticate as User");

        System.out.println("2. Authenticate as Admin");

        System.out.println("Please enter Valid Input No.");

        try
        {

            while (true)
            {
                BufferedReader number = new BufferedReader(new InputStreamReader(System.in));

                Integer choice = Integer.parseInt(number.readLine());

                try
                {
                    if (choice != null)
                    {
                        if (choice == 1)
                        {
                            user = "User";

                            UserVerification userObj = new Authentication();

                            userObj.UserFacility(user);

                        }
                        else if (choice == 2)
                        {
                            System.out.println("Please enter Password to use AdminSection");

                            password = number.readLine();

                            user = "Admin";

                            AdminVerification adminObj = new Authentication();

                            adminObj.AdminFacility(user, password);
                        }
                        else
                        {
                            System.out.println("Chose 1 or 2 Only");
                        }
                    }
                    else
                    {
                        System.out.println("Enter correct choice");
                    }

                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }

        catch (ClassCastException e)
        {
            System.out.println("Please enter Valid Input");
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}