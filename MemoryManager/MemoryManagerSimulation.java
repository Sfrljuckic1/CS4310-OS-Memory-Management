import java.util.*;

public class MemoryManagerSimulation {
    public static void main(String[] args) {
        // Simulate Paging
        Paging paging = new Paging();
        long start = System.currentTimeMillis();
        // Test reading and writing to memory
        paging.write(0, 42);
        paging.read(0);
        // Simulate accessing a large amount of memory randomly
        Random rand = new Random();
        for (int i = 0; i < 1000000; i++) {
            int address = rand.nextInt(paging.MEMORY_SIZE);
            paging.write(address, i);
            paging.read(address);
        }
        long end = System.currentTimeMillis();
        long pagingTime = end - start;

        // Simulate Segmentation
        Segmentation segmentation = new Segmentation(1024, 64);
        start = System.currentTimeMillis();
        // Test allocating memory
        int address1 = segmentation.allocate(128);
        int address2 = segmentation.allocate(256);
        int address3 = segmentation.allocate(512);
        // Test checking address validity
        boolean isValid1 = segmentation.isAddressValid(address1);
        boolean isValid2 = segmentation.isAddressValid(address2);
        boolean isValid3 = segmentation.isAddressValid(address3);
        // Test freeing memory
        segmentation.free(address1);
        segmentation.free(address2);
        segmentation.free(address3);
        end = System.currentTimeMillis();
        long segmentationTime = end - start;

        // Output results
        System.out.println("Paging time: " + pagingTime + "ms");
        System.out.println("Segmentation time: " + segmentationTime + "ms");

        if (pagingTime < segmentationTime) {
            System.out.println("Paging is more efficient.");
        } else {
            System.out.println("Segmentation is more efficient.");
        }
    }
}
