import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

public class Problem08 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return true;
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

        int completedCourses = 0;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            completedCourses++;

            for (int next : graph.get(curr)) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return completedCourses == numCourses;
    }
}