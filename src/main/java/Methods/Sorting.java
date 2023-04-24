package Methods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

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

    public HashMap<String, String[]> createStationsMap() throws Exception {
        String path = "/Users/danze/Desktop/London.csv/";

        BufferedReader br = new BufferedReader(new FileReader(path));

        Map<String, String[]> map = new HashMap<>();
        String line;
        int newId = 1;
        while ((line = br.readLine()) != null) {                        // Read the file line by line
            String[] values = line.split(",");                    // Split the line into an array of values
            if (values.length > 4 && ("1".equals(values[4].trim()) || "1.5".equals(values[4].trim()))) {    // Check if the line contains the station ID
                String[] stations = line.split(",");              // Split the line into an array of values
                stations[0] = Integer.toString(newId);                  // Replace the ID with the new value
                map.put(stations[0], stations);                         // Store the array of values using the new ID as the key
                newId++;                                                // Increment the new ID for the next station
            }
        }
        br.close();
        return (HashMap<String, String[]>) map;
    }

//    public void connectNodes() throws Exception {
//        AdjacencyMatrix am = new AdjacencyMatrix(64);
//        HashMap<String, String[]> map = createStationsMap();
//        String[] roots = new String[map.size()];                                 // Create an array to store the roots
//        int[] sizes = new int[map.size()];
//
//        int index = 0;
//        for (String root : map.keySet()) {                                    // Iterate over the roots in the size map
//            roots[index] = root;
//            sizes[index] = map.get(root);
//            index++;
//        }
//
//        Sorting.quickSort(roots, sizes, 0, roots.length - 1);
//
//        GraphNodes<String> earl = new GraphNodes<>(Arrays.toString(map.get(key)), am);
//    }
}
