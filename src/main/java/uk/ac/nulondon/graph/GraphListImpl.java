package uk.ac.nulondon.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphListImpl<V> implements Graph<V> {

    private final Map<V, List<V>> adjacencyLists = new HashMap<>();


    @Override
    public int getSize() {
        return adjacencyLists.size();
    }

    @Override
    public List<V> getVertices() {
        return new ArrayList<>(adjacencyLists.keySet());
    }

    @Override
    public List<V> getNeighbours(V vertex) {
        return adjacencyLists.get(vertex);
    }

    @Override
    public int getDegree(V vertex) {
        return adjacencyLists.get(vertex).size();
    }

    @Override
    public void clear() {
        adjacencyLists.clear();
    }

    @Override
    public boolean addVertex(V vertex) {
        return adjacencyLists.putIfAbsent(vertex, new ArrayList<>()) == null;
    }

    @Override
    public boolean addEdge(V from, V to) {
        List<V> fromList = adjacencyLists.get(from);
        List<V> toList = adjacencyLists.get(to);
        if (fromList.contains(to) && toList.contains(from)) {
            return false;
        }
        fromList.add(to);
        toList.add(from);
        return true;
    }

    @Override
    public boolean removeVertex(V vertex) {
        List<V> neighbours = adjacencyLists.remove(vertex);
        if (neighbours == null) return false;
        for (V neighbour : neighbours) {
            adjacencyLists.get(neighbour).remove(vertex);
        }
        return true;
    }

    @Override
    public boolean removeEdge(V from, V to) {
        return adjacencyLists.get(from).remove(to) && adjacencyLists.get(to).remove(from);
    }
}
