import java.util.*;

public class Problem10 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            g.add(new ArrayList<>());
        }
        int[] indeg = new int[numCourses];
        for (int[] p : prerequisites) {
            g.get(p[1]).add(p[0]);
            indeg[p[0]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) q.add(i);
        }

        int[] order = new int[numCourses];
        int k = 0;
        while (!q.isEmpty()) {
            int v = q.poll();
            order[k++] = v;
            for (int u : g.get(v)) {
                indeg[u]--;
                if (indeg[u] == 0) q.add(u);
            }
        }

        if (k < numCourses) return new int[0];
        return order;
    }
}
