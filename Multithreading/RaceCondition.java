package org.example;

public class RaceCondition {

    private int sum1 = 0;

    private int sum2 = 0;

    public void add(int value1, int value2)
    {
        synchronized (this)
        {
            this.sum1 += value1;

            this.sum2 += value2;
        }
    }
}
