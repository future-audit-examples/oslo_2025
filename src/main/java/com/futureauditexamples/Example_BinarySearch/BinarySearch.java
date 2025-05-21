package com.futureauditexamples.Example_BinarySearch;

public class BinarySearch {

    public static void main(String[] args) {

        // A sorted list of names (like a phone book)
        String[] phoneBook = {"Alice", "Bob", "Charlie", "David", "Emma", "Linda", "Michael", "Nina", "Zoe"};

        String nameToFind = "Bob";

        int result = binarySearch(phoneBook, nameToFind);

        if (result == -1) {
            System.out.println(nameToFind + " was not found in the phone book.");
        } else {
            System.out.println(nameToFind + " was found at position " + result + " in the phone book.");
        }
    }

    public static int binarySearch(String[] names, String target) {
        int left = 0;
        int right = names.length - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            int comparison = target.compareTo(names[middle]);

            if (comparison == 0) {
                return middle; // Found the name!
            } else if (comparison < 0) {
                right = middle - 1; // Look to the left
            } else {
                left = middle + 1; // Look to the right
            }
        }

        return -1; // Not found
    }
}
