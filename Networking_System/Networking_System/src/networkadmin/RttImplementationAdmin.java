package networkadmin;

import java.io.*;

public interface RttImplementationAdmin {

    default void rttImplementation(String ip)
    {
        try
        {
            String rttcmd = "ping -c 3 " + ip + " | grep 'rtt'";

            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    rttcmd
            };

            Process p = Runtime.getRuntime().exec(cmd);

            try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String rtt = inputStream.readLine();

                System.out.println(rtt);

                System.out.println("1. Min value of rtt");

                System.out.println("2. avg value of rtt");

                System.out.println("3. Max value of rtt");

                System.out.println("4. mdev value of rtt");

                try(BufferedReader number = new BufferedReader(new InputStreamReader(System.in)))
                {
                    while (true)
                    {
                        System.out.println("Enter your Choice");

                        Integer choice = Integer.parseInt(number.readLine());

                        try
                        {
                            if (choice != null)
                            {
                                switch (choice)
                                {
                                    case 1:
                                    {
                                        String minval = "ping -c 3 "  + ip + " | grep \"rtt min/avg/max/mdev =\" " +
                                                " | awk -F \"=\" '{print$2}' " + " | awk -F \"/\" '{print$1}'";

                                        String[] min = {
                                                "/bin/sh",
                                                "-c",
                                                minval
                                        };

                                        Process p1 = Runtime.getRuntime().exec(min);

                                        try (BufferedReader input = new BufferedReader(new InputStreamReader(p1.getInputStream())))
                                        {
                                            String rttmin = input.readLine();

                                            System.out.println("Min :" + rttmin);
                                        }

                                        catch (Exception exception)
                                        {
                                            exception.printStackTrace();
                                        }
                                        break;
                                    }

                                    case 2:
                                    {
                                        String avgval = rtt + " | grep \"rtt min/avg/max/mdev =\" " +
                                                " | awk -F \"=\" '{print$2}' " + " | awk -F \"/\" '{print$2}'";

                                        String[] avg = {
                                                "/bin/sh",
                                                "-c",
                                                avgval
                                        };

                                        Process p2 = Runtime.getRuntime().exec(avg);

                                        try (BufferedReader input = new BufferedReader(new InputStreamReader(p2.getInputStream())))
                                        {
                                            String rttavg = input.readLine();

                                            System.out.println("Avg :" + rttavg);
                                        }

                                        catch (Exception exception)
                                        {
                                            exception.printStackTrace();
                                        }
                                        break;
                                    }

                                    case 3:
                                    {
                                        String maxval = rtt + " | grep \"rtt min/avg/max/mdev =\" " +
                                                " | awk -F \"=\" '{print$2}' " + " | awk -F \"/\" '{print$3}'";

                                        String[] max = {
                                                "/bin/sh",
                                                "-c",
                                                maxval
                                        };

                                        Process p3 = Runtime.getRuntime().exec(max);

                                        try (BufferedReader input = new BufferedReader(new InputStreamReader(p3.getInputStream())))
                                        {
                                            String rttmax = input.readLine();

                                            System.out.println("Max :" + rttmax);
                                        }

                                        catch (Exception exception)
                                        {
                                            exception.printStackTrace();
                                        }
                                        break;
                                    }

                                    case 4:
                                    {
                                        String mdev = rtt + " | grep \"rtt min/avg/max/mdev =\" " +
                                                " | awk -F \"=\" '{print$2}' " + " | awk -F \"/\" '{print$4}'";

                                        String[] dev = {
                                                "/bin/sh",
                                                "-c",
                                                mdev
                                        };

                                        Process p4 = Runtime.getRuntime().exec(dev);

                                        try (BufferedReader input = new BufferedReader(new InputStreamReader(p4.getInputStream())))
                                        {
                                            String rttmdev = input.readLine();

                                            System.out.println("Mdev :" + rttmdev);
                                        }

                                        catch (Exception exception)
                                        {
                                            exception.printStackTrace();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        catch (Exception exception)
                        {
                            System.out.println("Enter Valid Input");

                            rttImplementation(ip);

                            break;
                        }
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

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
