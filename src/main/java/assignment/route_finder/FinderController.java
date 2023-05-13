package assignment.route_finder;

import Methods.GraphNodes;
//import Methods.LineDefinition;
import Methods.Stations;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;

//import static Methods.GraphTraversal.traverseGraphDepthFirst;

public class FinderController {

    @FXML
    private Button btnMain;

    @FXML
    private Button btnProcessed;

    @FXML
    private Button btnSignout;

    @FXML
    private ImageView map;

    @FXML
    private Button overview;

    @FXML
    private Pane pnlMain;

    @FXML
    private Pane pnlProcess;

    @FXML
    private ImageView scanndedMap;

    @FXML
    private ComboBox<Integer> routes;

    @FXML
    private ComboBox<String> startStn;

    @FXML
    private ComboBox<String> destinationStn;


    public void handleClicks(ActionEvent actionEvent) throws IOException {//side panel with buttons each button corresponds to displaying a panel
        if (actionEvent.getSource() == btnMain) {
            pnlMain.setVisible(true);
            pnlProcess.setVisible(false);
        }
        if (actionEvent.getSource() == btnProcessed) {
            pnlMain.setVisible(false);
            pnlProcess.setVisible(true);
        }
        if (actionEvent.getSource() == btnSignout) {
            Platform.exit();
        }
    }//handles all the buttons on the left side of the screen

    @FXML
    private TextField stationID;

    public static List<GraphNodes<Stations>> stationList = new ArrayList<>();

    Map<Integer, List<Stations>> lineMap = new HashMap<>();

    @FXML
    void findStation(MouseEvent event) throws Exception {
        readStationCsv("src\\main\\java\\London\\csv\\London.csv");

        populateStationAdjList("src\\main\\java\\London\\csv\\Lines.csv");
    }

    //--------------------------------------Methods--------------------------------------

    public void readStationCsv(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length > 4 && ("1".equals(values[4].trim()) || "1.5".equals(values[4].trim()))) {
                Stations stn = new Stations(Integer.valueOf(values[0]), values[3], Float.valueOf(values[1]), Float.valueOf(values[2]));
                GraphNodes<Stations> stn1 = new GraphNodes<>(stn);
                stationList.add(stn1);
                startStn.getItems().add(values[3]);
                destinationStn.getItems().add(values[3]);
            }
        }
        br.close();
    }

    public void populateStationAdjList(String path) throws Exception {              //populates the adjacency list with line definitions CSV
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int startId = Integer.valueOf(values[0]);                               //start station id
            int destId = Integer.valueOf(values[1]);                                //destination station id
            int lineNum = Integer.valueOf(values[2]);                               //line number

            GraphNodes<Stations> startNode = null;
            GraphNodes<Stations> destNode = null;
            for (GraphNodes<Stations> node : stationList) {                         //finds the start and destination nodes
                if (node.station.getId() == startId) {
                    startNode = node;
                }
                if (node.station.getId() == destId) {
                    destNode = node;
                }
            }
            if(startNode != null && destNode != null){                              //if both nodes are found, connect them
               startNode.connectToNodeUndirected(destNode, lineNum);
            }

            // Find the line number of the current station
            int stationLineNum = -1;
            for (GraphNodes<Stations> node : stationList) {                         //finds the start and destination nodes
                if (node.station.getId() == startId) {
                    stationLineNum = lineNum;
                    break;
                }
            }

            // Add the station to the appropriate list
            if (stationLineNum != -1) {
                List<Stations> lineStations = lineMap.get(stationLineNum);          //get the list of stations for the current line
                if (lineStations == null) {                                         //if the list is null, create a new list
                    lineStations = new ArrayList<>();
                    lineMap.put(stationLineNum, lineStations);                      //add the new list to the map
                }
                Stations station1 = startNode.station;
                Stations station2 = destNode != null ? destNode.station : null;

                if (!lineStations.contains(station1)) {                             //if the list doesn't contain the station, add it
                    lineStations.add(station1);
                }
                if (station2 != null && !lineStations.contains(station2)) {         //if the list doesn't contain the station, add it
                    lineStations.add(station2);
                }
            }
        }

        br.close();
    }

    //travers the route from one station to the next
    public void takeRoute(){
        //stationToStationByLine();
        stationToStationAllLine();
    }
    public void showRoute(){
//        int routNum = routes.getSelectionModel().getSelectedIndex();
//        chosenRoute.getItems().clear();
//        System.out.println(routNum + " Rout num");
//        System.out.println(path + " Path");
//        for(int i = 0; i < path.size(); i++){
//            System.out.println(path.size() + " Path size");
//            chosenRoute.getItems().add(String.valueOf(path.get(routNum).station));
//        }
        calculateCost();
    }

//    @FXML
//    private ListView<String> cost;

    @FXML
    private Text cost;

    public void calculateCost(){
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);

        int routSize = 4;

        double distance = distance(startNode.station.getLatitude(), startNode.station.getLongitude(), destNode.station.getLatitude(), destNode.station.getLongitude());
        double costOfTravel = distance * routSize;
        double roundedCost = Math.round(costOfTravel * 100.0) / 100.0;

        cost.setText("Route Cost: €" + roundedCost);
        System.out.println(costOfTravel + " Cost of travel");


    }

    public static final double RADIUS_OF_EARTH = 6371; // Earth's radius in kilometers

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS_OF_EARTH * c;
    }


    public void drawRoute(){
        double height = map.getFitHeight();
        double width = map.getFitWidth();

        System.out.println("Height: " + height);
        System.out.println("Width: " + width);

        System.out.println(stationList.get(0).station.getLatitude());
    }

    @FXML
    public ListView<String> pathsList;

    @FXML
    public ListView<String> chosenRoute;
    List<GraphNodes<?>> path = new ArrayList<>();

    //find the line that the two stations are on
    public void stationToStationByLine(){
        pathsList.getItems().clear();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);
        List<Stations> line;
        List<GraphNodes<?>> encountered = new ArrayList<>();

        Map<Integer, List<GraphNodes<?>>> paths = new HashMap<>();

        int numPath = 0;

        for(int k = 0; k < lineMap.size(); k++) {
            if (lineMap.get(k) != null) {
                for (int l = 0; l < lineMap.get(k).size(); l++) {
                    if (lineMap.get(k).get(l).getId() == startNode.station.getId()) {
                        for (int m = 0; m < lineMap.get(k).size(); m++) {
                            if (lineMap.get(k).get(m).getId() == destNode.station.getId()) {
                                line = lineMap.get(k);

                                traverseGraphByLine(startNode, destNode, encountered, line);

                                List<GraphNodes<?>> path = new ArrayList<>();


                                if (encountered != null) {
                                    for (GraphNodes<?> node : encountered) {
                                        if (startNode.station == node.station) {
                                            path = new ArrayList<>();
                                            path.add(node);
                                        } else if (startNode.adjList.contains(node)) {
                                            path = new ArrayList<>();
                                            path.add(startNode);
                                            path.add(node);
                                        } else if (destNode.station == node.station) {
                                            path.add(node);
                                            numPath++;

                                            if(!paths.isEmpty()) {
                                                boolean pathExists = false;
                                                for(List<GraphNodes<?>> n : paths.values()) {
                                                    if(n.equals(path)) {
                                                        pathExists = true;
                                                        numPath--;
                                                    }
                                                }
                                                if(!pathExists) {
                                                    pathsList.getItems().add("Route " + numPath + ": ");
                                                    for (GraphNodes<?> pathNode : path) {
                                                        GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;
                                                        pathsList.getItems().add(station.station.getName());
                                                    }
                                                    pathsList.getItems().add(" ");
                                                    paths.put(numPath, path);
                                                }
                                            }else {
                                                pathsList.getItems().add("Route " + numPath + ": ");
                                                for (GraphNodes<?> pathNode : path) {
                                                    GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;
                                                    pathsList.getItems().add(station.station.getName());
                                                }
                                                pathsList.getItems().add(" ");
                                                paths.put(numPath, path);
                                            }
                                        } else {
                                            path.add(node);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    //traverse graph from one station to the next
    public void traverseGraphByLine(GraphNodes<?> from, GraphNodes<?> to, List<GraphNodes<?>> encountered, List<Stations> line){
        if(line != null) {
            if (encountered == null)
                encountered = new ArrayList<>(); //First node so create new (empty) encountered list
            encountered.add(from);

            for (GraphNodes<?> adjNode : from.adjList) {
                if (line.contains(adjNode.station)) {
                    if (adjNode.station == to.station) {
                        encountered.add(adjNode);
                        return;
                    } else if (!encountered.contains(adjNode))
                        traverseGraphByLine(adjNode, to, encountered, line);
                }
            }
        }
    }

    public void stationToStationAllLine() {
        routes.getItems().clear();
        pathsList.getItems().clear();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);
        List<Stations> line;
        Queue<List<GraphNodes<?>>> queue = new LinkedList<>();

        Map<Integer, List<GraphNodes<?>>> paths = new HashMap<>();

        int numPath = 0;

        for (int k = 0; k < lineMap.size(); k++) {
            if (lineMap.get(k) != null) {
                for (int l = 0; l < lineMap.get(k).size(); l++) {
                    if (lineMap.get(k).get(l).getId() == startNode.station.getId()) {
                        for (int m = 0; m < lineMap.get(k).size(); m++) {
                            if (lineMap.get(k).get(m).getId() == destNode.station.getId()) {
                                line = lineMap.get(k);

                                List<GraphNodes<?>> shortestPath = traverseGraphByLineB(startNode, destNode, line, queue);

                                if (shortestPath.isEmpty()) {
                                    pathsList.getItems().add("No route found on single line");
                                } else if(!paths.isEmpty()){
                                    numPath++;
                                    boolean pathExists = false;
                                    for(List<GraphNodes<?>> n : paths.values()) {
                                        if(n.equals(shortestPath)) {
                                            pathExists = true;
                                            numPath--;
                                        }
                                    }

                                    if(!pathExists) {
                                        pathsList.getItems().add("Route " + numPath + ": " + shortestPath.size() + " stops");
                                        for (GraphNodes<?> pathNode : shortestPath) {
                                            GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;

                                            pathsList.getItems().add(station.station.getName());
                                        }

                                        pathsList.getItems().add(" ");
                                        routes.getItems().add(numPath);
                                    }
                                }else if(paths.isEmpty()){
                                    numPath++;
                                    pathsList.getItems().add("Route " + numPath + ": " + shortestPath.size() + " stops");
                                    for (GraphNodes<?> pathNode : shortestPath) {
                                        GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;

                                        pathsList.getItems().add(station.station.getName());
                                    }

                                    pathsList.getItems().add(" ");
                                    routes.getItems().add(numPath);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public List<GraphNodes<?>> traverseGraphByLineB(GraphNodes<?> from, GraphNodes<?> to, List<Stations> line, Queue<List<GraphNodes<?>>> queue) {
        List<GraphNodes<?>> shortestRoute = new ArrayList<>();
        Set<GraphNodes<?>> encountered = new HashSet<>();
        encountered.add(from);

        List<GraphNodes<?>> initialPath = new ArrayList<>();
        initialPath.add(from);
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            List<GraphNodes<?>> currentPath = queue.poll();
            GraphNodes<?> currentNode = currentPath.get(currentPath.size() - 1);

            if (currentNode == to) {
                // Found the destination node, return the current path
                return currentPath;
            }

            for (GraphNodes<?> adjNode : currentNode.adjList) {
                if (line.contains(adjNode.station) && !encountered.contains(adjNode)) {
                    List<GraphNodes<?>> newPath = new ArrayList<>(currentPath);
                    newPath.add(adjNode);
                    queue.add(newPath);
                    encountered.add(adjNode);
                }
            }
        }

        // If no path is found, return an empty list
        return shortestRoute;
    }



    //--------------------------------------Get station Methods--------------------------------------
    public static String getStationByName(String name) {
        String info = "";
        boolean printed = false;
        for (int i = 0; i < stationList.size(); i++) {
            if (stationList.get(i).station.getName().contains(name)) {
                if(!printed) {
                    System.out.println(stationList.get(i));
                    info = stationList.get(i).toString();
                }
            }
        }
        return info;
    }

    public static String getStationById(int id) {
        boolean printed = false;
        String info = "";
        for (int i = 0; i < stationList.size(); i++) {
            int stn = stationList.get(i).station.getId();
            if (stn == Integer.valueOf(id)) {
                if(!printed) {
                    System.out.println(stationList.get(i));
                    info = stationList.get(i).toString();
                }
                printed = true;
            }
        }

        return info;
    }



}









