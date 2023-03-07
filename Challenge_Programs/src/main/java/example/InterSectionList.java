package example;

import java.util.Collections;
import java.util.LinkedList;

public class InterSectionList {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<Integer> linklist1 = new LinkedList<>();

            LinkedList<Integer> linklist2= new LinkedList<>();

            linklist1.add(4);

            linklist1.add(1);

            linklist1.add(8);

            linklist1.add(4);

            linklist1.add(5);

            linklist2.add(5);

            linklist2.add(6);

            linklist2.add(1);

            linklist2.add(8);

            linklist2.add(4);

            linklist2.add(5);

            for(int index =0; index < linklist1.size(); index++)
            {
                //System.out.println(ll.subList(i,ll.size()));

                if(Collections.indexOfSubList(linklist2,linklist1.subList(index,linklist1.size())) != -1)
                {
                    System.out.println(linklist1.get(index));

                    break;
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }


    }
}
