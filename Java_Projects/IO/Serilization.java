public class Serilization {

    public static class SerializableDemo implements Serializable {
        //static final long serialVersionUID = 8882416210786165012L;
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        private transient int id = 4;
        public int getId() { return id; }

        //private String gender;
    }
    public void doSerialization() {
        System.out.println("\nInside doSerialization ...");

        SerializableDemo serializableDemo = new SerializableDemo();
        serializableDemo.setName("Java");
        System.out.println("name (before serialization): " + serializableDemo.getName());
        System.out.println("id (before serialization): " + serializableDemo.getId());

        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("serial.ser")))) {
            out.writeObject(serializableDemo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doDeserialization() {
        System.out.println("\nInside doDeserialization ...");

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("serial.ser")))) {
            SerializableDemo serializedObj = (SerializableDemo) in.readObject();
            System.out.println("name (after deserialization): " + serializedObj.getName());
            System.out.println("id (after deserialization): " + serializedObj.getId());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        if (args.length > 0 && args[0].equals("true")) {
            new IODemo().doSerialization();
        }
        new IODemo().doDeserialization();
    }
}
