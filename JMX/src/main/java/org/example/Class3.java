package org.example;

import java.util.HashMap;

public class Class3 extends Thread
{
    @Override
    public void run()
    {
        HashMap<String,String> class3HashMap = new HashMap<>();

        String s1 = "Hi";

        String s2 = "Hello";

        while (true)
        {
            class3HashMap.put(s1,s2);
        }
    }
}
