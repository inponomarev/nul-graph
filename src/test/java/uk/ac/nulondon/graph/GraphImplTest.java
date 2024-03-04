package uk.ac.nulondon.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class GraphImplTest {

    Graph<String> usaGraph;

    @BeforeEach
    void setup() {
        usaGraph = new GraphListImpl<>();
        USAGraphBuilder.buildGraph(usaGraph);
    }

    @Test
    void getVertices() {
        assertThat(usaGraph.getVertices()).hasSize(12);
    }

    @Test
    void size() {
        assertThat(usaGraph.getSize()).isEqualTo(12);
    }

    @Test
    void degree() {
        assertThat(usaGraph.getDegree("KC")).isEqualTo(6);
    }

    @Test
    void neighbours() {
        assertThat(usaGraph.getNeighbours("Oakland")).containsExactlyInAnyOrder("SF", "Denver", "Chicago");
    }

    @Test
    void clear() {
        usaGraph.clear();
        assertThat(usaGraph.getSize()).isEqualTo(0);
    }

    @Test
    void removeVertex() {
        assertThat(usaGraph.removeVertex("Oakland")).isTrue();
        assertThat(usaGraph.getDegree("SF")).isEqualTo(2);
        assertThat(usaGraph.getDegree("Denver")).isEqualTo(4);
    }

    @Test
    void removeUnknownVertex() {
        assertThat(usaGraph.removeVertex("London")).isFalse();
    }

    @Test
    void addExistingEdge() {
        assertThat(usaGraph.addEdge("Denver", "Oakland")).isFalse();
    }

    @Test
    void addExistingVertex() {
        assertThat(usaGraph.addVertex("NY")).isFalse();
    }

    @Test
    void removeEdge() {
        assertThat(usaGraph.removeEdge("SF", "Oakland")).isTrue();
        assertThat(usaGraph.getDegree("SF")).isEqualTo(2);
        assertThat(usaGraph.getDegree("Oakland")).isEqualTo(2);
    }

    @Test
    void removeUnknownEdge() {
        assertThat(usaGraph.removeEdge("LA", "NY")).isFalse();
    }
}