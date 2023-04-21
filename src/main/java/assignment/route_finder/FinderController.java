package assignment.route_finder;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;

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
    void scan(MouseEvent event) throws IOException {
        System.out.println("scan method called");

        String path = "/Users/danze/Desktop/London.csv/";

        BufferedReader br = new BufferedReader(new FileReader(path));

        Map<String, String[]> map = new HashMap<>();
        String line;
        int newId = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length > 4 && "1".equals(values[4].trim())) {
                String[] stations = line.split(",");
                stations[0] = Integer.toString(newId); // Replace the ID with the new value
                map.put(stations[0], stations); // Store the array of values using the new ID as the key
                newId++; // Increment the new ID for the next station
            }
        }

        br.close();

        for (String key : map.keySet()) {
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(map.get(key)));
        }

        // You can now use the map to access the updated information for each station
//        String[] aldgate = map.get("1"); // ID 1 is the first station that meets the condition
//        if (aldgate != null) {
//            System.out.println(Arrays.toString(aldgate));
//        }
    }




}









