package uk.ac.nulondon.bfs;

import uk.ac.nulondon.graph.DirectedGraph;
import uk.ac.nulondon.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

public class Traversal<V> {
    private final Graph<V> graph;

    Traversal(Graph<V> graph) {
        this.graph = graph;
    }

    List<V> toSequence(V root) {
        List<V> visited = new ArrayList<>();
        Deque<V> deque = new ArrayDeque<>();
        V current = root;
        visited.add(current);
        do {
            for (V n : graph.getNeighbours(current)) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    deque.addLast(n);
                }
            }
            current = deque.pollLast();
        } while (current != null);
        return visited;
    }

    Graph<V> toBFSTree(V root) {
        Graph<V> tree = new DirectedGraph<>();
        Deque<V> deque = new ArrayDeque<>();
        V current = root;
        tree.addVertex(current);
        do {
            for (V n : graph.getNeighbours(current)) {
                if (tree.addVertex(n)) {
                    deque.addFirst(n);
                    tree.addEdge(current, n);
                }
            }
            current = deque.pollLast();
        } while (current != null);
        return tree;
    }

    Graph<V> toDFSTree(V root) {
        Graph<V> tree = new DirectedGraph<>();
        Deque<V> deque = new ArrayDeque<>();
        V current = root;
        tree.addVertex(current);
        deque.addLast(current);
        do {
            for (V n : graph.getNeighbours(current)) {
                if (tree.addVertex(n)) {
                    deque.addLast(n);
                    tree.addEdge(current, n);
                    current = n;
                }
            }
            current = deque.pollLast();
        } while (current != null);
        return tree;
    }


}
