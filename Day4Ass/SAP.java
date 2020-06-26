import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

//import edu.princeton.cs.algs4.StdIn;
public class SAP{
    private Digraph G;
    private BreadthFirstDirectedPaths bfdp;
    private BreadthFirstDirectedPaths bfdp1;
    

    public SAP(Digraph G) {
        this.G = G;
    }

    public int length(int v, int w) {
        if (v < 0 || v > G.V() || w < 0 || w > G.V()) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return 0;
        }
        int len = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        bfdp = new BreadthFirstDirectedPaths(G, v);
        bfdp1 = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (bfdp.hasPathTo(i) && bfdp1.hasPathTo(i)) {
                len = bfdp.distTo(i) + bfdp1.distTo(i);
            }
            if (len <= min) {
                min = len;
            }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;
    }

    public int ancestor(int v, int w) {
        if (v < 0 || v > G.V() || w < 0 || w > G.V()) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return v;
        }
        int len = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        bfdp = new BreadthFirstDirectedPaths(G, v);
        bfdp1 = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (bfdp.hasPathTo(i) && bfdp1.hasPathTo(i)) {
                len = bfdp.distTo(i) + bfdp1.distTo(i);
                //ancestor = i;
                if (len <= min) {
                    min = len;
                    ancestor =i;
                }
            }
            // if (len <= min) {
            //     min = len;
            //     ancestor = i;
            // }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return ancestor;
    }

    public int length (Iterable<Integer> v, Iterable<Integer> w) {
        if (v.iterator().equals(null) || w.iterator().equals(null)) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return 0;
        }
        int min_len = Integer.MAX_VALUE;
        for (int i : v) {
            for (int j : w) {
               if (length(i, j) < min_len) {
                min_len =  length(i, j);
               }
            }
        }
        if (min_len == Integer.MAX_VALUE) {
            return -1;
        }
        return min_len;
    }

    public int ancestor (Iterable<Integer> v, Iterable<Integer> w) {
        if (v.iterator().equals(null) || w.iterator().equals(null)) {
            throw new IllegalArgumentException();
        }
        int min_len = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i : v) {
            for (int j : w) {
                if (i == j) {
                    return i;
                }
                int ancestor1 = ancestor(i, j);
               if (length(i, j) < min_len) {
                 min_len = length(i ,j);
                 ancestor = ancestor1;
               }
            }
        }
        return ancestor;
    }

    public static void main(String[] args) {
        // int ver = StdIn.readInt();
        // //int edg = StdIn.readInt();
        // ArrayList<Integer> v = new ArrayList<>();
        // v.add(13);
        // v.add(23);
        // v.add(24);
        // ArrayList<Integer> ed = new ArrayList<>();
        // ed.add(6);
        // ed.add(16);
        // ed.add(17);
        // Digraph G = new Digraph(ver);
        // int[] v_arr = {1, 2, 3, 4, 5, 6, 10, 11, 12, 17, 18, 19, 20, 23, 24, 7, 8, 9, 13, 14, 15, 16, 21, 22};
        // int[] e_arr = {0, 0, 1, 1, 2, 2, 5, 5, 5, 10, 10, 12, 12, 20, 20, 3, 3, 3, 7, 7, 9, 9, 16, 16};
        // for (int i = 0; i < v_arr.length; i++) {
        //     System.out.println(v_arr[i] + "->" + e_arr[i]);
        //     G.addEdge(v_arr[i], e_arr[i]);
        // }
        // SAP sap = new SAP(G);
        // int length = sap.length(v, ed);
        // int ancestor = sap.ancestor(v, ed);
        // StdOut.printf("Length = %d, Ancestor = %d\n", length, ancestor);
    }
}