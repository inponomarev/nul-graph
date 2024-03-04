package uk.ac.nulondon.graph;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class GraphListImplTest {

    Graph<String> usaGraph;

    @BeforeEach
    void setup() {
        usaGraph = new GraphListImpl<>();
        USAGraphBuilder.buildGraph(usaGraph);
    }

    @Test
    void size() {
        AssertionsForClassTypes.assertThat(usaGraph.getSize()).isEqualTo(12);
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
    void removeVertex(){
        usaGraph.removeVertex("Oakland");
        assertThat(usaGraph.getDegree("SF")).isEqualTo(2);
        assertThat(usaGraph.getDegree("Denver")).isEqualTo(4);
    }

    @Test
    void removeEdge(){
        usaGraph.removeEdge("SF", "Oakland");
        assertThat(usaGraph.getDegree("SF")).isEqualTo(2);
        assertThat(usaGraph.getDegree("Oakland")).isEqualTo(2);
    }
}