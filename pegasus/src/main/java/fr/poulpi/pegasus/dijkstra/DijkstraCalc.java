package fr.poulpi.pegasus.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.poulpi.pegasus.model.Edge;
import fr.poulpi.pegasus.model.Graph;
import fr.poulpi.pegasus.model.Stop;

/**
 * Created by pokito on 10/02/14.
 */
public class DijkstraCalc {

    private final List<Stop> nodes;
    private Set<Stop> settledNodes;
    private Set<Stop> unSettledNodes;
    private Map<Stop, Stop> predecessors;
    private Map<Stop, Integer> distance;

    public DijkstraCalc(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Stop>(graph.data);
    }

    public void execute(Stop source) {
        settledNodes = new HashSet<Stop>();
        unSettledNodes = new HashSet<Stop>();
        distance = new HashMap<Stop, Integer>();
        predecessors = new HashMap<Stop, Stop>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Stop node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Stop node) {
        List<Stop> adjacentNodes = getNeighbors(node);
        for (Stop target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private Stop getStopFromId(int id){
        for(Stop stop : nodes){
            if(stop.stop_id == id){
                return stop;
            }
        }
        return null;
    }

    private int getDistance(Stop node, Stop target) {
        for (Edge edge : node.edges) {
            if (edge.dest == target.stop_id) {
                return edge.dur;
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Stop> getNeighbors(Stop node) {
        List<Stop> neighbors = new ArrayList<Stop>();
        Stop mStop;
        for (Edge edge : node.edges) {
            mStop = getStopFromId(edge.dest);
            if (!isSettled(mStop)) {
                neighbors.add(mStop);
            }
        }
        return neighbors;
    }

    private Stop getMinimum(Set<Stop> nodes) {
        Stop minimum = null;
        for (Stop vertex : nodes) {
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

    private boolean isSettled(Stop node) {
        return settledNodes.contains(node);
    }

    private int getShortestDistance(Stop destination) {
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
    public LinkedList<Stop> getPath(Stop target) {
        LinkedList<Stop> path = new LinkedList<Stop>();
        Stop step = target;
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
