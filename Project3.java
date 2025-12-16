// Kristoffer Sy Comp 482 Project 3
// Personal Notes:
// Analysis: Requires O(log(n)), NO MERGING. low1,low2,low3...high1,high2,high3 => Binary Search 
// Input / Goal = kth index of merged arrays (one big array from 3 sorted Arrays), length of arrays, sorted arrays => Binary Search
// Scan, count how many elements are <= mid value, adjust low/high accordingly until low == high
// Find K-th value where mid value occurs and count Less or equal to K (mid = (min + max /2))
// compare count with K+1, repeat until min = max

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project3 {

    public static void main(String[] args) throws FileNotFoundException {

        int kthIndex;
        int lenList1;
        int lenList2;
        int lenList3;
        int[] list1;
        int[] list2;
        int[] list3;

        try (Scanner inputFile = new Scanner(new File("input.txt"))) {
            // k = 0-based index of the desired element in the merged ordering
            kthIndex = inputFile.nextInt();
            lenList1 = inputFile.nextInt();
            lenList2 = inputFile.nextInt();
            lenList3 = inputFile.nextInt();
            list1 = new int[lenList1];
            list2 = new int[lenList2];
            list3 = new int[lenList3];
            for (int i = 0; i < lenList1; i++) list1[i] = inputFile.nextInt();
            for (int i = 0; i < lenList2; i++) list2[i] = inputFile.nextInt();
            for (int i = 0; i < lenList3; i++) list3[i] = inputFile.nextInt();
        }

        int totalSize = lenList1 + lenList2 + lenList3;
        if (kthIndex < 0 || kthIndex >= totalSize) {
            throw new IllegalArgumentException("k is outside the valid range.");
        }
        long startTimestamp = System.currentTimeMillis();
        System.out.println(startTimestamp);

        int kthValue = findKthValue(list1, list2, list3, kthIndex);
        System.out.println(kthValue);

        long endTimestamp = System.currentTimeMillis();
        System.out.println(endTimestamp);
    }


    // binary search on the value range to find the k-th smallest value.
    private static int findKthValue(int[] list1, int[] list2, int[] list3, int kthIndex) {

        // Determine global min and max values across all lists
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        if (list1.length > 0) { minValue = Math.min(minValue, list1[0]); maxValue = Math.max(maxValue, list1[list1.length - 1]); }
        if (list2.length > 0) { minValue = Math.min(minValue, list2[0]); maxValue = Math.max(maxValue, list2[list2.length - 1]); }
        if (list3.length > 0) { minValue = Math.min(minValue, list3[0]); maxValue = Math.max(maxValue, list3[list3.length - 1]); }

        // need (k+1)-th smallest element (1-based rank)
        int targetRank = kthIndex + 1;

        // Binary search for the value whose rank is targetRank
        while (minValue < maxValue) {

            int midValue = minValue + (maxValue - minValue) / 2;

            long countLessOrEqual =
                    countLessOrEqual(list1, midValue) +
                    countLessOrEqual(list2, midValue) +
                    countLessOrEqual(list3, midValue);

            // If enough elements are ≤ midValue, the answer is ≤ midValue
            if (countLessOrEqual >= targetRank) {
                maxValue = midValue;
            } else {
                minValue = midValue + 1;
            }
        }

        return minValue;
    }


    // Use binary search, count how many elements in a sorted array are ≤ the given value
    private static int countLessOrEqual(int[] sortedList, int value) {

        int left = 0;
        int right = sortedList.length; 

        // Standard binary search for the first element > value
        while (left < right) {
            int mid = (left + right) / 2;

            if (sortedList[mid] <= value)
                left = mid + 1;
            else
                right = mid;
        }

        return left; // count of elements ≤ value
    }
}