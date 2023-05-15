//package Methods;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static assignment.route_finder.FinderController.distance;
//
//public class CostedPath {
//    private int pathCost;
//    private List<GraphNodes<?>> pathList;
//
//    public static <T> CostedPath findCheapestPathDijkstra(GraphNodes<?> startNode, GraphNodes<?> destNode, List<GraphNodes<?>> stationList, List<GraphNodes<?>> pathNodes) {
//        CostedPath cp = new CostedPath();
//        List<GraphNodes<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
//        startNode.nodeValue = 0;
//        unencountered.add(startNode);
//        GraphNodes<?> currentNode;
//        do {
//            currentNode = unencountered.remove(0);
//            encountered.add(currentNode);
//            if (currentNode == destNode) {
//                cp.pathCost = currentNode.nodeValue;
//                while (currentNode != startNode) {
//                    cp.pathList.add(currentNode);
//                    for (GraphNodes<?> pathNode : pathNodes) {
//                        GraphNodes<?> stnNode = (GraphNodes<?>) pathNode;
//                        if (stnNode.station.equals(currentNode.station)) {
//                            cp.pathCost += distance(stnNode.station.getLatitude(), stnNode.station.getLongitude(), currentNode.station.getLatitude(), currentNode.station.getLongitude());
//                            currentNode = stnNode;
//                            break;
//                        }
//                    }
//                }
//                Collections.reverse(cp.pathList);
//                return cp;
//            }
//            for (GraphLinkAL e : currentNode.adjList) {
//                if (!encountered.contains(e.destNode)) {
//                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.cost);
//                    if (!unencountered.contains(e.destNode)) unencountered.add(e.destNode);
//                }
//            }
//            Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue);
//        } while (!unencountered.isEmpty());
//        return null;
//    }
//}
