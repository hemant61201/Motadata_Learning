package ListDataStructure;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
public class CopyOnWriteArrayListIterator {

        public static void main(String[] args)
        {
            try
            {
                CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

                Iterator itr = list.iterator();

                list.add("Hemant");

                System.out.println("List contains: ");

                while (itr.hasNext())
                    System.out.println(itr.next());

                itr = list.iterator();

                System.out.println("List contains:");

                while (itr.hasNext())
                    System.out.println(itr.next());
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }
}

