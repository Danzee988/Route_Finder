package Methods;

import java.util.ArrayList;
import java.util.List;

public class GraphLink<GraphNode> {
    int stationId;
    //int cost;
    int lineId;

    public List<GraphLink<GraphNode>> adjacentNodes = new ArrayList<>();


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


    public void connectToNode(GraphLink<GraphNode> destNode) {
        adjacentNodes.add(destNode);

    }

    public void displayGraph(GraphLink<GraphNode> from){
        System.out.println("Recursive depth first traversal starting at Orange");
        System.out.println("-------------------------------------------------");
        TraverseGraph.traverseGraphDepthFirst(from, null);
    }
}
