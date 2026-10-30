import java.util.Arrays;

public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        if (points == null || points.length <= 1) {
            return 0;
        }

        int n = points.length;
        int totalCost = 0;
        int edgesUsed = 0;

        int[] minDist = new int[n];
        Arrays.fill(minDist, Integer.MAX_VALUE);

        boolean[] inMST = new boolean[n];

        minDist[0] = 0;

        for (int i = 0; i < n; i++) {
            int currMin = Integer.MAX_VALUE;
            int currNode = -1;

            for (int j = 0; j < n; j++) {
                if (!inMST[j] && minDist[j] < currMin) {
                    currMin = minDist[j];
                    currNode = j;
                }
            }


            if (currNode == -1) {
                break;
            }


            inMST[currNode] = true;
            totalCost += currMin;
            edgesUsed++;


            if (edgesUsed == n) {
                break;
            }


            for (int j = 0; j < n; j++) {
                if (!inMST[j]) {
                    int distance = Math.abs(points[currNode][0] - points[j][0]) +
                            Math.abs(points[currNode][1] - points[j][1]);
                    if (distance < minDist[j]) {
                        minDist[j] = distance;
                    }
                }
            }
        }

        return totalCost;
    }
}