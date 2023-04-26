package Methods;

import java.util.HashMap;
import java.util.List;

public class HashMaps {
    HashMap<Integer, GraphNodes<Stations>> stationsIdHashMap = new HashMap<>();
    HashMap<String, GraphNodes<Stations>> stationsNameHashMap = new HashMap<>();

    public void createStationMaps(List<Stations> stations){
        for (Stations station : stations) {
            int stationId = station.getId();
            String stationName = station.getName();

            GraphNodes<Stations> stationsGraph = new GraphNodes<>();
            stationsIdHashMap.put(stationId, stationsGraph);
            stationsNameHashMap.put(stationName, stationsGraph);
        }
    }




//    public void buildStationsHashMaps(List<Stations> stations) {
//        for (Stations station : stations) {
//            int id = GraphNodes.getStationById(station);
//            String name = Methods.Stations.getStationByName(station.getName());
//
//            GraphNodes<Stations> stationsGraph = new GraphNodes<>();
//
//            stationsIdHashMap.put(id, stationsGraph);
//            stationsNameHashMap.put(name, stationsGraph);
//        }
//    }
}
