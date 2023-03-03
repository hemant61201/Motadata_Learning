package ListDataStructure;

import java.util.*;
public class ArraytoList {

    public static void main(String[] args)
    {
        String[] array={"Java","Python","PHP","C++"};

        System.out.println("Printing Array: "+Arrays.toString(array));

        List<String> list=new ArrayList<String>();

        for(String lang:array)
        {
            list.add(lang);
        }

        System.out.println("Printing List: "+list);
    }

}

