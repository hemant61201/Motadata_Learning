package org.example;

public class Demo implements DemoMBean
{


    private  String Name = "Demo";

    private String player = "default";


    public String getName()
    {
        System.out.println(Name);
        return Name;
    }

    @Override
    public String getPlayer() {
        System.out.println("Player Set");
        return player;
    }

    public void PrintName()
    {
        System.out.println(Name);

    }


    public void setName(String name)
    {
        System.out.println("New Name" + name);
        this.Name = name;
    }

    @Override
    public void setPlayer(String args) {
        System.out.println("New player");
        this.player = args;
    }

    public void Play()
    {

        System.out.println("played");
    }




}
