package networkauth;

import networkuser.PingImplementationUser;

import networkuser.WebBrowser;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

interface UserVerification extends PingImplementationUser, WebBrowser {

    default void UserFacility(String user) {

        String website;

        try
        {
            if(user == "User")
            {
                System.out.println("Welcome to UserBasedApp");

                System.out.println("List of All User Facilities");

                System.out.println("1. File Transfer");

                System.out.println("2. Mail Transfer");

                System.out.println("3. SNMP Features");

                System.out.println("4. Open Web in Chrome");
            }

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
                                            break;
                                        }

                                        case 3: {
                                            break;
                                        }

                                        case 4:
                                        {
                                            try
                                            {
                                                System.out.println("Please Enter WebSiteName");

                                                website = number.readLine();

                                                if (website != null) {
                                                    String result = sendPingRequest(website);

                                                    if (result == "Host is reachable") {
                                                        System.out.println(result);

                                                        OpenWeb(website, result);
                                                    } else {
                                                        System.out.println(result);

                                                        break;
                                                    }
                                                } else {
                                                    throw new NullPointerException();
                                                }
                                            }

                                            catch (NullPointerException exception)
                                            {
                                                System.out.println("Enter Valid WebsiteName");

                                                break;
                                            }
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

        catch (NullPointerException e)
        {
            System.out.println("Not a User");
        }
    }
}
