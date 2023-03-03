package ComparabelAndComparator;

import java.util.*;

public class ComparatorExample {

    public static void main(String args[])
    {
        try
        {
            ArrayList<Student1> al=new ArrayList<Student1>();

            al.add(new Student1(101,"Vijay",23));

            al.add(new Student1(106,"Ajay",27));

            al.add(new Student1(105,"Jai",21));

            Comparator<Student1> cm1=Comparator.comparing(Student1::getName);

            Collections.sort(al,cm1);

            System.out.println("Sorting by Name");

            for(Student1 st: al)
            {
                System.out.println(st.rollno+" "+st.name+" "+st.age);
            }

            Comparator<Student1> cm2=Comparator.comparing(Student1::getAge);

            Collections.sort(al,cm2);

            System.out.println("Sorting by Age");

            for(Student1 st: al)
            {
                System.out.println(st.rollno+" "+st.name+" "+st.age);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
