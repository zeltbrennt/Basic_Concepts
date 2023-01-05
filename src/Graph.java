import java.util.*;

public class Graph {

    /**
     * the example shows how graphs should be represented:
     * - each node in the graph is a key in a dictionary
     * - each edge of a node is represented as a entry in a list of nodes, where the edge connects
     *
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
     * @param current id of current node
     */
    public static void depthFirstPrint(Map<String, List<String>> graph, String current) {
        Deque<String> stack = new ArrayDeque<>();
        stack.addLast(current);
        while (!stack.isEmpty()) {
            current = stack.removeLast();
            System.out.printf("%s ", current); //do something /w the element after popping from queue
            for (String neighbor : graph.get(current)) {
                stack.addLast(neighbor);
            }
        }
    }

    /**
     * uses the system stack for DFS. Prints the current Node.
     * @param graph Map representation of graph
     * @param current id of current Node
     */
    public static void recursiveDepthFirstPrint(Map<String, List<String>> graph, String current) {
        System.out.printf("%s ", current); // do something
        for (String neighbor : graph.get(current)) {
            recursiveDepthFirstPrint(graph, neighbor);
        }
    }


    /**
     * implements BFS on a simple graph. each node is just a String as it's id. Prints the current Node.
     * @param graph Map representation of a graph.
     * @param current id of current node
     */
    public static <E> void breadthFirstPrint(Map<E, List<E>> graph, E current) {
        Deque<E> queue = new ArrayDeque<>();
        queue.addLast(current);
        current = queue.removeFirst();
        System.out.printf("%s ", current); //do something /w the element after popping from queue
        for (E neighbor : graph.get(current)) {
            queue.addLast(neighbor);
        }
        while (!queue.isEmpty()) {
            current = queue.removeFirst();
            System.out.printf("%s ", current); //do something /w the element after popping from queue
            for (E neighbor : graph.get(current)) {
                queue.addLast(neighbor);
            }
        }
    }
}
