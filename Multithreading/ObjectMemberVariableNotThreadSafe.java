package org.example;

public class ObjectMemberVariableNotThreadSafe {

    public static void main(String[] args)
    {
        try
        {
            ObjectMemberVariable sharedInstance = new ObjectMemberVariable();

            new Thread(new ObjectMemberVariableMyRunnable(sharedInstance)).start();

            new Thread(new ObjectMemberVariableMyRunnable(sharedInstance)).start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
