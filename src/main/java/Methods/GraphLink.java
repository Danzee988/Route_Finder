package Methods;

public class GraphLink<GraphNode> {
    int stationId;
    //int cost;
    int lineId;

    //------------------Constructor------------------
    public GraphLink(GraphNode station, int lineId) {
        this.stationId = stationId;
        //this.cost = cost;
        this.lineId = lineId;
    }

    //------------------Getters------------------
    public int getStationId() {
        return stationId;
    }

//    public int getCost() {
//        return cost;
//    }

    public int getLineId() {
        return lineId;
    }

    //------------------Setters------------------

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

//    public void setCost(int cost) {
//        this.cost = cost;
//    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    //---------------Methods----------------
    public void addLink(int station1Id, int station2Id, int lineId) {
//        station1Id.co
    }
//    public void connectStations(GraphNodes<T> destination) {

//    }
}
