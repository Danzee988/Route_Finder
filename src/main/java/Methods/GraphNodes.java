package Methods;

import java.util.*;


public class GraphNodes<T> {
    public T station;

    public List<GraphNodes<T>> adjList=new ArrayList<>(); //Could use any List implementation
    public GraphNodes(T data) {
        this.station =data;
    }

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
}
