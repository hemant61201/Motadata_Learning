package org.example;

import java.util.HashMap;

public class Class2 extends Thread
{
    @Override
    public void run()
    {
        HashMap<String,Integer> class2HashMap = new HashMap<>();

        String s1 = "Hi";

        int s2 = 3;

        while (true)
        {
            class2HashMap.put(s1,s2);
        }
    }
}
