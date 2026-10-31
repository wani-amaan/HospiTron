package algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DijkstraRouting {

    public static final int NO_EDGE = 0;
    public static final int INF = Integer.MAX_VALUE / 4;

    public static class Result {
        private final int source;
        private final int[] distances;
        private final int[] previous;

        private Result(int source, int[] distances, int[] previous) {
            this.source = source;
            this.distances = distances;
            this.previous = previous;
        }

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
                return Collections.emptyList();
            }

            LinkedList<Integer> path = new LinkedList<>();
            int current = destination;

            while (current != -1) {
                path.addFirst(current);
                current = previous[current];
            }

            return path;
        }

        public int[] getDistancesCopy() {
            return Arrays.copyOf(distances, distances.length);
        }
    }

    public Result shortestPath(int[][] graph, int source) {
        validateGraph(graph);
        validateSource(graph, source);

        int n = graph.length;
        int[] dist = new int[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, INF);
        Arrays.fill(previous, -1);
        dist[source] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(dist, visited);
            if (u == -1) {
                break;
            }

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
        Result result = shortestPath(graph, source);

        System.out.println("\n--- SHORTEST DISTANCES FROM SOURCE " + source + " ---");
        for (int i = 0; i < graph.length; i++) {
            int distance = result.getDistanceTo(i);
            if (distance >= INF) {
                System.out.println("To node " + i + " = Unreachable");
            } else {
                System.out.println("To node " + i + " = " + distance);
            }
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