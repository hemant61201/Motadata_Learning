package org.example;

public class ObjectMemberVariableMyRunnable implements Runnable
{
    ObjectMemberVariable instance = null;

    public ObjectMemberVariableMyRunnable(ObjectMemberVariable instance)
    {
        this.instance = instance;
    }


    @Override
    public void run()
    {
        this.instance.add("Some Text");
    }
}
