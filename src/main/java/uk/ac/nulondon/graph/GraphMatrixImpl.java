package uk.ac.nulondon.graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class GraphMatrixImpl<V> implements Graph<V> {

    List<V> vertices = new ArrayList<>();
    BitSet matrix = new BitSet();

    private int getIndex(int x, int y) {
        //x <= y!!
        return y * (y + 1) / 2 + x;
    }

    private boolean setAdjacency(int x, int y) {
        int mapPosition =
                (x <= y) ? getIndex(x, y) : getIndex(y, x);
        if (matrix.get(mapPosition)) {
            return false;
        } else {
            matrix.set(mapPosition);
            return true;
        }
    }

    private boolean removeAdjacency(int x, int y){
        int mapPosition =
                (x <= y) ? getIndex(x, y) : getIndex(y, x);
        if (matrix.get(mapPosition)) {
            matrix.clear(mapPosition);
            return true;
        } else {
            return false;
        }
    }

    private List<Integer> getAdjacent(int i) {
        List<Integer> result = new ArrayList<>();
        for (int y = i; y < getSize(); y++) {
            if (matrix.get(getIndex(i, y))) {
                result.add(y);
            }
        }
        for (int x = 0; x < i; x++) {
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
        return List.copyOf(vertices);
    }

    @Override
    public List<V> getNeighbours(V vertex) {
        int index = getIndex(vertex);
        List<V> result = new ArrayList<>();
        for (Integer i : getAdjacent(index)) {
            result.add(vertices.get(i));
        }
        return result;
    }

    private int getIndex(V vertex) {
        return vertices.indexOf(vertex);
    }

    @Override
    public int getDegree(V vertex) {
        int index = getIndex(vertex);
        return getAdjacent(index).size();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addVertex(V vertex) {
        if (getIndex(vertex) < 0) {
            vertices.add(vertex);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addEdge(V from, V to) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);
        return setAdjacency(fromIndex, toIndex);
    }

    @Override
    public boolean removeVertex(V vertex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeEdge(V from, V to) {
        return false;
    }
}
