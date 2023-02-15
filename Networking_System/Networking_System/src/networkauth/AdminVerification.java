package networkauth;

import networkadmin.PingImplementationAdmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

interface AdminVerification extends PingImplementationAdmin{

    String password = "Hem@nt#06";

    default void AdminFacility(String user, String passwd) {

        try
        {
            if(user == "Admin" && passwd == password)
            {
                System.out.println("Welcome to AdminBasedApp");

                System.out.println("List of All Admin Facilities");

                System.out.println("1. Check IP Address And Provide IP Address");

                System.out.println("2. Ping Implementation");

                System.out.println("3. Traceroute Implementation");

                System.out.println("4. FileTransfer Implementation");

                System.out.println("5. MailTransfer Implementation");

                System.out.println("6. SNMP Implementation");

                System.out.println("7. SSH Implementation");
            }

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
                            switch (choice)
                            {
                                case 1:
                                {
                                    break;
                                }

                                case 2:
                                {
                                    break;
                                }

                                case 3:
                                {
                                    break;
                                }

                                case 4:
                                {
                                    break;
                                }

                                case 5:
                                {
                                    break;
                                }

                                case 6:
                                {
                                    break;
                                }

                                case 7:
                                {
                                    break;
                                }

                                default:
                                {
                                    System.out.println("Enter Valid Choice");
                                    break;
                                }
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

            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        catch (NullPointerException e)
        {
            System.out.println("Not a User");
        }
    }
}
