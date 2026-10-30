import java.util.*;

public class Problem09 {
    private List<List<Integer>> g;
    private int[] state;
    private int[] order;
    private int pos;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            g.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            g.get(p[1]).add(p[0]);
        }
        state = new int[numCourses];
        order = new int[numCourses];
        pos = numCourses - 1;

        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && !dfs(i)) return new int[0];
        }
        return order;
    }

    // false если нашли цикл
    private boolean dfs(int v) {
        state[v] = 1;
        for (int u : g.get(v)) {
            if (state[u] == 1) return false;
            if (state[u] == 0 && !dfs(u)) return false;
        }
        state[v] = 2;
        order[pos--] = v;
        return true;
    }
}
