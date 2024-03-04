package uk.ac.nulondon.graph;

public class USAGraphBuilder {
    public static void buildGraph(Graph<String> graph) {
        graph.addVertex("Oakland");
        graph.addVertex("SF");
        graph.addVertex("LA");
        graph.addVertex("Denver");
        graph.addVertex("KC");
        graph.addVertex("Chicago");
        graph.addVertex("Boston");
        graph.addVertex("NY");
        graph.addVertex("Atlanta");
        graph.addVertex("Miami");
        graph.addVertex("Dallas");
        graph.addVertex("Houston");
        graph.addEdge("Oakland", "SF");
        graph.addEdge("Oakland", "Denver");
        graph.addEdge("Oakland", "Chicago");
        graph.addEdge("SF", "Denver");
        graph.addEdge("SF", "LA");
        graph.addEdge("LA", "Denver");
        graph.addEdge("LA", "KC");
        graph.addEdge("LA", "Dallas");
        graph.addEdge("Denver", "Chicago");
        graph.addEdge("Denver", "KC");
        graph.addEdge("KC", "Chicago");
        graph.addEdge("KC", "NY");
        graph.addEdge("KC", "Atlanta");
        graph.addEdge("KC", "Dallas");
        graph.addEdge("Dallas", "Atlanta");
        graph.addEdge("Dallas", "Houston");
        graph.addEdge("Houston", "Atlanta");
        graph.addEdge("Houston", "Miami");
        graph.addEdge("Chicago", "Boston");
        graph.addEdge("Chicago", "NY");
        graph.addEdge("Atlanta", "NY");
        graph.addEdge("Atlanta", "Miami");
        graph.addEdge("NY", "Boston");
    }
}
