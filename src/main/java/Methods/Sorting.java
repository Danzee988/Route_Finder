package Methods;

public class Sorting {
    public static void quickSort(int[] roots, int[] sizes, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(roots, sizes, left, right);               // Partition the arrays and get the pivot index
            quickSort(roots, sizes, left, pivotIndex - 1);                  // Sort the left subarray
            quickSort(roots, sizes, pivotIndex + 1, right);                  // Sort the right subarray
        }
    }

    public static int partition(int[] roots, int[] sizes, int left, int right) {
        int pivot = sizes[right];                                                // Choose the rightmost element as the pivot
        int i = left - 1;                                                        // Index of the smaller element
        for (int j = left; j <= right - 1; j++) {
            if (sizes[j] >= pivot) {                                             // If the current element is greater than or equal to the pivot
                i++;
                swap(roots, i, j);                                               // Swap roots[i] and roots[j]
                swap(sizes, i, j);                                               // Swap sizes[i] and sizes[j]
            }
        }
        swap(roots, i + 1, right);                                             // Swap roots[i+1] and roots[right]
        swap(sizes, i + 1, right);                                             // Swap sizes[i+1] and sizes[right]
        return i + 1;                                                            // Return the pivot index
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
