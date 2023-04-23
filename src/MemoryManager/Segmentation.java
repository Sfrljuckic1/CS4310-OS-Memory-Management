import java.util.HashMap;
import java.util.Map;

public class Segmentation {
    private Map<Integer, MemorySegment> segmentTable;
    private int nextSegmentId;
    private int memorySize;
    private int blockSize;

    public Segmentation(int memorySize, int blockSize) {
        this.segmentTable = new HashMap<>();
        this.nextSegmentId = 0;
        this.memorySize = memorySize;
        this.blockSize = blockSize;
    }

    public int allocate(int size) {
        int numBlocks = (int) Math.ceil((double) size / blockSize);
        if (numBlocks > memorySize / blockSize) {
            // Not enough memory
            return -1;
        }

        int segmentId = nextSegmentId++;
        MemorySegment segment = new MemorySegment(segmentId, numBlocks);
        segmentTable.put(segmentId, segment);
        return segmentId * blockSize;
    }

    public void free(int address) {
        int segmentId = address / blockSize;
        MemorySegment segment = segmentTable.get(segmentId);
        if (segment != null) {
            segmentTable.remove(segmentId);
            System.out.println("Freed memory segment with ID: " + segment.getId());
        }
    }
    

    public boolean isAddressValid(int address) {
        int segmentId = address / blockSize;
        MemorySegment segment = segmentTable.get(segmentId);
        if (segment != null) {
            int offset = address % blockSize;
            return offset < segment.getSize() * blockSize;
        }
        return false;
    }

    public static void main(String[] args) {
        Segmentation memoryManager = new Segmentation(1024, 64);

        long startTime = System.nanoTime();
        int address1 = memoryManager.allocate(128);
        long endTime = System.nanoTime();
        System.out.println("Allocated address 1: " + address1);
        System.out.println("Time taken to allocate 128 bytes: " + (endTime - startTime) + " ns");
        System.out.println();

        startTime = System.nanoTime();
        int address2 = memoryManager.allocate(256);
        endTime = System.nanoTime();
        System.out.println("Allocated address 2: " + address2);
        System.out.println("Time taken to allocate 256 bytes: " + (endTime - startTime) + " ns");
        System.out.println();

        startTime = System.nanoTime();
        int address3 = memoryManager.allocate(512);
        endTime = System.nanoTime();
        System.out.println("Allocated address 3: " + address3);
        System.out.println("Time taken to allocate 512 bytes: " + (endTime - startTime) + " ns");
        System.out.println();

        boolean isValid1 = memoryManager.isAddressValid(address1);
        boolean isValid2 = memoryManager.isAddressValid(address2);
        boolean isValid3 = memoryManager.isAddressValid(address3);
        System.out.println("Validity of address 1: " + isValid1);
        System.out.println("Validity of address 2: " + isValid2);
        System.out.println("Validity of address 3: " + isValid3);
        System.out.println();

        startTime = System.nanoTime();
        memoryManager.free(address2);
        endTime = System.nanoTime();
        System.out.println("Time taken to free address 2: " + (endTime - startTime) + " ns");
        System.out.println();

        boolean isValid4 = memoryManager.isAddressValid(address2);
        System.out.println("Validity of address 2 after freeing: " + isValid4);
        System.out.println();
    }
    
    private class MemorySegment {
        private int id;
        private int size;

        public MemorySegment(int id, int size) {
            this.id = id;
            this.size = size;
        }

        public int getId() {
            return id;
        }

        public int getSize() {
            return size;
        }
    }
}