import java.util.*;

public class Paging {
    private final int PAGE_SIZE = 16; // 16 bytes per page
    private final int NUM_PAGES = 64; // 64 pages in memory
    public int MEMORY_SIZE = PAGE_SIZE * NUM_PAGES; // Total memory size
    
    private int[] memory = new int[MEMORY_SIZE]; // Memory array
    private Map<Integer, Integer> pageTable = new HashMap<>(); // Page table to map virtual pages to physical frames
    private int nextFreeFrame = 0; // Index of the next free frame in memory
    
    public Paging() {
        // Initialize memory to all zeros
        Arrays.fill(memory, 0);
    }
    
    public int read(int address) {
        // Calculate the page number and offset from the address
        int pageNum = address / PAGE_SIZE;
        int offset = address % PAGE_SIZE;
        
        // Check if the page is in memory
        if (!pageTable.containsKey(pageNum)) {
            // Page fault - allocate a new frame in memory and load the page into it
            int frameNum = nextFreeFrame++;
            pageTable.put(pageNum, frameNum);
            loadPage(pageNum, frameNum);
        }
        
        // Calculate the physical address and return the value at that address
        int frameNum = pageTable.get(pageNum);
        int physicalAddress = frameNum * PAGE_SIZE + offset;
        return memory[physicalAddress];
    }
    
    public void write(int address, int value) {
        // Calculate the page number and offset from the address
        int pageNum = address / PAGE_SIZE;
        int offset = address % PAGE_SIZE;
        
        // Check if the page is in memory
        if (!pageTable.containsKey(pageNum)) {
            // Page fault - allocate a new frame in memory and load the page into it
            int frameNum = nextFreeFrame++;
            pageTable.put(pageNum, frameNum);
            loadPage(pageNum, frameNum);
        }
        
        // Calculate the physical address and write the value to that address
        int frameNum = pageTable.get(pageNum);
        int physicalAddress = frameNum * PAGE_SIZE + offset;
        memory[physicalAddress] = value;
    }
    
    private void loadPage(int pageNum, int frameNum) {
        // Simulate loading the page from disk into memory
        System.out.println("Loading page " + pageNum + " into frame " + frameNum);
        for (int i = 0; i < PAGE_SIZE; i++) {
            memory[frameNum * PAGE_SIZE + i] = pageNum * PAGE_SIZE + i;
        }
    }
    
    public static void main(String[] args) {
        Paging memoryManager = new Paging();
        
        // Test reading and writing to memory
        memoryManager.write(0, 42);
        System.out.println(memoryManager.read(0));
        
        // Simulate accessing a large amount of memory randomly
        Random rand = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            int address = rand.nextInt(memoryManager.MEMORY_SIZE);
            memoryManager.write(address, i);
            memoryManager.read(address);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double efficiency = (double) memoryManager.NUM_PAGES / (double) (totalTime / 1000.0);
        System.out.println();
        System.out.println("Time taken: " + totalTime + " ms");
        System.out.println("Efficiency: " + efficiency + " pages/second");
    }
    
}
