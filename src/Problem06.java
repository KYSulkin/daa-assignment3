import java.util.*;

public class Problem06 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }
        for (int[] d : dependencies) {
            g.get(d[0]).add(d[1]);
        }

        int[] disc = new int[n];
        int[] low = new int[n];
        boolean[] onStack = new boolean[n];
        Arrays.fill(disc, -1);

        int[] st = new int[n];      // стек Тарьяна
        int sTop = 0;
        int[] call = new int[n];    // стек вызовов вместо рекурсии
        int[] idx = new int[n];
        int time = 0;
        int groups = 0;

        for (int s = 0; s < n; s++) {
            if (disc[s] != -1) continue;

            int cTop = 0;
            call[cTop++] = s;
            disc[s] = low[s] = time++;
            st[sTop++] = s;
            onStack[s] = true;

            while (cTop > 0) {
                int v = call[cTop - 1];
                if (idx[v] < g.get(v).size()) {
                    int u = g.get(v).get(idx[v]++);
                    if (disc[u] == -1) {
                        disc[u] = low[u] = time++;
                        st[sTop++] = u;
                        onStack[u] = true;
                        call[cTop++] = u;
                    } else if (onStack[u]) {
                        low[v] = Math.min(low[v], disc[u]);
                    }
                } else {
                    cTop--;
                    if (cTop > 0) {
                        int p = call[cTop - 1];
                        low[p] = Math.min(low[p], low[v]);
                    }
                    if (low[v] == disc[v]) {
                        groups++;
                        int w;
                        do {
                            w = st[--sTop];
                            onStack[w] = false;
                        } while (w != v);
                    }
                }
            }
        }
        return groups;
    }
}
