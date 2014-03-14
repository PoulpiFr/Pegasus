package fr.poulpi.pegasus.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.poulpi.pegasus.model.OfflineEdge;
import fr.poulpi.pegasus.model.OfflineGraph;
import fr.poulpi.pegasus.model.OfflineStop;

/**
 * Created by pokito on 10/02/14.
 */
public class DijkstraCalc {

    private final List<OfflineStop> nodes;
    private Set<OfflineStop> settledNodes;
    private Set<OfflineStop> unSettledNodes;
    private Map<OfflineStop, OfflineStop> predecessors;
    private Map<OfflineStop, Integer> distance;

    public DijkstraCalc(OfflineGraph offlineGraph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<OfflineStop>(offlineGraph.data);
    }

    public void execute(OfflineStop source) {
        settledNodes = new HashSet<OfflineStop>();
        unSettledNodes = new HashSet<OfflineStop>();
        distance = new HashMap<OfflineStop, Integer>();
        predecessors = new HashMap<OfflineStop, OfflineStop>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            OfflineStop node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(OfflineStop node) {
        List<OfflineStop> adjacentNodes = getNeighbors(node);
        for (OfflineStop target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private OfflineStop getStopFromId(int id){
        for(OfflineStop offlineStop : nodes){
            if(offlineStop.stop_id == id){
                return offlineStop;
            }
        }
        return null;
    }

    private int getDistance(OfflineStop node, OfflineStop target) {
        for (OfflineEdge offlineEdge : node.offlineEdges) {
            if (offlineEdge.dest == target.stop_id) {
                return offlineEdge.dur;
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<OfflineStop> getNeighbors(OfflineStop node) {
        List<OfflineStop> neighbors = new ArrayList<OfflineStop>();
        OfflineStop mOfflineStop;
        for (OfflineEdge offlineEdge : node.offlineEdges) {
            mOfflineStop = getStopFromId(offlineEdge.dest);
            if (!isSettled(mOfflineStop)) {
                neighbors.add(mOfflineStop);
            }
        }
        return neighbors;
    }

    private OfflineStop getMinimum(Set<OfflineStop> nodes) {
        OfflineStop minimum = null;
        for (OfflineStop vertex : nodes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(OfflineStop node) {
        return settledNodes.contains(node);
    }

    private int getShortestDistance(OfflineStop destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<OfflineStop> getPath(OfflineStop target) {
        LinkedList<OfflineStop> path = new LinkedList<OfflineStop>();
        OfflineStop step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
