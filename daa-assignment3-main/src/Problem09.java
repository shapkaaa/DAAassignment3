import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

public class Problem09 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return new int[0];
        }

        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];

        if (prerequisites != null) {
            for (int[] p : prerequisites) {
                int course = p[0];
                int prereq = p[1];

                graph.get(prereq).add(course);
                inDegree[course]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] order = new int[numCourses];
        int index = 0;


        while (!queue.isEmpty()) {
            int curr = queue.poll();
            order[index++] = curr;

            for (int next : graph.get(curr)) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        if (index == numCourses) {
            return order;
        } else {
            return new int[0];
        }
    }
}