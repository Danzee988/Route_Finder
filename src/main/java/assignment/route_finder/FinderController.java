package assignment.route_finder;

import Methods.GraphNodes;
//import Methods.LineDefinition;
import Methods.Stations;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
        File file = fileChooser.showOpenDialog(null);
//        File file = new File("C:\\Users\\danze\\Desktop\\1.jpg");

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

//    public static List<GraphLink2<LineDefinition>> lineArray = new ArrayList<>();

    public static HashMap<Integer, ArrayList<GraphNodes<Stations>>> lineMap = new HashMap<Integer, ArrayList<GraphNodes<Stations>>>();


    @FXML
    void findStation(MouseEvent event) throws Exception {
        readStationCsv("src\\main\\java\\London\\csv\\London.csv");
//        for (int i = 0; i < stationList.size(); i++) {
//            System.out.println(stationList.get(i).data);
//        }
        System.out.println(stationList.get(0).data.toString());

        populateStationAdjList("src\\main\\java\\London\\csv\\Lines.csv");

//        for (int i = 0; i < lineArray.size(); i++) {
//            System.out.println(lineArray.get(i).data.getStation1ID());
//        }
//        createLines(lineArray);

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
        //int newId = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length > 4 && ("1".equals(values[4].trim()) || "1.5".equals(values[4].trim()))) {
                Stations stn = new Stations(Integer.valueOf(values[0]), values[3], Float.valueOf(values[1]), Float.valueOf(values[2]));
                GraphNodes<Stations> stn1 = new GraphNodes<>(stn);
                stationList.add(stn1);
            }
        }
        br.close();

    }

    public void populateStationAdjList(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int srcId = Integer.valueOf(values[0]);
            int destId = Integer.valueOf(values[1]);
            GraphNodes<Stations> srcNode = null;
            GraphNodes<Stations> destNode = null;
            for (GraphNodes<Stations> node : stationList) {
                if (node.data.getId() == srcId) {
                    srcNode = node;
                }
                if (node.data.getId() == destId) {
                    destNode = node;
                }
            }
            if(srcNode != null && destNode != null){
               srcNode.connectToNodeUndirected(destNode);
            }
        }
        br.close();

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

        public static void traverseGraphDepthFirst(GraphNodes<?> from, List<GraphNodes<?>> encountered ){
            System.out.println(from.data);
            if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
            encountered.add(from);
            for(GraphNodes<?> adjNode : from.adjList)
                if(!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered );
        }



    //--------------------------------------Get station Methods--------------------------------------
    public static String getStationByName(String name) {
        String info = "";
        boolean printed = false;
        for (int i = 0; i < stationList.size(); i++) {
            if (stationList.get(i).data.getName().contains(name)) {
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
            int stn = stationList.get(i).data.getId();
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









