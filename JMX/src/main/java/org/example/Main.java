package org.example;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args)  {

        try{
            ObjectName objname =  new ObjectName("org.example:type=ddd,name=demo");

            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            mBeanServer.registerMBean(new Demo() , objname);


        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());
        }


        while(true)
        {

        }
    }
}