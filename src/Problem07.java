import java.util.*;

public class Problem07 {
    private List<List<Integer>> g;
    private int[] state; // 0 - не был, 1 - в процессе, 2 - готово

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            g.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            g.get(p[1]).add(p[0]);
        }
        state = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && hasCycle(i)) return false;
        }
        return true;
    }

    private boolean hasCycle(int v) {
        state[v] = 1;
        for (int u : g.get(v)) {
            if (state[u] == 1) return true;
            if (state[u] == 0 && hasCycle(u)) return true;
        }
        state[v] = 2;
        return false;
    }
}
