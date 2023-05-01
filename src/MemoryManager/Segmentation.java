import java.util.*;

public class Segmentation {
    private Map<Integer, MemorySegment> segmentTable;
    private int nextSegmentId;
    private int memorySize;
    private int pageSize;
    private int[] pageTable;
    private int freeFrameCount;
    private int[] frameTable;
    private int frameTableSize;

    public Segmentation(int memorySize, int pageSize) {
        this.segmentTable = new HashMap<>();
        this.nextSegmentId = 0;
        this.memorySize = memorySize;
        this.pageSize = pageSize;

        // Initialize page table
        int numPages = memorySize / pageSize;
        this.pageTable = new int[numPages];
        Arrays.fill(pageTable, -1);

        // Initialize frame table
        this.frameTableSize = memorySize / pageSize;
        this.frameTable = new int[frameTableSize];
        Arrays.fill(frameTable, -1);
        this.freeFrameCount = frameTableSize;
    }

    public int allocate(int size) {
        int numPages = (int) Math.ceil((double) size / pageSize);
        if (numPages > memorySize / pageSize || numPages > freeFrameCount) {
            // Not enough memory
            return -1;
        }

        // Allocate frames for the new pages
        int[] pageList = new int[numPages];
        int i = 0;
        while (i < numPages) {
            if (freeFrameCount == 0) {
                // No free frames left
                break;
            }

            int frameIndex = findFreeFrame();
            if (frameIndex == -1) {
                // No free frames left
                break;
            }

            // Mark frame as used
            frameTable[frameIndex] = nextSegmentId;
            freeFrameCount--;

            // Update page table
            int pageIndex = frameIndex;
            pageTable[pageIndex] = nextSegmentId;
            pageList[i] = pageIndex;
            i++;
        }

        if (i < numPages) {
            // Failed to allocate enough frames
            // Free up the frames we allocated and return -1
            for (int j = 0; j < i; j++) {
                int pageIndex = pageList[j];
                // int segmentId = pageTable[pageIndex];
                frameTable[pageIndex] = -1;
                freeFrameCount++;
            }
            return -1;
        }

        // Create new memory segment and update segment table
        int segmentId = nextSegmentId++;
        MemorySegment segment = new MemorySegment(segmentId, size, pageList);
        segmentTable.put(segmentId, segment);

        return segmentId * pageSize;
    }

    public void free(int address) {
        int pageIndex = address / pageSize;
        int segmentId = pageTable[pageIndex];
        if (segmentId != -1) {
            MemorySegment segment = segmentTable.get(segmentId);
            if (segment != null) {
                // Free all frames used by this segment
                for (int i = 0; i < segment.getPageCount(); i++) {
                    int pageTableIndex = segment.getPageList()[i];
                    frameTable[pageTableIndex] = -1;
                    freeFrameCount++;
                }

                // Remove segment from segment table
                segmentTable.remove(segmentId);
                System.out.println("Freed memory segment with ID: " + segment.getId());
            }
        }
    }

    public boolean isAddressValid(int address) {
        int pageIndex = address / pageSize;
        int segmentId = pageTable[pageIndex];
        if (segmentId != -1) {
            MemorySegment segment = segmentTable.get(segmentId);
            // Check if the address is within the bounds of the segment
            int segmentStart = segmentId * pageSize;
            int segmentEnd = segmentStart + segment.getSize() - 1;
            if (address >= segmentStart && address <= segmentEnd) {
                return true;
            }
        }
        return false;
    }
    
    
    
    private int findFreeFrame() {
        for (int i = 0; i < frameTableSize; i++) {
            if (frameTable[i] == -1) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        Segmentation segmentation = new Segmentation(1024, 32);
    
        int segmentId1 = segmentation.allocate(256);
        System.out.println("Allocated memory segment with ID: " + segmentId1);
        int segmentId2 = segmentation.allocate(128);
        System.out.println("Allocated memory segment with ID: " + segmentId2);
        int segmentId3 = segmentation.allocate(512);
        System.out.println("Allocated memory segment with ID: " + segmentId3);
    
        System.out.println("Is address 0 valid? " + segmentation.isAddressValid(0));
        System.out.println("Is address 256 valid? " + segmentation.isAddressValid(256));
        System.out.println("Is address 512 valid? " + segmentation.isAddressValid(512));
        System.out.println("Is address 768 valid? " + segmentation.isAddressValid(768));
        System.out.println("Is address 1023 valid? " + segmentation.isAddressValid(1023));
    
        segmentation.free(segmentId1 * 32);
        System.out.println("Freed memory segment with ID: " + segmentId1);
    
        int segmentId4 = segmentation.allocate(256);
        System.out.println("Allocated memory segment with ID: " + segmentId4);
    }
}

// A class to represent a single memory segment
class MemorySegment {
    private int id;             // The ID of the memory segment
    private int size;           // The total size of the memory segment in bytes
    private int[] pageList;     // An array of page numbers that belong to this memory segment

    // Constructor that takes an ID, size, and page list as arguments
    public MemorySegment(int id, int size, int[] pageList) {
        this.id = id;
        this.size = size;
        this.pageList = pageList;
    }

    // ID of the memory segment
    public int getId() {
        return id;
    }

    // size of the memory segment
    public int getSize() {
        return size;
    }

    // page list of the memory segment
    public int[] getPageList() {
        return pageList;
    }

    // number of pages in the memory segment
    public int getPageCount() {
        return pageList.length;
    }
}