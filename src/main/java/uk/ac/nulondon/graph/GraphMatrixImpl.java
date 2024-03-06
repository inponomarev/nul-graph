package uk.ac.nulondon.graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphMatrixImpl<V> implements Graph<V> {

    private final Map<V, Integer> vertices = new HashMap<>();
    private final BitSet matrix = new BitSet();

    private int matrixWidth;

    private int getIndex(int x, int y) {
        return y * matrixWidth + x;
    }

    private boolean setAdjacency(int from, int to) {
        int i = getIndex(from, to);
        int j = getIndex(to, from);
        if (matrix.get(i) && matrix.get(j)) {
            return false;
        } else {
            matrix.set(i);
            matrix.set(j);
            return true;
        }
    }

    private boolean removeAdjacency(int from, int to) {
        int i = getIndex(from, to);
        int j = getIndex(to, from);
        if (matrix.get(i) && matrix.get(j)) {
            matrix.clear(i);
            matrix.clear(j);
            return true;
        } else {
            return false;
        }
    }

    private List<Integer> getAdjacent(int i) {
        List<Integer> result = new ArrayList<>();
        for (int x = 0; x < matrixWidth; x++) {
            if (matrix.get(getIndex(x, i))) {
                result.add(x);
            }
        }
        return result;
    }

    @Override
    public int getSize() {
        return vertices.size();
    }

    @Override
    public List<V> getVertices() {
        return vertices.keySet().stream().toList();
    }

    @Override
    public List<V> getNeighbours(V vertex) {
        int index = vertices.get(vertex);
        List<Integer> adjacent = getAdjacent(index);
        List<V> result = new ArrayList<>();
        for (V v : vertices.keySet()) {
            if (adjacent.contains(vertices.get(v))) {
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
        matrix.clear();
        matrixWidth = 0;
    }

    @Override
    public boolean addVertex(V vertex) {
        //Wil not work if matrix is not empty!
        if (vertices.get(vertex) == null) {
            vertices.put(vertex, ++matrixWidth);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addEdge(V from, V to) {
        int fromIndex = vertices.get(from);
        int toIndex = vertices.get(to);
        return setAdjacency(fromIndex, toIndex);
    }

    @Override
    public boolean removeVertex(V vertex) {
        return vertices.remove(vertex) != null;
    }

    @Override
    public boolean removeEdge(V from, V to) {
        int fromIndex = vertices.get(from);
        int toIndex = vertices.get(to);
        return removeAdjacency(fromIndex, toIndex);
    }
}
