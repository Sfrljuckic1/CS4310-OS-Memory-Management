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
      return - 1;
    }
    
    return 0;
  }
}

// Most Recently Used
// The most recently used will be discared from the queue.
public class MRU {

  private int frames;
  private ArrayList<Node> pointers;
  private PriorityQueue<Node> queue;

  private int faults = 0;
  private int hits = 0;

  public MRU(int[] pointers, int frames) {
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

  public void compute() {
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
      System.out.println("Hit: " + this.hits);
      System.out.println("Faults: " + this.faults);
    }
  }
}