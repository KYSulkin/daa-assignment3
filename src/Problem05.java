import java.util.*;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> g = new ArrayList<>();
        List<List<Integer>> rg = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
            rg.add(new ArrayList<>());
        }
        for (int[] d : dependencies) {
            g.get(d[0]).add(d[1]);
            rg.get(d[1]).add(d[0]);
        }

        // 1 проход: порядок выхода из вершин
        boolean[] visited = new boolean[n];
        int[] order = new int[n];
        int cnt = 0;
        int[] stack = new int[n];
        int[] idx = new int[n];

        for (int s = 0; s < n; s++) {
            if (visited[s]) continue;
            int top = 0;
            stack[top++] = s;
            visited[s] = true;
            while (top > 0) {
                int v = stack[top - 1];
                if (idx[v] < g.get(v).size()) {
                    int u = g.get(v).get(idx[v]++);
                    if (!visited[u]) {
                        visited[u] = true;
                        stack[top++] = u;
                    }
                } else {
                    order[cnt++] = v;
                    top--;
                }
            }
        }

        // 2 проход: по обратному графу в обратном порядке
        Arrays.fill(visited, false);
        int groups = 0;
        for (int i = n - 1; i >= 0; i--) {
            int s = order[i];
            if (visited[s]) continue;
            groups++;
            int top = 0;
            stack[top++] = s;
            visited[s] = true;
            while (top > 0) {
                int v = stack[--top];
                for (int u : rg.get(v)) {
                    if (!visited[u]) {
                        visited[u] = true;
                        stack[top++] = u;
                    }
                }
            }
        }
        return groups;
    }
}
