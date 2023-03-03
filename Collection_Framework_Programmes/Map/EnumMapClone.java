package Map;

import java.util.*;

enum Price_of_Fruits {
    Orange,
    Apple,
    Banana,
    Pomegranate,
    Guava
};

public class EnumMapClone {
    public static void main(String[] args)
    {
        try
        {
            EnumMap<Price_of_Fruits, Integer> mp1 = new EnumMap<Price_of_Fruits, Integer>(Price_of_Fruits.class);

            EnumMap<Price_of_Fruits, Integer> mp2 = new EnumMap<Price_of_Fruits, Integer>(Price_of_Fruits.class);

            mp1.put(Price_of_Fruits.Orange, 30);

            mp1.put(Price_of_Fruits.Apple, 60);

            mp1.put(Price_of_Fruits.Banana, 40);

            mp1.put(Price_of_Fruits.Apple, 80);

            mp1.put(Price_of_Fruits.Pomegranate, 120);

            mp1.put(Price_of_Fruits.Guava, 20);

            System.out.println("Price of fruits in 1st map " + mp1);

            mp2 = mp1.clone();

            mp2.put(Price_of_Fruits.Apple, 80);

            System.out.println("Price of fruits in 2nd map " + mp2);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}

