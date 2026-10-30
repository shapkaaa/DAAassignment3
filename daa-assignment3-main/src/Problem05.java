import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        if (n <= 0) {
            return 0;
        }


        List<List<Integer>> graph = new ArrayList<>(n);
        List<List<Integer>> transpose = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            transpose.add(new ArrayList<>());
        }


        if (dependencies != null) {
            for (int[] dep : dependencies) {
                int u = dep[0];
                int v = dep[1];
                graph.get(u).add(v);
                transpose.get(v).add(u);
            }
        }


        boolean[] visited = new boolean[n];
        List<Integer> finishOrder = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                        Deque<int[]> stack = new ArrayDeque<>();
                stack.push(new int[]{i, 0});
                visited[i] = true;

                while (!stack.isEmpty()) {
                    int[] curr = stack.peek();
                    int u = curr[0];
                    int edgeIdx = curr[1];

                    List<Integer> neighbors = graph.get(u);
                    if (edgeIdx < neighbors.size()) {
                        curr[1] = edgeIdx + 1;
                        int v = neighbors.get(edgeIdx);
                        if (!visited[v]) {
                            visited[v] = true;
                            stack.push(new int[]{v, 0});
                        }
                    } else {
                        stack.pop();
                        finishOrder.add(u);
                    }
                }
            }
        }


        boolean[] visitedTranspose = new boolean[n];
        int sccCount = 0;

        for (int i = finishOrder.size() - 1; i >= 0; i--) {
            int root = finishOrder.get(i);
            if (!visitedTranspose[root]) {
                sccCount++;
                Deque<Integer> stack = new ArrayDeque<>();
                stack.push(root);
                visitedTranspose[root] = true;

                while (!stack.isEmpty()) {
                    int u = stack.pop();
                    for (int v : transpose.get(u)) {
                        if (!visitedTranspose[v]) {
                            visitedTranspose[v] = true;
                            stack.push(v);
                        }
                    }
                }
            }
        }

        return sccCount;
    }
}