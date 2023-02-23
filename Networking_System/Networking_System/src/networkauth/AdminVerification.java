package networkauth;

import networkadmin.PingImplementationAdmin;
import networkadmin.SshImplementationAdmin;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class AdminVerification implements PingImplementationAdmin, SshImplementationAdmin {

    String password = "Hem@nt#06";

    void AdminFacility(String user, String passwd) {

        String ipaddress;

        try
        {
            if(user.equals("Admin") && passwd.equals(password))
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
                                    switch (choice)
                                    {
                                        case 1: {
                                            break;
                                        }

                                        case 2: {
                                            try {
                                                System.out.println("Please Enter IPAddress");

                                                ipaddress = number.readLine();

                                                if (ipaddress != null) {
                                                    PingRequest(ipaddress);
                                                } else {
                                                    throw new NullPointerException();
                                                }
                                            } catch (NullPointerException exception) {
                                                System.out.println("Enter Valid WebsiteName");

                                                break;
                                            }
                                        }

                                        case 3: {
                                            break;
                                        }

                                        case 4: {
                                            break;
                                        }

                                        case 5: {
                                            break;
                                        }

                                        case 6: {
                                            break;
                                        }

                                        case 7: {
                                            sshImplemntation();
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

                                break;
                            }
                        }

                        catch (Exception exception)
                        {
                            exception.printStackTrace();

                            break;
                        }
                    }
                }

                catch (Exception exception)
                {
                    System.out.println("Please enter Valid Input");
                }
            }

            else
            {
                System.out.println("Please Enter Correct Password");

                try(BufferedReader number = new BufferedReader(new InputStreamReader(System.in)))
                {
                    String password = number.readLine();

                    AdminFacility("Admin", password);
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }

        catch (NullPointerException e)
        {
            System.out.println("Not a User");
        }
    }
}
