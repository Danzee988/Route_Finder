package assignment.route_finder;

import Methods.GraphNodes;
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

    @FXML
    void findStation(MouseEvent event) throws Exception {
        GraphNodes graphNodes = new GraphNodes();
        graphNodes.readCsv("C:\\Users\\janbi\\IdeaProjects\\Route_Finder\\src\\main\\java\\London\\csv\\London.csv");

//        GraphNodes.getStationById(stationID.getText());

        graphNodes.readLineDefinitions("C:\\Users\\janbi\\IdeaProjects\\Route_Finder\\src\\main\\java\\London\\csv\\Lines.csv");

//        graphNodes.buildStationsHashMaps(Stations.readCsv("/Users/danze/Desktop/London.csv"));
//        HashMap<Integer, List<String>> stations =
//
//        for (Object key : graphNodes.stationsIdHashMap.keySet()) {
//            Object str = Arrays.toString(new Object[]{graphNodes.stationsIdHashMap.get(key)});
//            if (key.equals(stationID.getText())) {
//                System.out.println("Station: " + str);
//            }
//        }
//        List<String[]> path = Stations.readCsv("/Users/danze/Desktop/London.csv");
//        System.out.println(path);

//        for (String[] station : GraphNodes.readCsv("/Users/danze/Desktop/London.csv")) {
//            System.out.println(Arrays.toString(station));
////            if (station[3].equals(stationID.getText())) {
////                Stations.getStationByName(stationID.getText());
////            }
//        }
//        Stations stations = new Stations();
//        String station = stationID.getText();
//
//        HashMap<String, String[]> map = stations.createStationsMap();
//
//
//        for (String key : map.keySet()) {
//            if (key.equals("15")) {
//                System.out.println("Station: " + Arrays.toString(map.get(key)));
//            }
//        }
            //System.out.println("Id: " + key + " Name: " + map.get("15"));
            //System.out.println(key);
//           // System.out.println("Station: " + Arrays.toString(map.get(0)) + ", " + Arrays.toString(map.get(3)));
//            if (Arrays.toString(map.get(key)).contains("15")) {
//                System.out.println("Station: " + Arrays.toString(map.get(3)));
//            }
//        }
    }




}









