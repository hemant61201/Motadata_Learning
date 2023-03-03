package Collection_Interface_Methods.Iterable_Interface_ForEachMethod;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
public class ForEachMethodAnnonymousClass {

    public static void main(String[] args)
    {
        List<String> data = new ArrayList<>();

        data.add("New Delhi");

        data.add("New York");

        data.add("Mumbai");

        data.add("London");

        data.forEach(new Consumer<String>() {

            @Override
            public void accept(String t)
            {

                System.out.println(t);
            }

        });
    }
}
