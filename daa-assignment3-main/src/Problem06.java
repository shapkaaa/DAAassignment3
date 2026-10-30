import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Arrays;

public class Problem06 {


    private static class Frame {
        int u;
        int nextEdge;

        Frame(int u, int nextEdge) {
            this.u = u;
            this.nextEdge = nextEdge;
        }
    }

    public int countDependencyGroups(int n, int[][] dependencies) {
        if (n <= 0) {
            return 0;
        }


        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        if (dependencies != null) {
            for (int[] dep : dependencies) {
                graph.get(dep[0]).add(dep[1]);
            }
        }

        int[] disc = new int[n];
        int[] low = new int[n];
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);

        boolean[] inStack = new boolean[n];
        Deque<Integer> sccStack = new ArrayDeque<>();
        int time = 0;
        int sccCount = 0;

            for (int i = 0; i < n; i++) {
            if (disc[i] == -1) {
                Deque<Frame> callStack = new ArrayDeque<>();

                disc[i] = low[i] = ++time;
                sccStack.push(i);
                inStack[i] = true;
                callStack.push(new Frame(i, 0));

                while (!callStack.isEmpty()) {
                    Frame frame = callStack.peek();
                    int u = frame.u;
                    List<Integer> neighbors = graph.get(u);

                    if (frame.nextEdge < neighbors.size()) {
                        int v = neighbors.get(frame.nextEdge++);
                        if (disc[v] == -1) {
                            disc[v] = low[v] = ++time;
                            sccStack.push(v);
                            inStack[v] = true;
                            callStack.push(new Frame(v, 0));
                        } else if (inStack[v]) {
                            low[u] = Math.min(low[u], disc[v]);
                        }
                    } else {

                        callStack.pop();

                        if (!callStack.isEmpty()) {
                            int parent = callStack.peek().u;
                            low[parent] = Math.min(low[parent], low[u]);
                        }


                        if (low[u] == disc[u]) {
                            sccCount++;
                            while (true) {
                               int node = sccStack.pop();
                                inStack[node] = false;
                                if (node == u) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return sccCount;
    }
}