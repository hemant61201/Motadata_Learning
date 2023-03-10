package org.example;

public class ObjectMemberVariable {

    StringBuilder stringBuilder = new StringBuilder();

    public void add(String string)
    {
        this.stringBuilder.append(string);

        System.out.println(stringBuilder);
    }
}
