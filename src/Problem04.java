import java.util.*;

public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int[] dist = new int[n];
        boolean[] used = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        int res = 0;

        for (int k = 0; k < n; k++) {
            int cur = -1;
            for (int i = 0; i < n; i++) {
                if (!used[i] && (cur == -1 || dist[i] < dist[cur])) {
                    cur = i;
                }
            }
            used[cur] = true;
            res += dist[cur];

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    int d = Math.abs(points[cur][0] - points[i][0])
                          + Math.abs(points[cur][1] - points[i][1]);
                    dist[i] = Math.min(dist[i], d);
                }
            }
        }
        return res;
    }
}
