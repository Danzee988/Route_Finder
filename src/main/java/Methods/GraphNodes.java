package Methods;

import java.util.*;


public class GraphNodes<T> {
    public T station;

    private int line;

    public List<GraphNodes<T>> adjList=new ArrayList<>(); //Could use any List implementation
    public GraphNodes(T data) {

        this.station =data;
    }

    public GraphNodes(T data, int line) {
        this.station =data;
        this.line = line;
    }
//    public void connectToNodeDirected(GraphLink2<T> destNode) {
//        adjList.add(destNode);
//    }
    public void connectToNodeUndirected(GraphNodes<T> destNode, int line) {
        if (!(destNode instanceof GraphNodes)) {
            throw new IllegalArgumentException("destNode must be an instance of GraphNodes<Stations>");
        }

        adjList.add(destNode);
//        System.out.println("Connecting " + this.data + " to " + destNode.data.toString() + " on line " + line);
        destNode.adjList.add(this);
    }

    @Override
    public String toString() {
        return "GraphNodes{ " +
                station +
                "}";
    }

    //    public void createlineDefinitionGraph(List<LineDefinition> lineDefinitions, Map<Integer, GraphNodes<Stations>> stationsIdHashMap) {
//        for (LineDefinition lineDefinition : lineDefinitions) {
//            int expectedStation1 = lineDefinition.getStation1ID();
//            int expectedStation2 = lineDefinition.getStation2ID();
//            int expectedLine = lineDefinition.getLineID();
//
//            GraphNodes<Stations> station1 = stationsIdHashMap.get(expectedStation1);
//            GraphNodes<Stations> station2 = stationsIdHashMap.get(expectedStation2);
//
//            GraphLink linkStation1to2 = new GraphLink<>(station2, expectedLine);
//            GraphLink linkStation2to1 = new GraphLink<>(station1, expectedLine);
//
//            station1.connectToNode(linkStation1to2);
//            station2.connectToNode(linkStation2to1);
//        }
//    }


//    public void createlineDefinitionGraph(List<List<Integer>> lineArray) {
//        for (List<Integer> values : lineArray) {
//            if (values.size() >= 3) {
//                int expectedStation1 = values.get(0);
//                int expectedStation2 = values.get(1);
//                int expectedLine = values.get(2);
//
//                GraphNodes<Stations> station1 = stationsIdHashMap.get(expectedStation1);
//                GraphNodes<Stations> station2 = stationsIdHashMap.get(expectedStation2);
//
//                GraphLink linkStation1to2 = new GraphLink(station2, expectedLine);
//                GraphLink linkStation2to1 = new GraphLink(station1, expectedLine);
//
//                station1.connectToNode(linkStation1to2);
//                station2.connectToNode(linkStation2to1);
//            }
//        }
//    }
}
