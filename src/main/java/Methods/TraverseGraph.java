package Methods;

import java.util.ArrayList;
import java.util.List;

public class TraverseGraph {
    //Regular recursive depth-first graph traversal
//    public static void traverseGraphDepthFirst(GraphLink<?> from, List<GraphLink<?>> encountered ){
//        System.out.println(from.adjacentNodes);
//        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
//        encountered.add(from);
//        for(GraphLink<?> adjNode : from.adjacentNodes)
//            if(!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered );
//    }

    //Agenda list based breadth-first graph traversal (tail recursive)
//    public static void traverseGraphBreadthFirst(List<GraphNodeAL<?>> agenda, List<GraphNodeAL<?>> encountered ){
//        if(agenda.isEmpty()) return;
//        GraphNodeAL<?> next=agenda.remove(0);
//        System.out.println(next.data);
//        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
//        encountered.add(next);
//        for(GraphNodeAL<?> adjNode : next.adjList)
//            if(!encountered.contains(adjNode) && !agenda.contains(adjNode)) agenda.add(adjNode); //Add children to
////end of agenda
//        traverseGraphBreadthFirst(agenda, encountered ); //Tail call
//    }
//    //Agenda list based depth-first graph traversal (tail recursive)
//    public static <T> void traverseGraphDepthFirst(List<GraphNodeAL<?>> agenda, List<GraphNodeAL<?>> encountered ){
//        if(agenda.isEmpty()) return;
//        GraphNodeAL<?> next=agenda.remove(0);
//        System.out.println(next.data);
//        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
//        encountered.add(next);
//        for(GraphNodeAL<?> adjNode : next.adjList)
//            if(!encountered.contains(adjNode) && !agenda.contains(adjNode)) agenda.add( 0 ,adjNode); //Add children to
////front of agenda (order unimportant here)
//        traverseGraphDepthFirst(agenda, encountered ); //Tail call
}
