package uk.ac.nulondon.graph;

import java.util.*;

public class GraphMatrixImpl<V> implements Graph<V> {

    private final Map<V, Integer> vertices = new HashMap<>();
    private final BitSet matrix = new BitSet();
    int matrixWidth = 0;

    private int getIndex(int x, int y) {
        return y * matrixWidth + x;
    }


    @Override
    public int getSize() {
        return vertices.size();
    }

    @Override
    public List<V> getVertices() {
        return new ArrayList<>(vertices.keySet());
    }

    @Override
    public List<V> getNeighbours(V vertex) {
        int index = vertices.get(vertex);
        Set<Integer> adjacentIndices = new HashSet<>();
        for (int i = 0; i < matrixWidth; i++) {
            if (matrix.get(getIndex(index, i))){
                adjacentIndices.add(i);
            }
        }
        List<V> result = new ArrayList<>();
        for (V v: vertices.keySet()) {
            if (adjacentIndices.contains(vertices.get(v))){
                result.add(v);
            }
        }
        return result;
    }

    @Override
    public int getDegree(V vertex) {
        return getNeighbours(vertex).size();
    }

    @Override
    public void clear() {
        vertices.clear();
    }

    @Override
    public boolean addVertex(V vertex) {
        if (vertices.containsKey(vertex)) {
            return false;
        } else {
            vertices.put(vertex, ++matrixWidth);
            return true;
        }
    }

    @Override
    public boolean addEdge(V from, V to) {
        int fromIndex = vertices.get(from);
        int toIndex = vertices.get(to);
        if (matrix.get(getIndex(fromIndex, toIndex)))
            return false;

        matrix.set(getIndex(fromIndex, toIndex));
        matrix.set(getIndex(toIndex, fromIndex));
        return true;
    }

    @Override
    public boolean removeVertex(V vertex) {
        return vertices.remove(vertex) != null;
    }

    @Override
    public boolean removeEdge(V from, V to) {
        int fromIndex = vertices.get(from);
        int toIndex = vertices.get(to);
        if (matrix.get(getIndex(fromIndex, toIndex))) {
            matrix.clear(getIndex(fromIndex, toIndex));
            matrix.clear(getIndex(toIndex, fromIndex));
            return true;
        } else {
            return false;
        }
    }
}
