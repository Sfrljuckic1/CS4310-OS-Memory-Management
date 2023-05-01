import java.util.StringTokenizer;

public class FIFO {
    /**
	 * First In First Out page replacement algorithm
	 * 
	 * This algorithm swaps pages based on the order in which they were added to the frames,
	 * it basically has a pointer that points to the next spot after an element was added,
	 * acting basically like a circular queue. 
	 * 
	 * @param reference_string			Reference string, is used to put values into the frames
	 * @param frames					Integer containing the number of frames (user defined)
	 * @return							Returns faults, which is the integer containing the number of page faults
	 * 
	 * Local Variables:
	 * faults		int				Contains number of page faults
	 * array		int[]			Array of frames where values will be added
	 * i			int				Loop iteration variable
	 * pointer		int				Pointer to index where value will be input in array
	 * tok			StringTokenizer	Tokenizer that separates reference string element by element
	 * value		int				Element in the reference string to be added to array
	 */
	
	public static void fifo(String reference_string, int frames) {
		int faults = 0;

		int[] array = new int [frames];

		for(int i = 0; i < frames; i++){			//fill frames with -1, because they are
			array[i] = -1;							//are first initialized with 0 and that alters
		}											//the result

		int pointer = 0;
        int counter = 0;
		StringTokenizer tok = new StringTokenizer(reference_string);
        long start = System.nanoTime();
		while(tok.hasMoreTokens()){
			int value = Integer.parseInt(tok.nextToken());
            counter++;
			if (!(isInFrames(value, frames, array))){
				faults++;				//increase count of faults
				array[pointer] = value;
				pointer = (pointer+1)%frames;		//move the pointer up in the queue
			}
		}
        long elapsed = System.nanoTime() - start;
        double elapsedTime = (double)elapsed/1000000;
        System.out.println("Time elapsed: " + elapsedTime + "ms");
        System.out.println("Page faults: " + faults);
        int numHits = counter - faults;
        System.out.println("Number of hits: " + numHits);
        double hitRate = (double)numHits/counter;
        hitRate = hitRate*100;
        System.out.printf("Hit rate: %.2f%%", hitRate);

	}

    static boolean isInFrames(int value, int frames, int[] array) {

		for (int i = 0; i < frames; i++){
			if (array[i] == value){
				return true;
			}
		}
		return false;
	}

    public static void main(String[] args) {

        System.out.println("FIFO Page Replacement Algorithm Simulation");
        System.out.println("------------------------------------------------------------------------------------------");

        int frames = 5;
        String ref_string = "7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1";

        System.out.println("Number of frames: " + frames);
        System.out.println("Reference string: " + ref_string);

        fifo(ref_string, frames);
    }
}
