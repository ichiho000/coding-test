import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Edge class to represent a connection between stations
class Edge {
    int to;
    double distance;

    Edge(int to, double distance) {
        this.to = to;
        this.distance = distance;
    }
}

public class LongestTrip {
    // Static fields to store the maximum distance and the longest path
    static double maxDistance = 0.0;
    static List<Integer> longestPath = new ArrayList<>();

    public static void main(String[] args) {
        // ① 入力を読む → グラフを作る
        Map<Integer, List<Edge>> graph = readInput();

        // ② 全ての駅を出発点として DFS で探索
        for (int start : graph.keySet()) {
            dfs(start, new HashSet<>(), new ArrayList<>(), 0.0, graph);
        }

        // ③ 最長経路を出力
        printLongestPath();
    }

    // 補助メソッドを下に作る（例：readInput, dfs, printLongestPathなど）
    static Map<Integer, List<Edge>> readInput() {
    // Scannerで入力を読み取り、Mapに格納
    Map<Integer, List<Edge>> graph = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) continue;

        String[] parts = line.split(",");
        if (parts.length != 3) {
            System.out.println("Invalid input: " + line);
            continue;
        }

        try {
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());
            double dist = Double.parseDouble(parts[2].trim());

            graph.computeIfAbsent(from, k -> new ArrayList<>())
                 .add(new Edge(to, dist));
        } catch (NumberFormatException e) {
            System.out.println("Number format error: " + line);
        }
    }
    scanner.close();
    return graph;
    }

    static void dfs(int now, Set<Integer> visited, List<Integer> path, double dist, Map<Integer, List<Edge>> graph) {
    // 再帰で経路探索（訪問済み駅はSetで記録）
    visited.add(now);
    path.add(now);

    if (dist > maxDistance) {
        maxDistance = dist;
        longestPath = new ArrayList<>(path); // 経路をコピーして記録
    }

    if (graph.containsKey(now)) {
        for (Edge edge : graph.get(now)) {
            if (!visited.contains(edge.to)) {
                dfs(edge.to, visited, path, dist + edge.distance, graph);
            }
        }
    }

    // 戻るときに元に戻す（バックトラッキング）
    visited.remove(now);
    path.remove(path.size() - 1);
    }

    static void printLongestPath() {
    // 最長経路（List<Integer>）を一行ずつ出力
        for (int station : longestPath) {
            System.out.println(station);
        }
    }
    
}
    

    
