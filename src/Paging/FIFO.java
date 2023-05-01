import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FIFO {
    //Method to calculate page faults, hits, hit rate, and time elapsed using FIFO algorithm
    static void pagingFIFO(int pages[], int n, int capacity)
    {
        long start = System.nanoTime();
        // To represent set of current pages. We use
        // an unordered_set so that we quickly check
        // if a page is present in set or not
        HashSet<Integer> s = new HashSet<>(capacity);
      
        // To store the pages in FIFO manner
        Queue<Integer> indexes = new LinkedList<>() ;
      
        // Start from initial page
        int page_faults = 0;
        for (int i=0; i<n; i++)
        {
            // Check if the set is full
            if (s.size() < capacity)
            {
                // If current page is not in set, insert it
                if (!s.contains(pages[i]))
                {
                    s.add(pages[i]);
    
                    // Increment page fault
                    page_faults++;
      
                    // Push the current page into the queue
                    indexes.add(pages[i]);
                }
            }
      
            // If set is filled, perform FIFO
            // rRemove the first page of the queue from the set and queue, and insert the current page
            else
            {
                // Check if current page is not in set
                if (!s.contains(pages[i]))
                {
                    //Pop the first page from the queue
                    int val = indexes.peek();
    
                    //Remove the head of the queue
                    indexes.poll();
      
                    //Remove the indexes page in set
                    s.remove(val);
    
                    //Insert the current page
                    s.add(pages[i]);
      
                    //Push the current page into the queue
                    indexes.add(pages[i]);
      
                    //Increment page faults
                    page_faults++;
                }
            }
        }
        //Calculate elapsed time for algorithm
        long elapsed = System.nanoTime() - start;
        double elapsedTime = (double)elapsed/1000000;

        //Calculate the number of hits
        int numHits = pages.length - page_faults;

        //Calculate hit rate
        double hitRate = 100*(double)numHits/pages.length;

        System.out.println("Time elapsed: " + elapsedTime + "ms");
        System.out.println("Number of hits: " + numHits);
        System.out.println("Number of faults: " + page_faults);
        System.out.println("Hit rate: " + hitRate + "%");
    }

    public static void main(String args[])
    {

        Scanner input = new Scanner(System.in);
        System.out.println("FIFO Page Replacement Algorithm Simulation");
        System.out.println("------------------------------------------------------------------------------------------");
        
        System.out.print("Enter number of frames: ");
        int capacity = input.nextInt();

        System.out.print("Enter reference string: ");
        int refStringList[];
        String refString = input.next();
        int refStringLength = refString.length();

        refStringList = new int[refStringLength];
        for(int i=0; i < refStringLength; i++)
        {
            refStringList[i] = Integer.parseInt(String.valueOf(refString.charAt(i)));
        }
        pagingFIFO(refStringList, refStringList.length, capacity);
    }
}
