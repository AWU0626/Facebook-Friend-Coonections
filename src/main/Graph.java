import java.io.File;
import java.util.*;

public class Graph {
    private Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
    private int maxKey = 0;

    public static void main(String[] args) {
        Graph g = new Graph();

        if (args.length == 3) {
            if (args[0].equals("searchWithBFS")) {
                g.searchWithBFS(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else if (args[0].equals("searchWithDFS")) {
                g.searchWithDFS(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            }
        } else if (args.length == 2) {
            if (args[0].equals("searchWithBFS")) {
                g.searchWithBFS(Integer.parseInt(args[1]));
            } else if (args[0].equals("searchWithDFS")) {
                g.searchWithDFS(Integer.parseInt(args[1]));
            }
        }

    }

    /*
     * Sets up the default graph by reading from the data file
     * "facebook_combined.txt" and create undirected graph using graph object
     */
    public Graph() {
        try { // open from data file
            File file = new File("facebook_combined.txt");
            Scanner input = new Scanner(file);

            // read all input from .txt
            while (input.hasNextLine()) {
                if (!input.hasNextInt()) {
                    break;
                }
                int key = input.nextInt(), val = input.nextInt();

                // updates the maximum threshold of the number of keys
                if (key > maxKey) {
                    maxKey = key;
                }

                if (val > maxKey) {
                    maxKey = val;
                }

                // adds edge from (key -> val)
                if (!graph.containsKey(key)) {
                    graph.put(key, new ArrayList<>(Arrays.asList(val)));
                } else {
                    graph.get(key).add(val);
                }

                // adds edge from (val -> key)
                if (!graph.containsKey(val)) {
                    graph.put(val, new ArrayList<>(Arrays.asList(key)));
                } else {
                    graph.get(val).add(key);
                }
            }
            input.close();
            maxKey += 1; // correct the number of keys from indexing.

            // fill in the remaining nodes that are empty, if there is
            for (int i = 0; i < maxKey; i++) {
                if (graph.get(i) == null) {
                    graph.put(i, new ArrayList<>());
                }
            }
        } catch (Exception e) {

        }
    }

    /*
     * Input: int src
     * 
     * Begins BFS search from src and prints out the number of times that
     * reinitialization is necessary in order for all the nodes in the graph to be
     * discovered
     */
    public void searchWithBFS(int src) {
        Queue<Integer> queue = new LinkedList<>();
        Integer[] parents = new Integer[this.maxKey];
        boolean[] discovered = new boolean[this.maxKey];
        int[] levels = new int[this.maxKey];
        int count = 1;

        // Initialization
        queue.add(src);
        discovered[src] = true;
        levels[src] = 0;
        parents[src] = null;

        // pop the first node in the queue and add back all the neighbors to the queue
        while (!queue.isEmpty()) {
            int currNode = queue.poll();
            ArrayList<Integer> neighbors = graph.get(currNode);

            for (int neighbor : neighbors) {
                // set the neighbors discovered to be true
                // update the distance of the node from the source node
                if (!discovered[neighbor]) {
                    queue.add(neighbor);
                    discovered[neighbor] = true;
                    levels[neighbor] = levels[currNode] + 1;
                    parents[neighbor] = currNode;
                }
            }

            // if queue becomes empty, check if there are undiscovered node and add to queue
            if (queue.isEmpty()) {
                for (int i = 0; i < maxKey; i++) {
                    if (!discovered[i]) {
                        queue.add(i);
                        count += 1;
                        break;
                    }
                }
            }
        }

        // print result
        System.out.println("Number of BFS runs to discover all nodes: " + count);
    }

    /*
     * Input: int src, int tgt
     * 
     * Begins BFS search from src and tracks the progress of each node until tgt is
     * found. When the tgt is found, print the path from src to tgt. Otherwise,
     * indicate that a path from src -> tgt is not possible
     */
    public boolean searchWithBFS(int src, int tgt) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] discovered = new boolean[this.maxKey];
        int[] levels = new int[this.maxKey];
        Integer[] parent = new Integer[this.maxKey];

        // Initialization
        queue.add(src);
        discovered[src] = true;
        levels[src] = 0;
        parent[src] = null;

        // Begin bfs search
        while (!queue.isEmpty()) {
            int currNode = queue.poll();
            ArrayList<Integer> neighbors = graph.get(currNode);

            // update neighbor status & distance from src node
            for (int neighbor : neighbors) {
                if (!discovered[neighbor]) {
                    queue.add(neighbor);
                    discovered[neighbor] = true;
                    levels[neighbor] = levels[currNode] + 1;
                    parent[neighbor] = currNode;

                    // if target found, print path from src -> tgt
                    if (neighbor == tgt) {
                        System.out.println("Target found at level: " + levels[tgt]);

                        return getPath(tgt, parent);
                    }
                }
            }
        }

        System.out.println("Target not found");
        return false;
    }

    /*
     * Input: int src
     * 
     * Begins DFS search from src and prints out the number of times that
     * reinitialization is necessary in order for all the nodes in the graph to be
     * discovered
     */
    public void searchWithDFS(int src) {
        Stack<Integer> stack = new Stack<>();
        int[] discovered = new int[this.maxKey];
        boolean[] visited = new boolean[this.maxKey];
        Integer[] parent = new Integer[this.maxKey];

        // initialization
        int time = 1, count = 1;
        stack.add(src);
        parent[src] = null;

        // begin dfs search
        while (!stack.isEmpty()) {
            int currNode = stack.pop();
            visited[src] = true;
            discovered[currNode] = time;

            ArrayList<Integer> neighbors = graph.get(currNode);

            // update neighbor status & track parent ndoe
            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    stack.add(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = currNode;
                }
            }

            // add a node to stack if not all nodes are discovered
            if (stack.isEmpty()) {
                for (int i = 0; i < visited.length; i++) {
                    if (!visited[i]) {
                        stack.add(i);
                        parent[i] = null;
                        count += 1;
                        break;
                    }
                }
            }
            time += 1;
        }

        System.out.println("Number of DFS runs to discover all nodes: " + count);
    }

    /*
     * Input: int src, int tgt
     * 
     * Begins DFS search from src and tracks the progress of each node until tgt is
     * found. When the tgt is found, print the path from src to tgt. Otherwise,
     * indicate that a path from src -> tgt is not possible
     */
    public boolean searchWithDFS(int src, int tgt) {
        Stack<Integer> stack = new Stack<>();
        int[] discovered = new int[this.maxKey];
        boolean[] visited = new boolean[this.maxKey];
        Integer[] parent = new Integer[this.maxKey];

        // initialization
        int time = 1;
        stack.add(src);
        parent[src] = null;

        // begin dfs search
        while (!stack.isEmpty()) {
            int currNode = stack.pop();
            visited[src] = true;
            discovered[currNode] = time;

            // if target found, print path from src -> tgt
            if (currNode == tgt) {
                return getPath(tgt, parent);
            }

            ArrayList<Integer> neighbors = graph.get(currNode);

            // update neighbor status & track parent node
            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    stack.add(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = currNode;
                }
            }

            time += 1;
        }

        System.out.println("Target tgt cannot be reached from the given source src");
        return false;
    }

    /*
     * Input : int tgt, Integer[] parent
     * 
     * Helper function that outputs the path from src -> tgt by backtracking the
     * parent array starting from tgt -> src where the parent[src] = null
     */
    private boolean getPath(int tgt, Integer[] parent) {
        ArrayList<Integer> order = new ArrayList<>();
        int backtrack = tgt;

        // backtrack from (tgt -> src) and obtain the path from (src -> tgt)
        while (parent[backtrack] != null) {
            order.add(0, backtrack);
            backtrack = parent[backtrack];
        }

        order.add(0, backtrack);

        System.out.print("Target reached from the order of: (");
        for (int i : order) {
            if (i != tgt) {
                System.out.print(i + ", ");
            } else {
                System.out.print(i);
            }

        }
        System.out.print(")");
        return true;
    }

}