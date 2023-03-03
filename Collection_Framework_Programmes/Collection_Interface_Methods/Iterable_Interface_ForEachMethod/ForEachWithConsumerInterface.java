package Collection_Interface_Methods.Iterable_Interface_ForEachMethod;

import java.util.*;
import java.util.function.Consumer;
class CityConsumer implements Consumer<String> {

    @Override
    public void accept(String t)
    {
        System.out.println(t);
    }
}
public class ForEachWithConsumerInterface {

    public static void main(String[] args)
    {
        List<String> data = new ArrayList<>();

        data.add("New Delhi");

        data.add("New York");

        data.add("Mumbai");

        data.add("London");

        CityConsumer cityConsumer = new CityConsumer();

        data.forEach(cityConsumer);
    }
}
