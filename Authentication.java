package networkauth;

import java.io.*;

public class Authentication extends AdminVerification implements UserVerification {

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
                try(BufferedReader number = new BufferedReader(new InputStreamReader(System.in)))
                {
                    Integer choice = Integer.parseInt(number.readLine());

                    try
                    {
                        if (choice != null)
                        {
                            if (choice == 1)
                            {
                                user = "User";

                                try
                                {
                                    UserVerification userObj = new Authentication();

                                    if (userObj != null)
                                    {
                                        userObj.UserFacility(user);
                                    }
                                    else
                                    {
                                        throw new NullPointerException();
                                    }
                                }

                                catch (NullPointerException exception)
                                {
                                    exception.printStackTrace();
                                }

                            }
                            else if (choice == 2)
                            {
                                user = "Admin";

                                System.out.println("Please enter Password to use AdminSection");

                                password = number.readLine();

                                try
                                {
                                    AdminVerification adminObj = new Authentication();

                                    if (adminObj != null)
                                    {
                                        adminObj.AdminFacility(user, password);
                                    }
                                    else
                                    {
                                        throw new NullPointerException();
                                    }
                                }
                                catch (NullPointerException exception)
                                {
                                    exception.printStackTrace();
                                }
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

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }

        catch (Exception exception)
        {
            System.out.println("Please enter Valid Input");
        }
    }
}