public class StaticInitialiser {
    public StaticInitialiser() {
        System.out.println("Inside no-arg constructor ...");
    }

    public StaticInitialiser(int id) {
        System.out.println("Inside constructor with a parameter ...");
    }

    {
        System.out.println("Inside instance initializer ...");

    }

    static {
        System.out.println("Inside Static initializer");
    }

    public static void main(String[] args){
        StaticInitialiser s1 = new StaticInitialiser();
    }
}
