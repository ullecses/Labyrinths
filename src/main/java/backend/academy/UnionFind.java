package backend.academy;

import java.util.Arrays;

public class UnionFind {
    private final int[] parent;
    private final int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        Arrays.fill(parent, -1); // Инициализация с -1 (означает, что все элементы отдельны)
    }

    public int find(int p) {
        if (parent[p] < 0) {
            return p; // p является корнем
        }
        // Путь сжатия
        parent[p] = find(parent[p]);
        return parent[p];
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP != rootQ) {
            // Объединяем деревья по рангу
            if (rank[rootP] > rank[rootQ]) {
                parent[rootQ] = rootP;
            } else if (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
        }
    }
}
