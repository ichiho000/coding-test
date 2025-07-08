import java.util.*;  // API: java.util package https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/package-summary.html

class Edge {
    final int to;
    final double distance;

    Edge(int to, double distance) {
        this.to = to;
        this.distance = distance;
    }
}

public class LongestTrip {
    static double maxDistance = 0.0;
    static List<Integer> longestPath = List.of();  // API: List.of() で immutable list https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html#of(E...)

    public static void main(String[] args) {
        Map<Integer, List<Edge>> graph = readInput();

        Set<Integer> allNodes = new HashSet<>();
        for (Map.Entry<Integer, List<Edge>> entry : graph.entrySet()) {
            allNodes.add(entry.getKey());
            for (Edge edge : entry.getValue()) {
                allNodes.add(edge.to);
            }
        }

        for (int start : allNodes) {
            dfs(start, new HashSet<>(), new ArrayList<>(), 0.0, graph);
        }

        printLongestPath();
    }

    static Map<Integer, List<Edge>> readInput() {
        Map<Integer, List<Edge>> graph = new HashMap<>();
        try (Scanner scanner = new Scanner(System.in)) {  // API: java.util.Scanner implements AutoCloseable
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) break;

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.err.println("Invalid input: " + line);
                    continue;
                }

                try {
                    int from = Integer.parseInt(parts[0].trim());
                    int to = Integer.parseInt(parts[1].trim());
                    double dist = Double.parseDouble(parts[2].trim());

                    graph.computeIfAbsent(from, k -> new ArrayList<>())
                         .add(new Edge(to, dist)); // API: computeIfAbsent https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html#computeIfAbsent(K,java.util.function.Function)
                } catch (NumberFormatException e) {
                    System.err.println("Number format error: " + line);
                }
            }
        }
        return graph;
    }

    static void dfs(int now, Set<Integer> visited, List<Integer> path, double dist, Map<Integer, List<Edge>> graph) {
        visited.add(now);
        path.add(now);

        if (dist > maxDistance) {
            maxDistance = dist;
            longestPath = List.copyOf(path);  // API: List.copyOf()で不変リストを生成 https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html#copyOf(java.util.Collection)
        }

        for (Edge edge : graph.getOrDefault(now, List.of())) {  // API: Map.getOrDefault() https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html#getOrDefault(java.lang.Object,V)
            if (!visited.contains(edge.to)) {
                dfs(edge.to, visited, path, dist + edge.distance, graph);
            }
        }

        visited.remove(now);
        path.remove(path.size() - 1);
    }

    static void printLongestPath() {
        System.out.println("=== 最長経路 ===");
        longestPath.forEach(System.out::println);  // API: forEach + method reference https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html#forEach(java.util.function.Consumer)
        System.out.printf("距離: %.2f\n", maxDistance);
    }
}
    
