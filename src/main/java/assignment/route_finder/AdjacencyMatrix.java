package assignment.route_finder;

import java.lang.reflect.Array;

public class AdjacencyMatrix {
    public boolean[][] amat;
    public GraphNodes<?>[] nodes;
    public int nodeCount=0;
    public AdjacencyMatrix(int size){
        amat = new boolean [size][size]; //All false values by default
        nodes = (GraphNodes<?>[]) Array.newInstance(GraphNodes.class, size);
    }
}
