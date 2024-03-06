package uk.ac.nulondon.graph;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Graph<V> {
    int getSize();

    List<V> getVertices();

    List<V> getNeighbours(V vertex);

    int getDegree(V vertex);

    void clear();

    boolean addVertex(V vertex);

    boolean addEdge(V from, V to);

    boolean removeVertex(V vertex);

    boolean removeEdge(V from, V to);

    default String visualize() {
        return visualize(Collections.emptySet());
    }

    default String visualize(Set<V> highlighted) {
        Set<String> visited = new HashSet<>();
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            pw.println("graph G {");
            pw.println("  layout=\"neato\";");
            for (V vertex : getVertices()) {
                if (highlighted.contains(vertex)) {
                    pw.printf("  %s[style=filled,fillcolor=yellow];%n", vertex);
                }
                for (V neighbour : getNeighbours(vertex)) {
                    String edge = String.format("  %s--%s;", vertex, neighbour);
                    String reversed = String.format("  %s--%s;", neighbour, vertex);
                    if (!visited.contains(reversed)) {
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

    static void main(String[] args) throws IOException, URISyntaxException {
        Graph<String> graph = new GraphMatrixImpl<>();
        USAGraphBuilder.buildGraph(graph);
        String dot = graph.visualize();
        show(dot);
    }

    private static void show(String dot) throws IOException, URISyntaxException {
        String encoded = URLEncoder.encode(dot, "UTF8")
                .replaceAll("\\+", "%20");
        Desktop.getDesktop().browse(
                new URI("https://dreampuf.github.io/GraphvizOnline/#"
                        + encoded));
    }
}
