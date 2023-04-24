package Paging;

import java.util.Scanner;
import java.util.ArrayList;

public class LRU {

    public int lru(int[] time, int n)
    {
        //int i;
        int min = time[0];
        int loc = 0;

        for(int i = 1; i < n; ++i)
        {
            if(time[i] < min)
            {
                min = time[i];
                loc = i;
            }
        }

        return loc;
    }


    public static void main(String[] args)
    {

        Scanner keyboard = new Scanner(System.in);

        System.out.println("LRU Page Replacement Algorithm Simulation");
        System.out.println("------------------------------------------------------------------------------------------");

        int numFrames, pointer = 0, numHits = 0, numFaults = 0, refStringLength;
        Boolean isFull = false;
        int buffer[];
        ArrayList<Integer> stack = new ArrayList<Integer>();
        int referenceString[];
        int memoryLayout[][];
        
        System.out.print("Please enter the number of Frames: ");
        numFrames = keyboard.nextInt();
        
        System.out.println("Please enter the reference string: ");
        
        //whole string starts
        String ref = keyboard.next();
        refStringLength = ref.length();
        
        while(ref.length() != refStringLength)
        {
            System.out.println("Length of this reference string must be " + refStringLength + " characters long!");
            System.out.println("Please enter the reference string: ");
            ref = keyboard.next();
        }
        
        referenceString = new int[refStringLength];
        memoryLayout = new int[refStringLength][numFrames];
        buffer = new int[numFrames];
        for(int j = 0; j < numFrames; j++)
        {
                buffer[j] = -1;
        }
        for(int i=0; i < refStringLength; i++)
        {
            referenceString[i] = Integer.parseInt(String.valueOf(ref.charAt(i)));
        }
        //whole string ends

        System.out.println();
        for(int i = 0; i < refStringLength; i++)
        {
            if(stack.contains(referenceString[i]))
            {
             stack.remove(stack.indexOf(referenceString[i]));
            }
            stack.add(referenceString[i]);
            int search = -1;
            for(int j = 0; j < numFrames; j++)
            {
                if(buffer[j] == referenceString[i])
                {
                    search = j;
                    numHits++;
                    break;
                }
            }
            if(search == -1)
            {
             if(isFull)
             {
              int min_loc = refStringLength;
                    for(int j = 0; j < numFrames; j++)
                    {
                     if(stack.contains(buffer[j]))
                        {
                            int temp = stack.indexOf(buffer[j]);
                            if(temp < min_loc)
                            {
                                min_loc = temp;
                                pointer = j;
                            }
                        }
                    }
             }
                buffer[pointer] = referenceString[i];
                numFaults++;
                pointer++;
                if(pointer == numFrames)
                {
                 pointer = 0;
                 isFull = true;
                }
            }
            for(int j = 0; j < numFrames; j++)
                memoryLayout[i][j] = buffer[j];
        }
        
        System.out.println("Result");
        System.out.println("------------------------------------------------------------------------------------------");
        for(int i = 0; i < numFrames; i++)
        {
            for(int j = 0; j < refStringLength; j++)
                System.out.printf("%3d ",memoryLayout[j][i]);
            System.out.println();
        }
        
        System.out.println("Number of Hits: " + numHits);
        System.out.println("Hit Ratio: " + (float)((float)numHits/refStringLength));
        System.out.println("Number of Faults: " + numFaults);
        
        keyboard.close();

    }
}
