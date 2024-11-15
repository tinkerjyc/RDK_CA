/**
 * Yicheng Jiang Coding Activity 2 - Pseudocode to code
 **/

import java.util.Scanner;

public class Code2 {
  /**
   * Drive code with the user input int array interface.
   *
   * @param args user inputs
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int length = 0;

    // Input validation for the array length
    while (true) {
      System.out.println("Please enter the length of the integer array:");
      try {
        length = Integer.parseInt(sc.nextLine());
        if (length <= 0) {
          System.out.println("The length must be a positive integer. Try again.");
          continue;
        }
        break;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid positive integer.");
      }
    }

    // Create an int array to save user input
    int[] input = new int[length];

    // Input validation for array elements
    System.out.println("Please enter " + length + " integers, pressing Enter after each one:");
    for (int i = 0; i < length; i++) {
      while (true) {
        try {
          System.out.print("Element " + (i + 1) + ": ");
          input[i] = Integer.parseInt(sc.nextLine());
          break;
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter an integer.");
        }
      }
    }

    sc.close();

    int output = sortAndFindMedian(input);

    System.out.println("The median of the array input from the user is:");
    System.out.println(output);
  }


  /**
   * Coding based on the pseudocode to find the median of a sorted array.
   *
   * @param numbers an array of int
   * @return the median of the input array
   */
  public static int sortAndFindMedian(int[] numbers) {
    sort(numbers);
    int n = numbers.length;
    if (n % 2 == 0) {
      return (numbers[n / 2 - 1] + numbers[n / 2]) / 2;
    } else {
      return numbers[n / 2];
    }
  }

  /**
   * Merge sort help to keep the pseudocode unchanged.
   *
   * @param numbers an array of int
   */
  public static void sort(int[] numbers) {
    mergeSort(numbers, 0, numbers.length - 1);
  }

  /**
   * Merge sort implementation.
   *
   * @param array the array to be sorted
   * @param left  the starting index
   * @param right the ending index
   */
  public static void mergeSort(int[] array, int left, int right) {
    if (left < right) {
      int mid = left + (right - left) / 2;

      // Recursively split the array
      mergeSort(array, left, mid);
      mergeSort(array, mid + 1, right);

      // Merge the sorted halves
      merge(array, left, mid, right);
    }
  }

  /**
   * Merge two sorted halves into one sorted array.
   *
   * @param array the array to be merged
   * @param left  the starting index of the first half
   * @param mid   the ending index of the first half
   * @param right the ending index of the second half
   */
  public static void merge(int[] array, int left, int mid, int right) {
    // Sizes of the two subarrays
    int n1 = mid - left + 1;
    int n2 = right - mid;

    // Create temporary arrays
    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    // Copy data to temp arrays
    for (int i = 0; i < n1; i++) {
      leftArray[i] = array[left + i];
    }
    for (int i = 0; i < n2; i++) {
      rightArray[i] = array[mid + 1 + i];
    }

    // Merge the temp arrays
    int i = 0, j = 0, k = left;

    while (i < n1 && j < n2) {
      if (leftArray[i] <= rightArray[j]) {
        array[k] = leftArray[i];
        i++;
      } else {
        array[k] = rightArray[j];
        j++;
      }
      k++;
    }

    // Copy remaining elements of leftArray, if any
    while (i < n1) {
      array[k] = leftArray[i];
      i++;
      k++;
    }

    // Copy remaining elements of rightArray, if any
    while (j < n2) {
      array[k] = rightArray[j];
      j++;
      k++;
    }
  }

}