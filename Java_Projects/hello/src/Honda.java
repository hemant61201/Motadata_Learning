// Concrete class
public class Honda extends Car {
    // Hiding implementation details
    @Override
    public void stop() {
        System.out.println("Honda::Stop");
        System.out.println(
                "Mechanism to stop the car using break");
    }
}
