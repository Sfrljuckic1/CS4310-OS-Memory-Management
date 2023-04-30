package Paging;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FIFO {
    // Method to find page faults using FIFO
    static int pageFaults(int pages[], int n, int capacity)
    {
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
            // Check if the set can hold more pages
            if (s.size() < capacity)
            {
                // Insert it into set if not present
                // already which represents page fault
                if (!s.contains(pages[i]))
                {
                    s.add(pages[i]);
    
                    // increment page fault
                    page_faults++;
      
                    // Push the current page into the queue
                    indexes.add(pages[i]);
                }
            }
      
            // If the set is full then need to perform FIFO
            // i.e. remove the first page of the queue from
            // set and queue both and insert the current page
            else
            {
                // Check if current page is not already
                // present in the set
                if (!s.contains(pages[i]))
                {
                    //Pop the first page from the queue
                    int val = indexes.peek();
    
                    indexes.poll();
      
                    //Remove the indexes page
                    s.remove(val);
    
                    //insert the current page
                    s.add(pages[i]);
      
                    //push the current page into
                    //the queue
                    indexes.add(pages[i]);
      
                    //Increment page faults
                    page_faults++;
                }
            }
        }
      
        return page_faults;
    }

    public static void main(String args[])
    {

        Scanner input = new Scanner(System.in);
        System.out.println("FIFO Page Replacement Algorithm Simulation");
        System.out.println("------------------------------------------------------------------------------------------");
        
        System.out.println("Enter number of frames: ");
        int capacity = input.nextInt();

        System.out.println("Enter reference string: ");
        int refStringList[];
        String refString = input.next();
        int refStringLength = refString.length();

        refStringList = new int[refStringLength];
        for(int i=0; i < refStringLength; i++)
        {
            refStringList[i] = Integer.parseInt(String.valueOf(refString.charAt(i)));
        }

        System.out.print("Page faults: ");
        System.out.println(pageFaults(refStringList, refStringList.length, capacity));
    }
}
