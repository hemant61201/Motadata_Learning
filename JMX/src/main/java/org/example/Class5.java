package org.example;

import java.util.HashMap;

public class Class5 extends Thread
{
    @Override
    public void run()
    {
        HashMap<String,String> class5HashMap = new HashMap<>();

        String s1 = "Hi";

        String s2 = "Hello";

        while (true)
        {
            class5HashMap.put(s1,s2);
        }
    }
}
