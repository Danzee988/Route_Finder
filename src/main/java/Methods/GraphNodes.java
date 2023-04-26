package Methods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class GraphNodes<T> {
    public T data;

     HashMap<String, GraphNodes<T>> stationsIdHashMap = new HashMap<>();
     HashMap<String, GraphNodes<T>> stationsNameHashMap = new HashMap<>();

    static List<Stations> result = new ArrayList<Stations>();
    static List<Object> lineArray = new ArrayList<>();

    public List<GraphNodes<T>> adjacentNodes = new ArrayList<>();

    public GraphNodes() {
        this.data = data;
    }

    public void addAdjacentNode(GraphNodes<T> adjacent) {
        adjacentNodes.add(adjacent);
        adjacent.adjacentNodes.add(this);
    }

    public List<Stations> readCsv(String path) throws Exception {
        //List<String[]> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        //int newId = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length > 4 && ("1".equals(values[4].trim()) || "1.5".equals(values[4].trim()))) {
//                values[0] = Integer.toString(newId);
//                newId++;
                Stations stations = new Stations(Integer.valueOf(values[0]), values[3], Float.valueOf(values[1]), Float.valueOf(values[2]));
                result.add(stations);
            }
        }
        br.close();
        return result;
    }


    public List<Object> readLineDefinitions(String path) throws Exception {
        //List<String[]> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Integer> ln = new ArrayList<>();
            for (int j = 0; j < values.length; j++) {
                ln.add(Integer.valueOf(values[j]));
            }

            if (values.length >= 3) {
                lineArray.add(ln);
            }
        }
        br.close();
        System.out.println(lineArray);
        return lineArray;
    }



    public static String getStationByName(String name) {
        String info = "";
        boolean printed = false;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getName().equals(name)) { // Assuming the station name is stored in the second column
                if(!printed) {
                    System.out.println(result.get(i));
                    info = result.get(i).toString();
                }
            }
        }
        return info;
    }

    public static String getStationById(String id) {
        boolean printed = false;
        String info = "";
        for (int i = 0; i < result.size(); i++) {
            int stn = result.get(i).getId();
            if (stn == Integer.valueOf(id)) { // Assuming the station name is stored in the second column
                if(!printed) {
                    System.out.println(result.get(i));
                    info = result.get(i).toString();
                }
                printed = true;
            }
        }

        return info;
    }
}
