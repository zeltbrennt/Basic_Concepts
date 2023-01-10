import java.util.*;

public class Graph {

    /**
     * the example shows how graphs should be represented:
     * - each node in the graph is a key in a dictionary
     * - each edge of a node is represented as a entry in a list of nodes, where the edge connects
     * A > C
     * v   v
     * B   E
     * v
     * D > F
     */
    public static final Map<String, List<String>> EXAMPLE_MAP = Map.of(
            "a", List.of("c", "b"),
            "b", List.of("d"),
            "c", List.of("e"),
            "d", List.of("f"),
            "e", Collections.emptyList(),
            "f", Collections.emptyList()
    );

    /**
     * Example shows how a edge list can represent a undirected graph.
     * Can be transformed into an Adjecency Map
     * I - J
     * |
     * K - L
     * |
     * M   N - O
     */
    public static final List<List<String>> EXAMPLE_EDGES = List.of(
            List.of("i", "j"),
            List.of("k", "i"),
            List.of("m", "k"),
            List.of("k", "l"),
            List.of("o", "n")
    );

    /**
     * implements DFS on a simple graph. each node is just a String as it's id. Prints the current Node.
     *
     * @param graph Map representation of a graph.
     * @param start id of start node
     */
    public static <T> void depthFirstPrint(Map<T, List<T>> graph, T start) {
        Deque<T> stack = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        stack.addLast(start);
        while (!stack.isEmpty()) {
            T current = stack.removeLast();
            visited.add(current);
            System.out.printf("%s ", current); //do something /w the element after popping from queue
            for (T neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) stack.addLast(neighbor);
            }
        }
    }

    /**
     * uses the system stack for DFS. Prints the current Node.
     *
     * @param graph Map representation of graph
     * @param node  id of current Node
     */
    public static <T> void recursiveDepthFirstPrint(Map<T, List<T>> graph, T node, Set<T> visited) {
        visited.add(node);
        System.out.printf("%s ", node); // do something
        for (T neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) recursiveDepthFirstPrint(graph, neighbor, visited);
        }
    }


    /**
     * implements BFS on a simple graph. each node is just a String as it's id. Prints the current Node.
     *
     * @param graph Map representation of a graph.
     * @param start id of start node
     */
    public static <T> void breadthFirstPrint(Map<T, List<T>> graph, T start) {
        Deque<T> queue = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        queue.addLast(start);
        while (!queue.isEmpty()) {
            T node = queue.removeFirst();
            visited.add(node);
            System.out.printf("%s ", node); //do something /w the element after popping from queue
            for (T neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) queue.addLast(neighbor);
            }
        }
    }

    /**
     * transforms a list of edges into an adjecency map representation for algorithmic use
     *
     * @param edges 2D ArrayList of exactly 2 connected Nodes. Nodes without edges should be represented as an edge to itself
     * @param <T>   any
     * @return a Adjecency Map as A Dictonary
     */
    public static <T> Map<T, List<T>> createAdjecencyMap(List<List<T>> edges) {
        Map<T, List<T>> adjecencyMap = new HashMap<>();
        for (List<T> edge : edges) {
            T nodeA = edge.get(0);
            T nodeB = edge.get(1);
            if (adjecencyMap.containsKey(nodeA)) adjecencyMap.get(nodeA).add(nodeB);
            else adjecencyMap.put(nodeA, new ArrayList<>(Collections.singletonList(nodeB)));
            if (adjecencyMap.containsKey(nodeB)) adjecencyMap.get(nodeB).add(nodeA);
            else adjecencyMap.put(nodeB, new ArrayList<>(Collections.singletonList(nodeA)));
        }
        return adjecencyMap;
    }
}

class GraphExercises {

    /* finds any path using BFS */
    static <T> boolean hasPath(Map<T, List<T>> graph, T start, T target) {
        Deque<T> queue = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        queue.addLast(start);
        while (!queue.isEmpty()) {
            T node = queue.removeFirst();
            visited.add(node);
            for (T edge : graph.get(node)) {
                if (edge == target) return true;
                if (!visited.contains(edge)) queue.addLast(edge);
            }
        }
        return false;
    }

    /* finds the number of connected components of a graph using recursive DFS */
    static <T> int connectedComponentsCount(Map<T, List<T>> graph) {
        Set<T> visited = new HashSet<>();
        int count = 0;
        for (T node : graph.keySet()) {
            if (explore(graph, node, visited)) count++;
        }
        return count;
    }

    /* true if the graph / component is fully explored */
    static <T> boolean explore(Map<T, List<T>> graph, T current, Set<T> visited) {
        if (visited.contains(current)) return false;
        visited.add(current);
        for (T neighbor : graph.get(current)) {
            explore(graph, neighbor, visited);
        }
        return true;
    }

    static <T> int largestComponent(Map<T, List<T>> graph) {
        Set<T> visited = new HashSet<>();
        int largest = 0;
        for (T node : graph.keySet()) {
            largest = Math.max(exploreSize(graph, node, visited), largest);
        }
        return largest;
    }

    static <T> int exploreSize(Map<T, List<T>> graph, T node, Set<T> visited) {
        if (visited.contains(node)) return 0;
        visited.add(node);
        int size = 1;
        for (T neighbor : graph.get(node)) {
            size += exploreSize(graph, neighbor, visited);
        }
        return size;
    }

    static <T> List<T> shortestPath(Map<T, List<T>> graph, T start, T target) {
        Map<T, T> connections = new HashMap<>();
        Deque<T> queue = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        queue.addLast(start);
        connections.put(start, start);
        while (!queue.isEmpty()) {
            T node = queue.removeFirst();
            if (node == target) return showPath(connections, start, target);
            for (T neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(node);
                    queue.addLast(neighbor);
                    connections.putIfAbsent(neighbor, node);
                }
            }
        }
        return Collections.emptyList();
    }

    private static <T> List<T> showPath(Map<T, T> connections, T start, T target) {
        List<T> path = new ArrayList<>(Collections.singletonList(target));
        while (start != target) {
            path.add(connections.get(target));
            target = connections.get(target);
        }
        Collections.reverse(path);
        return path;
    }
}
