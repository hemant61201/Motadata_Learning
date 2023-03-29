package org.example;

import java.util.HashMap;

public class Class6 extends Thread
{
    @Override
    public void run()
    {
        HashMap<String,String> class6HashMap = new HashMap<>();

        String s1 = "Hi";

        String s2 = "Hello";

        while (true)
        {
            class6HashMap.put(s1,s2);
        }
    }
}
