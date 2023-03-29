package org.example;

import java.util.HashMap;

public class Class4 extends Thread
{
    @Override
    public void run()
    {
        HashMap<Integer,String> class4HashMap = new HashMap<>();

        int s1 = 1;

        String s2 = "Hello";

        while (true)
        {
            class4HashMap.put(s1,s2);
        }
    }
}
