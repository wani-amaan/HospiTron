package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Modern Java implementation of Dijkstra's Shortest Path Algorithm for hospital emergency routing.
 */
public final class DijkstraRouting {

    public static final int NO_EDGE = 0;
    public static final int INF = Integer.MAX_VALUE / 4;

    public record Result(int source, int[] distances, int[] previous) {

        public int getSource() {
            return source;
        }

        public int getDistanceTo(int destination) {
            if (destination < 0 || destination >= distances.length) {
                throw new IllegalArgumentException("Invalid destination node.");
            }
            return distances[destination];
        }

        public List<Integer> getPathTo(int destination) {
            if (destination < 0 || destination >= distances.length) {
                throw new IllegalArgumentException("Invalid destination node.");
            }

            if (distances[destination] >= INF) {
                return List.of();
            }

            var path = new ArrayList<Integer>();
            int current = destination;

            while (current != -1) {
                path.addFirst(current); // Modern Java SequencedCollection method
                current = previous[current];
            }

            return Collections.unmodifiableList(path);
        }

        public int[] getDistancesCopy() {
            return Arrays.copyOf(distances, distances.length);
        }
    }

    public Result shortestPath(int[][] graph, int source) {
        validateGraph(graph);
        validateSource(graph, source);

        int n = graph.length;
        var dist = new int[n];
        var previous = new int[n];
        var visited = new boolean[n];

        Arrays.fill(dist, INF);
        Arrays.fill(previous, -1);
        dist[source] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(dist, visited);
            if (u == -1) break;

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                int weight = graph[u][v];
                if (!visited[v] && weight > 0 && dist[u] < INF && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    previous[v] = u;
                }
            }
        }

        return new Result(source, dist, previous);
    }

    public List<Integer> shortestPathNodes(int[][] graph, int source, int destination) {
        return shortestPath(graph, source).getPathTo(destination);
    }

    public void printResult(int[][] graph, int source) {
        var result = shortestPath(graph, source);
        System.out.println("\n--- SHORTEST DISTANCES FROM SOURCE " + source + " ---");
        for (int i = 0; i < graph.length; i++) {
            int distance = result.getDistanceTo(i);
            System.out.println("To node " + i + " = " + (distance >= INF ? "Unreachable" : distance));
        }
    }

    private int minDistance(int[] dist, boolean[] visited) {
        int min = INF;
        int minIndex = -1;

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private void validateGraph(int[][] graph) {
        if (graph == null || graph.length == 0) {
            throw new IllegalArgumentException("Graph cannot be null or empty.");
        }

        int n = graph.length;
        for (int[] row : graph) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException("Graph must be a square matrix.");
            }
        }
    }

    private void validateSource(int[][] graph, int source) {
        if (source < 0 || source >= graph.length) {
            throw new IllegalArgumentException("Invalid source node.");
        }
    }
}