package org.example;

public class RaceConditionSplitCriticalSection {

    private int sum1 = 0;

    private int sum2 = 0;

    private Integer sumlocak1 = new Integer(1);

    private Integer sumlocak2 = new Integer(2);

    public void add(int value1, int value2)
    {
        synchronized (this.sumlocak1)
        {
            this.sum1 += value1;
        }

        synchronized (this.sumlocak2)
        {
            this.sum2 += value2;
        }
    }
}
