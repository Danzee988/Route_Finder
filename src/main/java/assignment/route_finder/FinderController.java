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
    private ComboBox<?> selectImage;

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

    public void Chooser() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
//        File file = fileChooser.showOpenDialog(null);
        File file = new File("C:\\Users\\danze\\Desktop\\1.jpg");

        Image image1 = new Image(String.valueOf(file));
        map.setImage(image1);

        int height=(int)image1.getHeight();
        int width=(int)image1.getWidth();
        PixelReader pixelReader=image1.getPixelReader();
        WritableImage writableImage = new WritableImage(width,height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }

//        map = image1;
//        writableImage1 = writableImage;
    }

    @FXML
    void pickImg(MouseEvent event) throws IOException {
        Chooser();

    }

    @FXML
    private TextField stationID;

    public static List<GraphNodes<Stations>> stationList = new ArrayList<>();

    Map<Integer, List<Stations>> lineMap = new HashMap<>();


//    public static List<GraphNodes<Stations>> lineList = new ArrayList<>();

//    public static List<GraphLink2<LineDefinition>> lineArray = new ArrayList<>();

//    public static HashMap<Integer, ArrayList<GraphNodes<Stations>>> lineMap = new HashMap<Integer, ArrayList<GraphNodes<Stations>>>();


    @FXML
    void findStation(MouseEvent event) throws Exception {
        readStationCsv("src\\main\\java\\London\\csv\\London.csv");

        populateStationAdjList("src\\main\\java\\London\\csv\\Lines.csv");

//        System.out.println("Recursive depth first traversal starting at Orange");
//        System.out.println("-------------------------------------------------");
//        traverseGraphDepthFirst(stationList.get(1),null);
//        System.out.println(stationList.toString());
    }

    //--------------------------------------Methods--------------------------------------

//    public List<GraphNodes<Stations>> readCsv(String path) throws Exception {
//        BufferedReader br = new BufferedReader(new FileReader(path));
//        String line;
//        //int newId = 1;
//        while ((line = br.readLine()) != null) {
//            String[] values = line.split(",");
//            if (values.length > 4 && ("1".equals(values[4].trim()) || "1.5".equals(values[4].trim()))) {
//                Stations stn = new Stations(Integer.valueOf(values[0]), values[3], Float.valueOf(values[1]), Float.valueOf(values[2]));
//                GraphNodes<Stations> stn1 = new GraphNodes<>(stn);
//                stationList.add(stn1);
//            }
//        }
//        br.close();
//        return stationList;
//    }

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
//        for (Map.Entry<Integer, List<Stations>> entry : lineMap.entrySet()) {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue().toString());
//        }

    }

    //travers the route from one station to the next
    public void takeRoute(){
        int i = startStn.getSelectionModel().getSelectedIndex();
//        GraphNodes<Stations> startNode = stationList.get(i);
        stationToStationByLine();

    }



//    public List<GraphLink2<LineDefinition>> readLineDefinitions(String path) throws Exception {
//        BufferedReader br = new BufferedReader(new FileReader(path));
//        String line;
//
//        while ((line = br.readLine()) != null) {
//            String[] values = line.split(",");
//            LineDefinition lineDef = new LineDefinition(Integer.valueOf(values[0]), Integer.valueOf(values[1]),Integer.valueOf(values[2]));
//            GraphLink2<LineDefinition> info = new GraphLink2<>(lineDef);
//            lineArray.add(info);
//        }
//        br.close();
//        return lineArray;
//    }
//
//    public void createLines(List<GraphLink2<LineDefinition>> lineDefinitions){
//        GraphNodes<Stations> station1Node = null;
//        GraphNodes<Stations> station2Node = null;
//
//        for (GraphLink2<LineDefinition> connection : lineDefinitions) {
//            int station1 = connection.data.getStation1ID();
//            int station2 = connection.data.getStation2ID();
//            int lineNum = connection.data.getLineID();
//
//            //GraphNode ----------------------------------------------
//            for (int i = 0; i < stationList.size(); i++) {
//                if (stationList.get(i).data.getId() == station1) {
//                    System.out.println(stationList.get(i).data + " IN SIDE LOOP");
//                    station1Node = stationList.get(i);
//
//                }
//                if (stationList.get(i).data.getId() == station2) {
//                    station2Node = stationList.get(i);
//                }
//            }
//            //System.out.println(station1Node.data);
//            //-------------------------------------------------------
//            GraphNodes<Stations> first = new GraphNodes(station1Node, lineNum);
//            GraphNodes<Stations> second = new GraphNodes(station2Node, lineNum);
//
////            if (!lineMap.containsKey(lineNum)) {
////                lineMap.put(lineNum, new ArrayList<GraphNodes<Stations>>());
////            }
////            if (!lineMap.get(lineNum).contains(station1Node) && !lineMap.get(lineNum).contains(station2Node)) {
////                lineMap.get(lineNum).add(lineNum, new ArrayList<>(first));
////                lineMap.get(lineNum).add(second);
////            }
//            station1Node.connectToNodeUndirected(second);
//            station2Node.connectToNodeUndirected(first);
//
////            System.out.println("Recursive depth first traversal starting at Orange");
////            System.out.println("-------------------------------------------------");
////            traverseGraphDepthFirst(station1Node.adjList.get(1), new ArrayList<>());
//
//        }
//        System.out.println(station1Node.data + " OUTSIDE LOOP");
//
//    }

    @FXML
    public ListView<String> pathsList;

    //find the line that the two stations are on
    public void stationToStationByLine(){
        pathsList.getItems().clear();
        int i = startStn.getSelectionModel().getSelectedIndex();
        int j = destinationStn.getSelectionModel().getSelectedIndex();
        GraphNodes<Stations> startNode = stationList.get(i);
        GraphNodes<Stations> destNode = stationList.get(j);
        List<Stations> line;
        List<GraphNodes<?>> encountered = new ArrayList<>();

        int numPath = 0;

        for(int k = 0; k < lineMap.size(); k++){
            if(lineMap.get(k) != null) {
                for (int l = 0; l < lineMap.get(k).size(); l++) {
                    if (lineMap.get(k).get(l).getId() == startNode.station.getId()) {
                        for(int m = 0; m < lineMap.get(k).size(); m++){
                            if(lineMap.get(k).get(m).getId() == destNode.station.getId()){
                                line = lineMap.get(k);

                                traverseGraphByLine(startNode, destNode, encountered, line);

                                List<GraphNodes<?>> path = new ArrayList<>();


                                //TODO: fix this. Prints out the first path twice
                                if(encountered.isEmpty()){
                                    pathsList.getItems().add("No route found on single line");
                                }else if(encountered != null) {
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
                                            pathsList.getItems().add("Route " + numPath + ": ");
                                            for (GraphNodes<?> pathNode : path) {
                                                GraphNodes<Stations> station = (GraphNodes<Stations>) pathNode;
                                                pathsList.getItems().add(station.station.getName());
                                            }
                                            pathsList.getItems().add(" ");
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





    public static void traverseGraphDepthFirst(GraphNodes<?> from, List<GraphNodes<?>> encountered ) {
        System.out.println(from.station);
        if (encountered == null)
            encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);
        for (GraphNodes<?> adjNode : from.adjList)
            if (!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered);
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









