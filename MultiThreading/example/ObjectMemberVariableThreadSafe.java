package org.example;

public class ObjectMemberVariableThreadSafe {

    public static void main(String[] args)
    {
        try
        {
            new Thread(new ObjectMemberVariableMyRunnable(new ObjectMemberVariable())).start();

            new Thread(new ObjectMemberVariableMyRunnable(new ObjectMemberVariable())).start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
