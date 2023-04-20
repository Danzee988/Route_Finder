package assignment.route_finder;

import java.util.ArrayList;
import java.util.List;

public class GraphNodes<Station> {
    public Station data;
    public AdjacencyMatrix mat;
    public int nodeId;
    public GraphNodes(Station data,AdjacencyMatrix mat){
        this.data=data;
        this.mat=mat;
        mat.nodes[nodeId=mat.nodeCount++]=this; //Add this node to adjacency matrix and record id
    }
    public void connectToNodeDirected(GraphNodes<Station> destNode) {
        mat.amat[nodeId][destNode.nodeId]=true;
    }
    public void connectToNodeUndirected(GraphNodes<Station> destNode) {
        mat.amat[nodeId][destNode.nodeId]=mat.amat[destNode.nodeId][nodeId]=true;
    }
}
