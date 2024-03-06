package uk.ac.nulondon.graph;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedGraph<V> implements Graph<V> {
    private final Map<V, List<V>> outgoing = new HashMap<>();
    private final Map<V, List<V>> incoming = new HashMap<>();

    @Override
    public int getSize() {
        return outgoing.size();
    }

    @Override
    public List<V> getVertices() {
        return outgoing.keySet().stream().toList();
    }

    @Override
    public List<V> getNeighbours(V vertex) {
        final List<V> out = outgoing.get(vertex);
        if (out == null)
            return null;
        List<V> result = new ArrayList<>(out);
        result.addAll(incoming.get(vertex));
        return result;
    }

    @Override
    public int getDegree(V vertex) {
        return outgoing.get(vertex).size() + incoming.get(vertex).size();
    }

    @Override
    public void clear() {
        outgoing.clear();
        incoming.clear();
    }

    @Override
    public boolean addVertex(V vertex) {
        boolean result = outgoing.putIfAbsent(vertex, new ArrayList<>()) == null;
        if (result) {
            incoming.put(vertex, new ArrayList<>());
        }
        return result;
    }

    @Override
    public boolean addEdge(V from, V to) {
        List<V> fromList = outgoing.get(from);
        List<V> toList = incoming.get(to);
        if (fromList.contains(to))
            return false;
        fromList.add(to);
        toList.add(from);
        return true;
    }

    @Override
    public boolean removeVertex(V vertex) {
        List<V> neighbours = getNeighbours(vertex);
        outgoing.remove(vertex);
        incoming.remove(vertex);
        if (neighbours == null)
            return false;
        for (V neighbour : neighbours) {
            outgoing.get(neighbour).remove(vertex);
            incoming.get(neighbour).remove(vertex);
        }
        return true;
    }

    @Override
    public boolean removeEdge(V from, V to) {
        List<V> fromList = outgoing.get(from);
        List<V> toList = incoming.get(to);
        if (fromList.contains(to) && toList.contains(from)) {
            fromList.remove(to);
            toList.remove(from);
            return true;
        }
        return false;
    }

    @Override
    public String visualize() {
        Set<String> visited = new HashSet<>();
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            pw.println("digraph G {");
            for (V vertex : getVertices()) {
                for (V out : outgoing.get(vertex)) {
                    String edge = String.format("  %s->%s;", vertex, out);
                    if (!visited.contains(edge)) {
                        visited.add(edge);
                        pw.println(edge);
                    }
                }
            }
            pw.println("}");
            pw.flush();
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
