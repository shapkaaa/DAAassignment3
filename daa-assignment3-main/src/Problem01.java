public class Problem01 {
    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }

        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                provinces++;
            }
        }
        return provinces;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int city) {
        visited[city] = true;
        for (int next = 0; next < isConnected.length; next++) {
            if (isConnected[city][next] == 1 && !visited[next]) {
                dfs(isConnected, visited, next);
            }
        }
    }
}