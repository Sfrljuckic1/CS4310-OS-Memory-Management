package Paging;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node {
  public int value;
  public int frequency;

  Node(int value) {
    this.value = value;
    this.frequency = 1;
  }

  Node(int value, int frequency) {
    this.value = value;
    this.frequency = frequency;
  }

  @Override
  public String toString() {
    return "pointer: " + value + "; " + "frequency: " + frequency;
  }
}

class NodeComparator implements Comparator<Node> {
  public int compare(Node a, Node b) {
    if (a.frequency < b.frequency) {
      return 1;
    } else if (a.frequency > b.frequency) {
      return -1;
    }
    
    return 0;
  }
}

// Most Frequently Used
// The most frequently used will be discared from the queue.
public class MFU {

  private int frames;
  private ArrayList<Node> pointers;
  private PriorityQueue<Node> queue;

  private int faults = 1;
  private int hits = 0;
  private double ratio = 0;

  public MFU(int[] pointers, int frames) {
    this.frames = frames;
    this.pointers = new ArrayList<>();
    this.queue = new PriorityQueue<>(new NodeComparator());

    for (int pointer : pointers) {
      Node node = new Node(pointer);
      this.pointers.add(node);
    }
  }

  private boolean isInMemory(Node pointer) {
    int target = pointer.value;
    Iterator<Node> iterator = queue.iterator();

    // O(n), where n is the size of the frames.
    // Therefore, n is a constant, thus O(1)
    while (iterator.hasNext()) {
      Node current = iterator.next();
      if (current.value == target) {
        // Since priority queue doesn't track mutations
        // we have to force the binary tree to update its ordering by
        // entirely replacing the new node.
        int frequency = current.frequency;
        this.queue.remove(current);
        this.queue.add(new Node(target, frequency + 1));
        this.hits = this.hits + 1;
        return true;
      }
    }

    return false;
  }

  // Time complexity: O(n * v) -> O(n); since v is a constant.
  // Define `n` as the length of the reference string.
  // Define `v` as the size of the frames.
  public void compute() {
    long start = System.currentTimeMillis();

    // O(n), where n is the length of pointers.
    for (Node pointer : pointers) { 

      // If the current pointer doesn't exist within the queue.
      // and if the queue size is >= frames
      // there's a page faults and we have to replace the most recently used to the current pointer.

      // If the current pointer exist within the queue,
      // we have to move that pointer to the head of the heap, since that is the most recently used.
      // we can mark there's a hit.

      // There is a page fault if it doesn't exist within queue.
      // We have add the pointer to the queue and increase the `faults` by 1.

      if (!isInMemory(pointer)) {
        if (this.queue.size() >= frames) {
          this.queue.poll();
        }

        this.queue.add(pointer);
        this.faults = this.faults + 1;
      }

      Iterator<Node> iterator = this.queue.iterator();
      while (iterator.hasNext()) {
        System.out.println(iterator.next());
      }

      System.out.println("-----------------------------------------");
      System.out.println("Faults: " + this.faults);
      System.out.println("Hit: " + this.hits);

      this.ratio = ((double) this.hits / (double) this.pointers.size()) * 100;
      System.out.println("Hit Ratio: " + String.format("%.2f", this.ratio) + "%");
    }

    long delta = System.currentTimeMillis() - start;
    System.out.println("Completed in (ms): " + delta);
  }
}