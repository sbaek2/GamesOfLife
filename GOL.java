/*
    CS101 Project1 FA20 Game Of Life
    Name: Sangwon Baek
    Date: October 27th, 2020
*/

public class GOL 
{
    public static void init(boolean[][] alive, double alivePerc)
    {
        //assume the grid is squared
        int n = alive.length; //len(alive)
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                alive[i][j] = Math.random() < alivePerc;
            }
        }
    }

    public static void print(boolean[][] alive)
    {
        char aliveChar = (char) 0x2B1B;
        //assume the grid is squared
        int n = alive.length;
        
        //Printing the results
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                if(alive[i][j])
                    System.out.print(aliveChar);
                else
                    System.out.print("  ");
            }
            System.out.println("");
        }
    }

    public static boolean isAlive(boolean[][] alive, int i, int j)
    {
        int n = alive.length;
        int x = (i+n) % n;
        int y = (j+n) % n;
        //returning the coordinate of the spot where the life is alive.
        return alive[x][y];
    }
    
    public static int [][] getNeighs(int i, int j)
    {
        //Establishing a 2d array neighs with 8*2
        int [][]neighs= new int[8][2];
        int count=0;
        
        //assigning values to the array with excluding the input (i,j) coordinate
        for (int x=i-1; x<=i+1; x++)
        {
            for (int y=j-1; y<=j+1; y++)
            {
                if (count<neighs.length&&x!=i||y!=j)
                {
                    neighs[count][0]=x;
                    neighs[count][1]=y;
                    count++;
                }
            }
        }
        return neighs;
    }
    
    public static int countAliveNeighs(boolean[][] alive, int i, int j)
    {
        int nAlive= 0; 
        int[][] coordinate=getNeighs(i,j);
        //Counting alive neighbors by going through coordinates gained from getNeighs() method.
        for (int x=0; x<coordinate.length; x++)
        {
                int n=coordinate[x][0];
                int m=coordinate[x][1];
                if (isAlive(alive,n,m))
                    nAlive++;
        }
        return nAlive;
    }
    
    public static void update(boolean[][] alive, int[] born, int[] surviving)
    {
        //assume the grid is squared
        int n = alive.length; //len(alive)
        boolean[][] newAlive = new boolean[n][n];
        for(int i = 0; i < n; i++)
        {
           for(int j = 0; j < n; j++)
           {
                //Number of all alive neighbors
                int nAlive = countAliveNeighs(alive, i, j);
                if(isAlive(alive, i, j))
                {
                    boolean checker = false;
                    for (int x=0; x<surviving.length; x++)
                    {
                        if (nAlive == surviving[x])
                        {
                            newAlive[i][j] = true;
                            checker = true;
                        }
                    }
                    if (checker == false)
                        newAlive[i][j] = false;
                }
                else
                {
                    boolean checker = false;
                    for (int k=0; k<born.length; k++)
                    {
                        if (nAlive==born[k])
                        {
                            newAlive[i][j] = true;
                            checker = true;
                        }
                    }
                    if (checker==false)
                        newAlive[i][j] = false;
                }
           }
        }

        for(int i = 0; i < n; ++i)
        {
           for(int j = 0; j < n; ++j)
           {
               alive[i][j] = newAlive[i][j];
           }
        }
    }

    public static void main(String[] args) 
    {
        //Taking args[0] as a integer value
        int n = Integer.parseInt(args[0]);
        boolean[][] alive = new boolean[n][n];
        String a = args[1];
        //Spliting the string: for instance, B2/S2 into B2,S2 
        String [] b = a.split("/");
        String c = b[0];
        String d = b[1];
        
        //Establishing a born int array
        int [] born = new int [c.length()-1];
        //Assigning int values to a born array.
        for (int j=0; j<born.length; j++)
        {
            born[j]= Integer.parseInt(c.substring(j+1,j+2));
        }
        
        //Establishing a surviving int array
        int [] surviving = new int [d.length()-1];
        //Assigning int values to a surviving array.
        for (int i=0; i<surviving.length; i++)
        {
            surviving[i] = Integer.parseInt(d.substring(i+1,i+2));
        }
        init(alive, 0.2);

        while(true)
        {
            //printing and updating the game
            print(alive);
            update(alive, born, surviving);

            try 
            {
                Thread.sleep(200);
            } 
            catch(InterruptedException e)
            {
                // this part is executed when an exception (in this example InterruptedException) occurs
            }					
            System.out.flush();
        }
    }
}