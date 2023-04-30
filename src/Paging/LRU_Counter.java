package Paging;
import java.util.Scanner;

public class LRU_Counter 
{
    //helper method to find the page to replace in the event that a fault occurs and the frames are all full
    public static int findPagePositionToReplace(int counter[],int numFrames)
    {
        int minCount = counter[0];  //initialize minimum value
        int pos = 0;  //initialize position
        for(int i = 0; i < numFrames; i++) //iterate through the frames
        { 
            if(minCount > counter[i])  //if the minimum count is greater than the current count
            {
                pos = i;  //assign the position the value of that index
            }
        }
        return pos;  //return the position to replace
    }
   
    public static void main(String[] args) 
    {
        Scanner keyboard = new Scanner(System.in);
       
        //declare initial variables to track
        int recent = 0, numFaults = 0, numHits = 0;


        System.out.println("LRU Page Replacement Algorithm Simulation Using Counter");
        System.out.println("------------------------------------------------------------------------------------------");

        //allow user to enter reference string
        System.out.print("\nEnter the reference string: ");  //try using 70120304230321201701 or 1312628131
        String ref = keyboard.next();
        int refStringLength = ref.length();
        int[] pages = new int[refStringLength];
        for(int i = 0; i < refStringLength; i++)
        {
            pages[i] = Integer.parseInt(String.valueOf(ref.charAt(i)));  //take each value in the reference string and add to the array of pages
        }


        //allow user to enter the number of frames
        System.out.print("\nEnter the number of frames: ");  //try using 3 frames
        int numFrames = keyboard.nextInt();


        long startTime = System.currentTimeMillis();  //start the timer (to use as one of the performance indicators)

        //create an array for the frames and an array for the counters (which will be used to track how long each page has been present in the frames)
        int frames[] = new int[numFrames];
        int counter[] = new int[numFrames];
    
        for(int i = 0; i < numFrames; i++)
        {        
            //initialize the frames and counters
            frames[i] = -1;  //a value of -1 means that the current frame is empty
            counter[i] = -1;  
        }
    
        for(int i = 0; i < refStringLength; i++)  //iterate through the reference string
        {
            int flag = 0;  //flag for the different cases of page occurrences and frame availability
            for(int j = 0; j < numFrames; j++)  //iterate through the frames
            {
                if(frames[j] == pages[i])  //if the current frame equals the page that is incoming, it means there is a hit (case 1)
                {
                    numHits++;
                    flag = 1;  //set the flag to 1

                    //set that index of the counter array to hold this frame that is most recently used 
                    //(more recently used page in frame will have a bigger number while least recently used page in frame will have a lower number)
                    //more recent = higher recency value vs. less recent = lower recency value
                    counter[j] = recent++; 
                    
                    break;  //break out
                }
            }
        
            if(flag == 0)  //case 2 - page fault with free frame available
            {
                for(int j = 0; j < numFrames; j++)  //iterate through the frames
                {
                    if(frames[j] == -1)  //if we come accross an empty frame (current frame in the iteration is empty)
                    {   
                        frames[j] = pages[i];  //if a frame is free, add the incoming page to that frame slot
                        
                        //set that index of the counter array to hold this frame that is most recently used 
                        //(more recently used page in frame will have a bigger number while least recently used page in frame will have a lower number)
                        //more recent = higher recency value vs. less recent = lower recency value
                        counter[j] = recent++; 
                        
                        flag = 1;  //again set the flag
                        
                        numFaults++;  //increment the number of page faults to keep track
                        
                        break; //break out
                    }
                
                }
            }

            //case 3 - page fault with NO free frame available
            //if the flag is still 0 it means that we have come across a page fault but the frames are all full so we need to replace something
            if(flag == 0)
            {
                int pagePositionToReplace = findPagePositionToReplace(counter,numFrames);  //find the LRU page to replace based on the counter for it
                frames[pagePositionToReplace] = pages[i];  //replace the LRU page with the new incoming page
                counter[pagePositionToReplace] = recent++;  //set that counter to become the new most recent (so that we can continue keeping track of LRU pages for the future)
                numFaults++;  //increment the number of page faults to keep track
            }
        
            //print resulting frames for each iteration
            System.out.println();
            for(int j = 0; j < numFrames; j++)
            {
                if (frames[j] == -1)  //formatting of empty frames
                {
                    System.out.print("[ ]");
                }
                else
                {
                    System.out.print("[" + frames[j] + "]");
                }
            }
        
        }

        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
    
        System.out.println("\n\nResults");
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Number of Page Faults: " + numFaults);
        System.out.println("Number of Hits: " + numHits);
        System.out.println("Hit Ratio: " + (float)((float)numHits/refStringLength));
        System.out.println("Elapsed Time for LRU Simulation (in milliseconds): " + elapsedTime);
    
        keyboard.close();
    }
    
}
