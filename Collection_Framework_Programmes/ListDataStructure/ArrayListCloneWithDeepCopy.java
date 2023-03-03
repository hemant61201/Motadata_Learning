package ListDataStructure;

class Test1 {
    int x, y;
}

class Test3 implements Cloneable {
    int a, b;

    Test1 c = new Test1();

    public Object clone() throws CloneNotSupportedException
    {
        Test3 t = (Test3)super.clone();

        t.c = new Test1();

        t.c.x = c.x;

        t.c.y = c.y;

        return t;
    }
}

public class ArrayListCloneWithDeepCopy
{
    public static void main(String args[]) throws CloneNotSupportedException
    {
        Test3 t1 = new Test3();

        t1.a = 10;

        t1.b = 20;

        t1.c.x = 30;

        t1.c.y = 40;

        Test3 t3 = (Test3)t1.clone();

        t3.a = 100;

        t3.c.x = 300;

        System.out.println(t1.a + " " + t1.b + " " + t1.c.x + " " + t1.c.y);

        System.out.println(t3.a + " " + t3.b + " " + t3.c.x + " " + t3.c.y);

        System.out.println(t1.hashCode());

        System.out.println(t3.hashCode());
    }
}

