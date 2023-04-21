package Methods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Stations {

    public HashMap<String, String[]> createStationsMap() throws Exception {
        String path = "/Users/danze/Desktop/London.csv/";

        BufferedReader br = new BufferedReader(new FileReader(path));

        Map<String, String[]> map = new HashMap<>();
        String line;
        int newId = 1;
            while ((line = br.readLine()) != null) {                        // Read the file line by line
                String[] values = line.split(",");                    // Split the line into an array of values
                if (values.length > 4 && "1".equals(values[4].trim())) {    // Check if the line contains the station ID
                    String[] stations = line.split(",");              // Split the line into an array of values
                    stations[0] = Integer.toString(newId);                  // Replace the ID with the new value
                    map.put(stations[0], stations);                         // Store the array of values using the new ID as the key
                    newId++;                                                // Increment the new ID for the next station
                }
            }
            br.close();
        return (HashMap<String, String[]>) map;
    }
}
