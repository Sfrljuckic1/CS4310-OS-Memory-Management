import java.util.StringTokenizer;


public class FIFO {
	//First In First Out page replacement algorithm
	public static void fifo(String reference_string, int frames) {
		int faults = 0;

		int[] array = new int [frames];

		for(int i = 0; i < frames; i++){			//fill frames with -1, because they are
			array[i] = -1;							//are first initialized with 0 and that alters
		}											//the result

        //single pointer 
		int pointer = 0;
        //count string length
        int counter = 0;

		StringTokenizer tok = new StringTokenizer(reference_string);
        //start time for algorithm
        long start = System.nanoTime();

        //while loop to read reference string
		while(tok.hasMoreTokens()){
            //convert string token to int
			int value = Integer.parseInt(tok.nextToken());

            //increment string counter
            counter++;

            //check if value is already in frame or not
			if (!(isInFrames(value, frames, array))){
                //increment page faults
				faults++;

                //set page value
				array[pointer] = value;

                //move pointer up in queue
				pointer = (pointer+1)%frames;
			}
		}
        //end time for algorithm and perform calculations for results
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
