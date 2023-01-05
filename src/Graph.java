import java.util.*;

public class Graph {

    /**
     * the example shows how graphs should be represented:
     * - each node in the graph is a key in a dictionary
     * - each edge of a node is represented as a entry in a list of nodes, where the edge connects
     *  A > C
     *  v   v
     *  B   E
     *  v
     *  D > F
     */
    public static final Map<String, List<String>> EXAMPLE = Map.of(
            "a", List.of("c", "b"),
            "b", List.of("d"),
            "c", List.of("e"),
            "d", List.of("f"),
            "e", Collections.emptyList(),
            "f", Collections.emptyList()
    );

    /**
     * implements DFS on a simple graph. each node is just a String as it's id. Prints the current Node.
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
     * @param graph Map representation of graph
     * @param node id of current Node
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
}

class GraphExercises {

    static <T> boolean hasPath(Map<T, List<T>> graph , T start, T target) {
        Deque<T> queue = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        queue.addLast(start);
        while (!queue.isEmpty()) {
            T node = queue.removeFirst();
            visited.add(node);
            for (T edge : graph.get(node)) {
                if (edge == target ) return true;
                if (!visited.contains(edge)) queue.addLast(edge);
            }
        }
        return false;
    }
}
