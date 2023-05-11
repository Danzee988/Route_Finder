//package Methods;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GraphTraversal {
//    public static void traverseGraphDepthFirst(GraphNodes<?> from, List<GraphNodes<?>> encountered ){
//        System.out.println(from.station);
//        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
//        encountered.add(from);
//        for(GraphNodes<?> adjNode : from.adjList)
//            if(!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered );
//    }
//
////    public static void traverseGraphDepthFirst(GraphNodes<Stations> from, List<GraphNodes<Stations>> encountered) {
////        System.out.println(from.station.getName());
////        if (encountered == null) encountered = new ArrayList<>(); // First node so create new (empty) encountered list
////        encountered.add(from);
////        for (GraphLink link : from.adjacentNodes) {
////            //GraphNodes<Stations> adjNode = GraphNodes.getStationById(link.getStationId());
////            if (!encountered.contains(adjNode)) {
////                traverseGraphDepthFirst(adjNode, encountered);
////            }
////        }
////    }
//
////    --------------------------------------Peters Notes--------------------------------------
//
////    public static void traverseGraphDepthFirst(GraphNodeAL<?> from, List<GraphNodeAL<?>> encountered ){
////        System.out.println(from.data);
////        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
////        encountered.add(from);
////        for(GraphNodeAL<?> adjNode : from.adjList)
////            if(!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered );
////    }
//
//
//}
