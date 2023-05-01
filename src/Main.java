import Paging.MFU;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String[] args) {
    try {
      File input = new File("input.txt");
      Scanner buffer = new Scanner(input);

      // Parse the `input.txt` and 
      // tokenize the reference strings to be stored as an array of integers.
      String line = buffer.nextLine();
      String[] tokens = line.split(",");
      int[] pointers = new int[tokens.length];

      for (int i = 0; i < tokens.length; i++) {
        String token = tokens[i].trim();
        pointers[i] = Integer.parseInt(token);
      }

      MFU mru = new MFU(pointers, 5);
      mru.compute();

      buffer.close();
    } catch(FileNotFoundException error) {
      error.printStackTrace();
    }

  }
}
