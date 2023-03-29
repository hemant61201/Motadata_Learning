package org.example;

public class MainClass
{
    public static void main(String[] args)
    {
        Thread thread1 = new Thread(new Class1());

        Thread thread2 = new Thread(new Class2());

        Thread thread3 = new Thread(new Class3());

        Thread thread4 = new Thread(new Class4());

        Thread thread5 = new Thread(new Class5());

        Thread thread6 = new Thread(new Class6());

        thread1.start();

        thread2.start();

        thread3.start();

        thread4.start();

        thread5.start();

        thread6.start();
    }
}
