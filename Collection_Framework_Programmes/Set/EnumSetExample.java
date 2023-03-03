package Set;

import java.util.EnumSet;

public class EnumSetExample {

    enum Game { CRICKET, HOCKEY, TENNIS }

    public static void main(String[] args)
    {
        try
        {
            EnumSet<Game> games1 = EnumSet.allOf(Game.class);

            EnumSet<Game> games2 = EnumSet.noneOf(Game.class);

            games2.add(Game.HOCKEY);

            System.out.println("EnumSet Using add(): " + games2);

            games2.addAll(games1);

            System.out.println("EnumSet Using addAll(): " + games2);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
