import Methods.GraphNodes;
import Methods.Stations;
import assignment.route_finder.FinderController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class testCostAndDistance {

    double totalCost = 0;
    double roundedCost = 0;
    int total = 0;
    public void calculateCostB(List<List<GraphNodes<Stations>>> paths, int x, int y) {
        int routNum = y;

        int routSize = x;
        List<Double> costs = new ArrayList<>();

        for (int k = 0; k < routSize - 1; k++) {
            GraphNodes<Stations> stnNode1 = paths.get(routNum + 1).get(k); // get the first station
            GraphNodes<Stations> stnNode2 = paths.get(routNum + 1).get(k + 1); // get the second station
            double lat1 = stnNode1.station.getLatitude();
            double lon1 = stnNode1.station.getLongitude();
            double lat2 = stnNode2.station.getLatitude();
            double lon2 = stnNode2.station.getLongitude();
            double distance = distance(lat1, lon1, lat2, lon2); // calculate distance between the two stations
            double cost = distance * 1.2; // calculate cost of the route between the two stations
            costs.add(cost); // add the cost to the list of costs
        }

        totalCost = costs.stream().mapToDouble(Double::doubleValue).sum(); // calculate the total cost of the route
        roundedCost = Math.round(totalCost * 100.0) / 100.0; // round the cost to two decimal places
        System.out.println("Route Cost: €" + roundedCost);
        total = costs.size() + 1; // calculate the total number of stations in the route
        System.out.println("Total Routes: " + total);
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

    @Test
    public void costCalculation() {
        // Create sample data for testing
        List<List<GraphNodes<Stations>>> paths = new ArrayList<>();
        List<GraphNodes<Stations>> route1 = new ArrayList<>();
        List<GraphNodes<Stations>> route2 = new ArrayList<>();
        // Add GraphNodes<Stations> objects to the route lists
        route1.add(new GraphNodes<>(new Stations(1, "Station 1", 10.0f, 20.0f, 0,0)));
        route1.add(new GraphNodes<>(new Stations(2, "Station 2", 30.0f, 40.0f,0,0)));
        route2.add(new GraphNodes<>(new Stations(3, "Station 3", 50.0f, 60.0f,0,0)));
        route2.add(new GraphNodes<>(new Stations(4, "Station 4", 70.0f, 80.0f,0,0)));

        // Add route lists to paths
        paths.add(route1);
        paths.add(route2);

        int i = route1.size();

        // Call the method you want to test
        calculateCostB(paths, i, 0);

        // Assert the expected results
        Assertions.assertEquals(2783.362896372705, totalCost); // Replace `expectedCost` with the expected cost value
        Assertions.assertEquals(2, total); // Replace `expectedTotalRoutes` with the expected total routes value
    }

    @Test
    public void testDistance() {
        double lat1 = 52.520008;
        double lon1 = 13.404954;
        double lat2 = 51.5074;
        double lon2 = -0.1278;
        double expectedDistance = 931.5663334126956; // Replace with the expected distance value

        double distance = FinderController.distance(lat1, lon1, lat2, lon2);

        Assertions.assertEquals(expectedDistance, distance, 0.01); // Allow a small delta for floating-point precision
    }
}
