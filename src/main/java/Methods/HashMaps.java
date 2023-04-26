package Methods;

import java.util.HashMap;

public class HashMaps {
    HashMap<String, GraphNodes<Stations>> stationsIdHashMap = new HashMap<>();
    HashMap<String, GraphNodes<Stations>> stationsNameHashMap = new HashMap<>();


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
