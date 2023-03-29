package org.example;

import java.util.HashMap;

public class Class1 extends Thread
{
    @Override
    public void run()
    {
        HashMap<String,String> class1HashMap = new HashMap<>();

        String s1 = "Hi";

        String s2 = "Hello";

        while (true)
        {
            class1HashMap.put(s1,s2);
        }
    }
}
