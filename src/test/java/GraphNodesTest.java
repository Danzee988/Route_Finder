import Methods.GraphNodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

public class GraphNodesTest {

    @Test
    public void testConnectToNodeUndirected() {
        GraphNodes<String> node1 = new GraphNodes<>("Node 1");
        GraphNodes<String> node2 = new GraphNodes<>("Node 2");
        GraphNodes<String> node3 = new GraphNodes<>("Node 3");

        node1.connectToNodeUndirected(node2, 1);
        node1.connectToNodeUndirected(node3, 2);

        List<GraphNodes<String>> expectedAdjList1 = new ArrayList<>();
        expectedAdjList1.add(node2);
        expectedAdjList1.add(node3);
        Assertions.assertEquals(expectedAdjList1, node1.adjList);

        List<GraphNodes<String>> expectedAdjList2 = new ArrayList<>();
        expectedAdjList2.add(node1);
        Assertions.assertEquals(expectedAdjList2, node2.adjList);

        List<GraphNodes<String>> expectedAdjList3 = new ArrayList<>();
        expectedAdjList3.add(node1);
        Assertions.assertEquals(expectedAdjList3, node3.adjList);
    }

    @Test
    public void testToString() {
        GraphNodes<Integer> node = new GraphNodes<>(10);
        String expected = "GraphNodes{ 10}";
        Assertions.assertEquals(expected, node.toString());
    }
}
