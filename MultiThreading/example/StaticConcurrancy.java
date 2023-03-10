package org.example;

public class StaticConcurrancy  extends Thread{

    public static void show()
    {
        System.out.println("Running " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        StaticConcurrancy staticConcurrancy = new StaticConcurrancy();

        StaticConcurrancy staticConcurrancy1 = new StaticConcurrancy();

        staticConcurrancy.start();

        staticConcurrancy1.start();

        staticConcurrancy.show();

        staticConcurrancy1.show();
    }
}
