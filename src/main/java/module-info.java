module assignment.route_finder {
    requires javafx.controls;
    requires javafx.fxml;


    opens assignment.route_finder to javafx.fxml;
    exports assignment.route_finder;
}