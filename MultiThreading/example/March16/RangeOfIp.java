package org.example.March16;

import org.apache.commons.net.util.SubnetUtils;

import java.util.Scanner;

public class RangeOfIp
{
    public String[] rangeIp()
    {
        Scanner input = new Scanner(System.in);

        String ipAddress = input.nextLine();

        SubnetUtils subnetUtils = new SubnetUtils(ipAddress);

        String[] allAddresses = subnetUtils.getInfo().getAllAddresses();

        for (String ip : allAddresses)
            System.out.println(ip);

        return allAddresses;
    }

}
