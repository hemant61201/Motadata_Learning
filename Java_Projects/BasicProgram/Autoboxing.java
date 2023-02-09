public class Autoboxing {
}
	static void veryExpensive() {
		  System.out.println("\nInside veryExpensive ...");
		  Long sum = 0L;
		  for (long i = 0; i < Integer.MAX_VALUE; i++) {
			  sum = sum + i;
		  }
		}

	static void compareBoxPrimitives() {
	  System.out.println("\nInside compareBoxPrimitives ...");
      Integer num1 = new Integer(0);
	  Integer num2 = new Integer(0);
	  System.out.println("(num1 == num2): " + (num1 == num2));

	  // Solutions:
	  System.out.println("(num1.intValue() == num2.intValue()): " + (num1.intValue() == num2.intValue()));
	  System.out.println("(num1.equals(num2)): " + (num1.equals(num2)));

	  Integer num3 = new Integer(1);
	  System.out.println("(num1 < num3): " + (num1 < num3)); // "<" does un-boxing first
    }

	static Integer i;
	static void unbelievable() {
	  System.out.println("\nInside unbelievable ...");
      if (i == 0)
         System.out.println("weird!");
    }


	public static void main(String[] args){
		compareBoxPrimitives();
		long start = System.nanoTime();
		veryExpensive();
		System.out.println("Elapsed time: " + ((System.nanoTime() - start) / 1000000.0) + " msec");
		unbelievable();
	}
}
