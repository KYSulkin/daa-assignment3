import java.util.*;

public class Problem03 {
    private int[] parent;

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int d = Math.abs(points[i][0] - points[j][0])
                      + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{d, i, j});
            }
        }
        edges.sort((a, b) -> a[0] - b[0]);

        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int res = 0, used = 0;
        for (int[] e : edges) {
            int a = find(e[1]), b = find(e[2]);
            if (a != b) {
                parent[a] = b;
                res += e[0];
                used++;
                if (used == n - 1) break;
            }
        }
        return res;
    }

    private int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
