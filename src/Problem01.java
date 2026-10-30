public class Problem01 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(int[][] g, boolean[] visited, int v) {
        visited[v] = true;
        for (int u = 0; u < g.length; u++) {
            if (g[v][u] == 1 && !visited[u]) {
                dfs(g, visited, u);
            }
        }
    }
}
