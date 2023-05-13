package Methods;

import java.util.ArrayList;
import java.util.List;

public class GraphLink2<T> {
    public T data;

    private GraphNodes<T> node1;

    private int line;
    public List<GraphLink2<T>> linkList=new ArrayList<>(); //Could use any List implementation
    public GraphLink2(T data) {
        this.data=data;
    }

//    public GraphLink2(GraphNodes<T> node1, GraphNodes<T> node2) {
//        this.node1 = node1;
//        this.node2 = node2;
//    }

    public GraphLink2(GraphNodes<T> node1, int line) {
        this.node1 = node1;
        this.line = line;
    }
}
