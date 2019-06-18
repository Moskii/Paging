import java.util.*;
import java.io.*;

class A1Paging {

  private static Scanner keyboardInput = new Scanner (System.in);
  private static final int maxCacheSize = 10;
  private static final int maxRequest = 100;


  // Do NOT change the main method!
  // main program
  public static void main(String[] args) throws Exception {
    int count=0, size=0;
    int[] org_cache = new int[maxCacheSize];
    int[] cache = new int[maxCacheSize];
    int[] request = new int[maxRequest];
    
    init_array(org_cache, maxCacheSize, -1);
    init_array(request, maxRequest, 0);

    // get the cache size and the number of requests 
    // then get the corresponding input in the respective arrays
    try {
      System.out.println();
      System.out.print("Enter the cache size (1-" + maxCacheSize + "): ");
      size = keyboardInput.nextInt();
      System.out.print("Enter the content of the cache (" + size + " different +ve integers): ");
      for (int i=0; i<size; i++)
        org_cache[i] = keyboardInput.nextInt();       
      System.out.println();
      System.out.print("Enter the number of page requests: (1-" + maxRequest + "): ");
      count = keyboardInput.nextInt();
      if (count > maxRequest || count <= 0)
        System.exit(0);
      System.out.print("Enter " + count + " +ve integers: ");
      for (int i=0; i<count; i++)
        request[i] = keyboardInput.nextInt();       
    }
    catch (Exception e) {
      keyboardInput.next();
      System.exit(0);
    }

    
    System.out.println();
    System.out.println("Cache content: ");
    print_array(org_cache, size);
    System.out.println("Request sequence: ");
    print_array(request, count);

    
    try {
      copy_array(org_cache, cache, size);
      System.out.println("no_evict");
      no_evict(cache, size, request, count);
    }
    catch (Exception e) {
      System.out.println("ERROR: no_evict");
    }

    try {
      copy_array(org_cache, cache, size);
      System.out.println("evict_largest");
      evict_largest(cache, size, request, count);
    }
    catch (Exception e) {
      System.out.println("ERROR: evict_largest");
    }

    try {
      copy_array(org_cache, cache, size);
      System.out.println("evict_fifo");
      evict_fifo(cache, size, request, count);
    }
    catch (Exception e) {
      System.out.println("ERROR: evict_fifo");
    }

    try {
      copy_array(org_cache, cache, size);
      System.out.println("evict_lfu");
      evict_lfu(cache, size, request, count);
    }
    catch (Exception e) {
      System.out.println("ERROR: evict_lfu");
    }

    try {
      copy_array(org_cache, cache, size);
      System.out.println("evict_lfd");
      evict_lfd(cache, size, request, count);
    }
    catch (Exception e) {
      System.out.println("ERROR: evict_lfd");
    }


  }
  
  // Do NOT change this method!
  // set array[0]..array[n-1] to value
  static void init_array(int[] array, int n, int value) {
    for (int i=0; i<n; i++) 
      array[i] = value;
  }
  
  // Do NOT change this method!
  // print array[0]..array[n-1]
  static void print_array(int[] array, int n) {
    for (int i=0; i<n; i++) {
      System.out.print(array[i] + " ");
      if (i%10 == 9)
        System.out.println();
    }
    System.out.println();
  }
  
  // Do NOT change this method!
  // copy n numbers from array a1 to array a2, starting from a1[x1] and a2[x2]
  static void copy_array(int[] a1, int[] a2, int n) {
    for (int i=0; i<n; i++) {
      a2[i] = a1[i];
    }
  } 

  // no eviction
  // Complete
  static void no_evict(int[] cache, int c_size, int[] request, int r_size) {
    // Declaring variables which will iterate through request sequence and the cache and counts the N.O of 'h' and 'm'.
    int h_counter = 0;
    int m_counter = 0;
    int c_index = 0;
    int r_index = 0;
    String hit_or_miss = ""; 

     do {
      if (request[r_index] == cache[c_index]) {
        hit_or_miss = hit_or_miss + "h";
        // By adding 1, it moves onto the next request in the sequence
        r_index = r_index + 1;
        // Always reset the index for cache to 0 after a 'h' / 'm' to compare the cache fully
        c_index = 0;
        h_counter = h_counter + 1;
      }
      /* If the index for the cache is greater than the size of cache (as explicitly inputted at the beginning), this implies
         the request is not in the cache i.e 'm' */
      else if (c_index > c_size) {
        hit_or_miss = hit_or_miss + "m";
        r_index = r_index + 1;
        c_index = 0;
        m_counter = m_counter + 1;
      }
      else {
        // Iterates through cache when the other conditions are not met
        c_index = c_index + 1;
      }
    } while (r_index < r_size); // The loop won't terminate till it has iterated through the whole request
    System.out.print(hit_or_miss + "\n");
    System.out.print(h_counter + " " + "h" + " " + m_counter + " " + "m" + "\n");
  }

  // evict largest number in cache if next request is not in cache
  // Complete
  static void evict_largest(int[] cache, int c_size, int[] request, int r_size) {
    int h_counter = 0;
    int m_counter = 0;
    int c_index = 0;
    int r_index = 0;
    String hit_or_miss = "";

    // Declare variables which will help iterate through the cache to look for the max
    int i;
    int max;
    max = 0;

     while (r_index < r_size) {
      if (request[r_index] == cache[c_index]) {
        hit_or_miss = hit_or_miss + "h";
        r_index = r_index + 1;
        c_index = 0;
        h_counter = h_counter + 1;
      }
      else if (c_index > c_size) {
        //Add loops to replace the largest.
        for (i = 0; i < c_size;) {
          // This distinguishes whether the number is a max or not.
          if (cache[i] <= cache[i+1]) {
            i = i + 1;
          }
          else if (cache[i] > cache[i+1]) {
            // Place the number into the max variables which will go in the cache and replace it.
            max = request[r_index];
            cache[i] = max;
            i = i + 1;
          }
        }

        hit_or_miss = hit_or_miss + "m";
        r_index = r_index + 1;
        c_index = 0;
        m_counter = m_counter + 1;
      }
      else {
        c_index = c_index + 1;
      }
    } 
    System.out.print(hit_or_miss + "\n");
    System.out.print(h_counter + " " + "h" + " " + m_counter + " " + "m" + "\n");
  }
  
  // evict the number present in cache for longest time if next request is not in cache
  // Complete
  static void evict_fifo(int[] cache, int c_size, int[] request, int r_size) {
    // Declaring variables which will iterate through request sequence and the cache and counts the N.O of 'h' and 'm'.
    int h_counter = 0;
    int m_counter = 0;
    int c_index = 0;
    int r_index = 0;
    String hit_or_miss = ""; 

    // Declare head to indicate the position of queue.
    int head = 0;

     do {
      if (request[r_index] == cache[c_index]) {
        hit_or_miss = hit_or_miss + "h";
        r_index = r_index + 1;
        c_index = 0;
        h_counter = h_counter + 1;
      }
      else if (c_index > c_size) {
        // Switches the element
        cache[head] = request[r_index];
        // Adds 1 to the head to indicate the postion of the cache for fifo
        head = head + 1;
        hit_or_miss = hit_or_miss + "m";
        r_index = r_index + 1;
        c_index = 0;
        m_counter = m_counter + 1;
      }
      else {
        c_index = c_index + 1;
      }
    } while (r_index < r_size); 
    System.out.print(hit_or_miss + "\n");
    System.out.print(h_counter + " " + "h" + " " + m_counter + " " + "m" + "\n");
  }

  // evict the number that is least freqently used so far if next request is not in cache
  // Incomplete
  static void evict_lfu(int[] cache, int c_size, int[] request, int r_size) {
    /*int h_counter = 0;
    int m_counter = 0;
    int c_index = 0;
    int r_index = 0;
    String hit_or_miss = "";

    int size = c_size;
    int[] myIntArr = new int[size];
    int i = 0;
    //Time complexity: o()?

     do {
      if (request[r_index] == cache[c_index]) {
        hit_or_miss = hit_or_miss + "h";
        r_index = r_index + 1;
        c_index = 0;
        h_counter = h_counter + 1;
        myIntArr[i]++;
        i = i + 1;
      }
      else if (c_index > c_size) {
        for (int j = 0;j < c_size;) {
          if (myIntArr[j] > myIntArr[j+1]) {
            j = j + 1;
          }
          else {
            cache[c_index] = request[r_index];
            break;
          }
        }
        hit_or_miss = hit_or_miss + "m";
        r_index = r_index + 1;
        c_index = 0;
        m_counter = m_counter + 1;
      }
      else {
        c_index = c_index + 1;
      }
    } while (r_index < r_size); 
    System.out.print(hit_or_miss + "\n");
    System.out.print(h_counter + " " + "h" + " " + m_counter + " " + "m" + "\n");*/
  }

  // evict the number whose next request is the latest
  // Incomplete
  static void evict_lfd(int[] cache, int c_size, int[] request, int r_size) {
    /* int h_counter = 0;
    int m_counter = 0;
    int c_index = 0;
    int r_index = 0;
    String hit_or_miss = "";
     
    do {
      if (request[r_index] == cache[c_index]) {
        hit_or_miss = hit_or_miss + "h";
        r_index = r_index + 1;
        c_index = 0;
        h_counter = h_counter + 1;
      }
      else if (c_index > c_size) {
        // Add LFD Method here?
        hit_or_miss = hit_or_miss + "m";
        r_index = r_index + 1;
        c_index = 0;
        m_counter = m_counter + 1;
      }
      else {
        c_index = c_index + 1;

     }
    } while (r_index < r_size);
    System.out.print(hit_or_miss + "\n");
    System.out.print(h_counter + " " + "h" + " " + m_counter + " " + "m" + "\n"); */
  }

}
