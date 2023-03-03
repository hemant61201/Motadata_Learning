package Map;

import java.util.EnumMap;

public class EnumMapExample {

    public enum GFG
    {
        CODE,
        CONTRIBUTE,
        QUIZ,
        MCQ;
    }

    public static void main(String args[])
    {
        try
        {
            EnumMap<GFG, String> gfgMap = new EnumMap<GFG, String>(GFG.class);

            gfgMap.put(GFG.CODE, "Start Coding with gfg");

            gfgMap.put(GFG.CONTRIBUTE, "Contribute for others");

            gfgMap.put(GFG.QUIZ, "Practice Quizes");

            gfgMap.put(GFG.MCQ, "Test Speed with Mcqs");

            System.out.println("Size of EnumMap in java: " + gfgMap.size());

            System.out.println("EnumMap: " + gfgMap);

            System.out.println("Key : " + GFG.CODE + " Value: " + gfgMap.get(GFG.CODE));

            System.out.println("Does gfgMap has " + GFG.CONTRIBUTE + ": " + gfgMap.containsKey(GFG.CONTRIBUTE));

            System.out.println("Does gfgMap has :" + GFG.QUIZ + " : " + gfgMap.containsValue("Practice Quizes"));

            System.out.println("Does gfgMap has :" + GFG.QUIZ + " : " + gfgMap.containsValue(null));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}

