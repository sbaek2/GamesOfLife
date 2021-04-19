/*
    CS101 Project1 FA20 Game Of Life
    Name: Sangwon Baek
    Date: October 27th, 2020
*/

public class GOLRule
{
    public static void main(String[] args)
    {
        if (args.length<=2)
            GOL.main(args);
        else if (args[2].equals("hex"))
            GOLHEX.main(args);
    }
}
