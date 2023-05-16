package assignment.route_finder;

import Methods.GraphNodes;
//import Methods.LineDefinition;
import Methods.Stations;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import java.io.*;
import java.util.*;

public class FinderController {

    @FXML
    private Button btnMain;

    @FXML
    private Button btnProcessed;

    @FXML
    private Button btnSignout;

    Image imageDefault = new Image("file:src/main/resources/assignment/route_finder/images/London_Underground_Zone_1.png");

    @FXML
    private ImageView map;

    @FXML
    private Pane pnlMain;

    @FXML
    private Pane pnlProcess;

    @FXML
    private ComboBox<Integer> routes;

    @FXML
    private ComboBox<String> startStn;

    @FXML
    private ComboBox<String> destinationStn;


    public void drawLine() {
        int height = (int) imageDefault.getHeight();
        int width = (int) imageDefault.getWidth();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = imageDefault.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = pixelReader.getColor(i, j).grayscale();
                pixelWriter.setColor(i, j, color);
            }
        }

        Canvas canvas = new Canvas(map.getImage().getWidth(), map.getImage().getHeight());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.RED);
        gc.setFill(Color.RED);

        int x1= 0,y1=0,x2=0,y2=0;

        for(int i = 0; i < chosenRoute.getItems().size(); i++) {
            Stations station1;
            Stations station2;
            for (int j = 0; j < stationList.size(); j++) {
                if (chosenRoute.getItems().get(i).equals(stationList.get(j).station.getName())) {
                    station1 = stationList.get(j).station;
                    x1 = (int) ((station1.getX() * 1.63719) + 10);
                    y1 = (int) (station1.getY() * 1.64932);
                }
                if (i + 1 < chosenRoute.getItems().size()) {
                    if (chosenRoute.getItems().get(i + 1).equals(stationList.get(j).station.getName())) {
                        station2 = stationList.get(j).station;
                        x2 = (int) ((station2.getX() * 1.63719) + 10);
                        y2 = (int) (station2.getY() * 1.64932);
                    }
                }
            }
            gc.strokeLine(x1, y1, x2, y2);
        }

        ImageView numberedImage = new ImageView(writableImage);

        Group group = new Group(numberedImage, canvas);
        numberedImage = new ImageView(group.snapshot(null, null));

        map.setImage(numberedImage.getImage());
    }


    public void handleClicks(ActionEvent actionEvent){//side panel with buttons each button corresponds to displaying a panel
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


    public static List<GraphNodes<Stations>> stationList = new ArrayList<>();

    Map<Integer, List<Stations>> lineMap = new HashMap<>();

    @FXML
    void findStation(MouseEvent event) throws Exception {
        map.setImage(imageDefault);
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
                Stations stn = new Stations(Integer.valueOf(values[0]), values[3], Float.valueOf(values[1]), Float.valueOf(values[2]),Integer.valueOf(values[5]),Integer.valueOf(values[6]));
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
            if (startNode != null && destNode != null) {                              //if both nodes are found, connect them
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
    public void routeNoTransfers(){
        stationToStationByLine();
//        routeByAnyLine();
    }

    public void shortRoute(){
        chosenRoute.getItems().clear();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);

        findShortestRoute();
        dijkstra(startNode, destNode);
    }

    public void routeAllLines(){
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);

        findAllPaths(startNode, destNode);
    }
    public void showRoute(){
        int routNum = routes.getSelectionModel().getSelectedIndex();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();

        chosenRoute.getItems().clear();

        for (GraphNodes<?> pathNode : paths.get(routNum + 1)) {
            GraphNodes<Stations> stnNode = (GraphNodes<Stations>) pathNode;
            chosenRoute.getItems().add(stnNode.station.getName());
        }

        calculateCostB(paths.get(routNum + 1).size());
        drawLine();
    }

    //--------------------------------------Methods for all paths--------------------------------------
    public List<List<GraphNodes<Stations>>> findAllPaths(GraphNodes<Stations> start, GraphNodes<Stations> destination) {
        Map<GraphNodes<Stations>, Integer> visited = new HashMap<>(); // 0 = not visited, 1 = currently visiting, 2 = visited
        List<List<GraphNodes<Stations>>> allPaths = new ArrayList<>(); // list of all paths
        List<GraphNodes<Stations>> currentPath = new ArrayList<>(); // current path being explored
        searchNodes(start, destination, visited, allPaths, currentPath); // search for all paths
        for (List<GraphNodes<Stations>> path : allPaths) { // print all paths
            for (GraphNodes<Stations> node : path) {
                System.out.print(node.station.getName());
                System.out.println(" \n");
            }
            System.out.println();
        }
        return allPaths;
    }

    private void searchNodes(GraphNodes<Stations> node, GraphNodes<Stations> destination, Map<GraphNodes<Stations>, Integer> visited,
                     List<List<GraphNodes<Stations>>> allPaths, List<GraphNodes<Stations>> currentPath) {
        visited.put(node, 1); // mark as currently visiting
        currentPath.add(node); // add to current path
        if (node.equals(destination)) { // if destination reached, add current path to list of all paths
            allPaths.add(new ArrayList<>(currentPath)); // add copy of current path
        } else {
            for (GraphNodes<Stations> neighbor : node.adjList) { // visit all neighbors
                if (!visited.containsKey(neighbor) || visited.get(neighbor) == 0) { // not yet visited
                    searchNodes(neighbor, destination, visited, allPaths, currentPath); // recursively search
                } else if (visited.get(neighbor) == 1) { // currently visiting, cycle detected
                    System.out.println("Cycle detected at node " + neighbor);
                }
            }
        }
        visited.put(node, 2); // mark as visited
        currentPath.remove(currentPath.size() - 1); // remove from current path
        System.out.println(allPaths.size() + " number of paths" );
    }

    //--------------------------------------Calculation Methods--------------------------------------
    //--------------------------------------Cost Methods---------------------------------------------
    @FXML
    private Text cost;

    @FXML
    private Text totalRoutes;

    public double calculateCost(GraphNodes<Stations> node1, GraphNodes<Stations> node2) {
        double lat1 = node1.station.getLatitude();
        double lon1 = node1.station.getLongitude();
        double lat2 = node2.station.getLatitude();
        double lon2 = node2.station.getLongitude();
        double distance = distance(lat1, lon1, lat2, lon2);
        double cost = distance * 1.2;
        return cost;
    }


    public void calculateCostB(int x){
        int routNum = routes.getSelectionModel().getSelectedIndex();

        int routSize =  x;
        List<Double> costs = new ArrayList<>();

        for (int k = 0; k < routSize - 1; k++) {
            GraphNodes<Stations> stnNode1 = paths.get(routNum + 1).get(k); //get the first station
            GraphNodes<Stations> stnNode2 = paths.get(routNum + 1).get(k + 1); //get the second station
            double lat1 = stnNode1.station.getLatitude();
            double lon1 = stnNode1.station.getLongitude();
            double lat2 = stnNode2.station.getLatitude();
            double lon2 = stnNode2.station.getLongitude();
            double distance = distance(lat1, lon1, lat2, lon2); //calculate distance between the two stations
            double cost = distance * 1.2; //calculate cost of the route between the two stations
            costs.add(cost); //add the cost to the list of costs
        }

        double totalCost = costs.stream().mapToDouble(Double::doubleValue).sum(); //calculate the total cost of the route
        double roundedCost = Math.round(totalCost * 100.0) / 100.0; //round the cost to two decimal places
        cost.setText("Route Cost: €" + roundedCost); //display the cost of the route
        int total = costs.size() + 1; //calculate the total number of stations in the route
        totalRoutes.setText("Total Routes: " + total); //display the total number of stations in the route
        System.out.println("Total Cost: " + roundedCost);
    }


    public static final double RADIUS_OF_EARTH = 6371; // Earth's radius in kilometers

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1); // difference in latitude
        double dLon = Math.toRadians(lon2 - lon1); // difference in longitude

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2); // haversine formula (spherical law of cosines)

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // great circle distance in radians

        return RADIUS_OF_EARTH * c; // great circle distance in kilometers (approximately)
    }

//---------------------------------------------------------------------------------------



    //--------------------------------------Route Finding Methods--------------------------------------
    //--------------------------------------All Routes Methods-----------------------------------------
    @FXML
    public ListView<String> pathsList;

    @FXML
    public ListView<String> chosenRoute;
    Map<Integer, List<GraphNodes<Stations>>> paths = new HashMap<>();

    //find the line that the two stations are on
    public void stationToStationByLine() {
        routes.getItems().clear();
        pathsList.getItems().clear();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);
        List<Stations> line;
        List<GraphNodes<Stations>> encountered = new ArrayList<>();
        paths.clear();

        int numPath = 0;

        for (int k = 0; k < lineMap.size(); k++) {//loop through the different lines in the hash map
            if (lineMap.get(k) != null) {
                for (int l = 0; l < lineMap.get(k).size(); l++) {//loop through the stations in the line
                    if (lineMap.get(k).get(l).getId() == startNode.station.getId()) {//if the station is the start station
                        for (int m = 0; m < lineMap.get(k).size(); m++) {//loop through the stations in the line
                            if (lineMap.get(k).get(m).getId() == destNode.station.getId()) {//if the station is the destination station
                                line = lineMap.get(k);//get the line
                                System.out.println(encountered.size() + " Encountered size");

                                traverseGraphByLine(startNode, destNode, encountered, line);

                                List<GraphNodes<Stations>> path = new ArrayList<>();


                                if (encountered != null) {
                                    for (GraphNodes<Stations> node : encountered) {
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

                                            if (!paths.isEmpty()) {
                                                boolean pathExists = false;
                                                for (List<GraphNodes<Stations>> n : paths.values()) {
                                                    if (n.equals(path)) {
                                                        pathExists = true;
                                                        numPath--;
                                                    }
                                                }
                                                if (!pathExists) {
                                                    pathsList.getItems().add("Route " + numPath + ": " + path.size() + " stops");
                                                    for (GraphNodes<Stations> pathNode : path) {
                                                        GraphNodes<Stations> station = pathNode;
                                                        pathsList.getItems().add(station.station.getName());
                                                    }
                                                    pathsList.getItems().add(" ");
                                                    paths.put(numPath, path);
                                                    routes.getItems().add(numPath);
                                                }
                                            } else {
                                                pathsList.getItems().add("Route " + numPath + ": " + path.size() + " stops");
                                                for (GraphNodes<?> pathNode : path) {
                                                    GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;
                                                    pathsList.getItems().add(station.station.getName());
                                                }
                                                pathsList.getItems().add(" ");
                                                paths.put(numPath, path);
                                                routes.getItems().add(numPath);

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


    public void routeByAnyLine() {
        routes.getItems().clear();
        pathsList.getItems().clear();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);
        List<Stations> line;
        List<GraphNodes<Stations>> encountered = new ArrayList<>();
        paths.clear();

        int numPath = 0;


        for (int k = 0; k < lineMap.size(); k++) {
            if (lineMap.get(k) != null) {
                for (int l = 0; l < lineMap.get(k).size(); l++) {
                    if (lineMap.get(k).get(l).getId() == startNode.station.getId()) {
                        for (int m = 0; m < lineMap.get(k).size(); m++) {

                            if (lineMap.get(k).get(m).getId() == destNode.station.getId()) {
                                line = lineMap.get(k);

                                traverseGraphByLine(startNode, destNode, encountered, line);

                                List<GraphNodes<Stations>> path = new ArrayList<>();

                                if (encountered != null) {
                                    for (GraphNodes<Stations> node : encountered) {
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

                                            if (!paths.isEmpty()) {
                                                boolean pathExists = false;
                                                for (List<GraphNodes<Stations>> n : paths.values()) {
                                                    if (n.equals(path)) {
                                                        pathExists = true;
                                                        numPath--;
                                                    }
                                                }
                                                if (!pathExists) {
                                                    pathsList.getItems().add("Route " + numPath + ": " + path.size() + " stops");
                                                    for (GraphNodes<?> pathNode : path) {
                                                        GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;
                                                        pathsList.getItems().add(station.station.getName());
                                                    }
                                                    pathsList.getItems().add(" ");
                                                    paths.put(numPath, path);
                                                    routes.getItems().add(numPath);
                                                }
                                            } else {
                                                pathsList.getItems().add("Route " + numPath + ": " + path.size() + " stops");
                                                for (GraphNodes<?> pathNode : path) {
                                                    GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;
                                                    pathsList.getItems().add(station.station.getName());
                                                }
                                                pathsList.getItems().add(" ");
                                                paths.put(numPath, path);
                                                routes.getItems().add(numPath);

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
        List<GraphNodes<Stations>> encountered2 = new ArrayList<>();

        List<GraphNodes<Stations>> path = new ArrayList<>();

        List<GraphNodes<Stations>> path2 = null;

        findNodesOnOneLine(startNode, destNode, encountered2);

        int startNodeNumber = 0;
        int destNodeNumber;


        for(int z = 0; z < encountered2.size(); z++){
            if(encountered2.get(z) == startNode){
                path2 = new ArrayList<>();
                path = new ArrayList<>();
                path2.add(encountered2.get(z));
                path.add(encountered2.get(z));
                startNodeNumber = z;
            }
            if(encountered2.get(z) == destNode){
                destNodeNumber = z;

                int back = destNodeNumber-startNodeNumber;

                for(int y = 0; y < back; y++){
                    path2.add(encountered2.get(startNodeNumber+y));
                }
                for(int x = 0; x < path2.size(); x++){
                    if(x+1 < path2.size()){
                        if(path2.get(x).adjList.contains(path2.get(x+1))){
                            if(!path.contains(path2.get(x))) {
                                path.add(path2.get(x));
                            }
                        }else{
                            List<GraphNodes<Stations>> pathOnLine = new ArrayList<>();
                            List<Stations> lineNum = null;
                            for(int w = 0; w < lineMap.size(); w++){
                                if(lineMap.get(w) != null){
                                    if(lineMap.get(w).contains(path2.get(x).station) && lineMap.get(w).contains(path2.get(x+1).station)){
                                        lineNum = lineMap.get(w);
                                    }
                                }
                            }
                            traverseGraphByLine(path2.get(x), path2.get(x+1), pathOnLine, lineNum);

                            for (int w = 0; w < pathOnLine.size(); w++){
                                if(!path.contains(pathOnLine.get(w))) {
                                    path.add(pathOnLine.get(w));
                                }
                            }
                        }
                    }
                }
                path.add(encountered2.get(destNodeNumber));

                System.out.println(path);

                if (!paths.isEmpty()) {
                    boolean pathExists = false;
                    for (List<GraphNodes<Stations>> n : paths.values()) {
                        if (n.equals(path)) {
                            pathExists = true;
                            numPath--;
                        }
                    }
                    if (!pathExists) {
                        pathsList.getItems().add("Route " + numPath + ": " + path.size() + " stops");
                        for (GraphNodes<Stations> pathNode : path) {
                            GraphNodes<Stations> station = pathNode;
                            pathsList.getItems().add(station.station.getName());
                        }
                        pathsList.getItems().add(" ");
                        paths.put(numPath, path);
                        routes.getItems().add(numPath);
                    }
                }else {
                    pathsList.getItems().add("Route " + numPath + ": " + path.size() + " stops");
                    for (GraphNodes<Stations> pathNode : path) {
                        GraphNodes<Stations> station = pathNode;
                        pathsList.getItems().add(station.station.getName());
                    }
                    pathsList.getItems().add(" ");
                    paths.put(numPath, path);
                    routes.getItems().add(numPath);
                }
            }
        }
    }


    public void findNodesOnOneLine(GraphNodes<Stations> node1, GraphNodes<Stations> node2, List<GraphNodes<Stations>> encountered2) {
        if (encountered2 == null) {
            encountered2 = new ArrayList<>();
        }

        for (int i = 0; i < lineMap.size(); i++) {

            for (GraphNodes<Stations> adjNode : node1.adjList) {
                if (lineMap.get(i) != null) {

                    if (lineMap.get(i).contains(adjNode.station) && lineMap.get(i).contains(node2.station)) {
                        encountered2.add(node1);
                        encountered2.add(adjNode);
                        encountered2.add(node2);
                    } else {
                        for (GraphNodes<Stations> adjNode2 : node2.adjList) {
                            if (lineMap.get(i) != null) {
                                if (lineMap.get(i).contains(adjNode2.station) && lineMap.get(i).contains(adjNode.station)) {
                                    encountered2.add(node1);
                                    encountered2.add(adjNode);
                                    if(adjNode != adjNode2) {
                                        encountered2.add(adjNode2);
                                    }
                                    encountered2.add(node2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }




    //traverse graph from one station to the next
    public void traverseGraphByLine(GraphNodes<Stations> from, GraphNodes<Stations> to, List<GraphNodes<Stations>> encountered, List<Stations> line){
        if(line != null) {
            if (encountered == null)
                encountered = new ArrayList<>(); //First node so create new (empty) encountered list
            encountered.add(from);
//            System.out.println(from.station.getName());

            for (GraphNodes<Stations> adjNode : from.adjList) {
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

    //--------------------------------------Shortest Routes Methods-----------------------------------------
    public void findShortestRoute() {
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
                                            System.out.println(2);
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
                                        paths.put(numPath, shortestPath);
                                    }
                                }else if(paths.get(0) != shortestPath || paths.isEmpty()){
                                    numPath++;
                                    pathsList.getItems().add("Route " + numPath + ": " + shortestPath.size() + " stops");
                                    for (GraphNodes<?> pathNode : shortestPath) {
                                        GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;

                                        pathsList.getItems().add(station.station.getName());
                                    }
                                    paths.put(numPath, shortestPath);

                                    pathsList.getItems().add(" ");
                                    routes.getItems().add(numPath);
                                }
                            }
                        }
                    }
                }
            }
        }
        //calculateCost(paths.get(1).size());
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

    //--------------------------------------Dijkstra Methods-----------------------------------------
    public Pair<List<GraphNodes<?>>, Double> dijkstra(GraphNodes<?> startNode, GraphNodes<?> destNode) {
        Map<GraphNodes<?>, Double> distance = new HashMap<>();
        PriorityQueue<GraphNodes<?>> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        Map<GraphNodes<?>, GraphNodes<?>> parent = new HashMap<>();

        for (GraphNodes<?> node : stationList) {
            distance.put(node, Double.POSITIVE_INFINITY);
        }
        distance.put(startNode, 0.0);
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            GraphNodes<?> current = queue.poll();

            if (current == destNode) { // Found the destination node
                List<GraphNodes<?>> path = new ArrayList<>(); // Reconstruct the shortest path
                GraphNodes<?> node = destNode; // from the parent map
                while (node != null) { // backtracking from the destination node
                    path.add(0, node);  // to the start node
                    node = parent.get(node); // through its parents
                }
                double amount = distance.get(destNode); // The cost is the distance to the destination node
                System.out.println("The shortest path from " + startNode + " to " + destNode + " is " + path + " with cost " + amount);
                totalRoutes.setText("Total Routes: " + path.size());
                double roundedCost = Math.round(amount * 100.0) / 100.0;
                cost.setText("Route Cost: €" + roundedCost);
                return new Pair<>(path, amount); // Return the path and its cost
            }

            for (GraphNodes<?> neighbor : current.adjList) { // For each neighbor of the current node
                double newDistance = distance.get(current) + calculateCost((GraphNodes<Stations>) current, (GraphNodes<Stations>) neighbor); // Calculate the new distance
                if (newDistance < distance.get(neighbor)) { // If the new distance is less than the current distance
                    distance.put(neighbor, newDistance); // Update the distance map
                    parent.put(neighbor, current); // Update the parent map
                    queue.offer(neighbor); // Offer the neighbor to the queue
                }
            }
        }

        return null;
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









