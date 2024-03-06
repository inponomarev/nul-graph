package uk.ac.nulondon.bfs;

import uk.ac.nulondon.graph.Graph;
import uk.ac.nulondon.graph.GraphListImpl;
import uk.ac.nulondon.graph.USAGraphBuilder;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

public class App {
    private final Graph<String> usaGraph;
    private final Traversal<String> traversal;

    App() {
        usaGraph = new GraphListImpl<>();
        USAGraphBuilder.buildGraph(usaGraph);
        //USAGraphBuilder.split(usaGraph);
        traversal = new Traversal<>(usaGraph);
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.showSequence();
       //  app.showTree();
    }

    private void showSequence() throws Exception {
        final List<String> traversalSequence = traversal.toSequence("Chicago");
        for (int i = 1; i <= traversalSequence.size(); i++) {
            String dot = usaGraph.visualize(Set.copyOf(traversalSequence.subList(0, i)));
            show(dot);
            Thread.sleep(100);
        }
    }

    private void showTree() throws Exception {
        String dot = traversal.toBFSTree("Chicago").visualize();
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
