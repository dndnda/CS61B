//package hw2;
//
//import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Percolation {
//
//    private WeightedQuickUnionUF uf;
//    private boolean percolated;
//    private final int N;
//    private boolean[] isOpen;
//    private boolean[] isFull;
//    private List<Integer> openInTop;
//    private List<Integer> openInBottom;
//    private int numbersOfOpenSites;
//
//
//    // create N-by-N grid, with all sites initially blocked
//    public Percolation(int N) {
//        if (N <= 0) {
//            throw new IllegalArgumentException();
//        }
//
//        uf = new WeightedQuickUnionUF(N * N);
//        this.N = N;
//        isOpen = new boolean[N * N];
//        isFull = new boolean[N * N];
//        openInTop = new ArrayList<>();
//        openInBottom = new ArrayList<>();
//        numbersOfOpenSites = 0;
//        percolated = false;
//    }
//
//    private int toIndex(int row, int col) {
//        return row * N + col;
//    }
//
//    /**
//     * Return the indexes of the Adjacent to the given site.
//     * @param row
//     * @param col
//     * @return
//     */
//    private int[] indexesOfAdjacent(int row, int col) {
//        int[] ans;
//        if (row > 0 && row < N - 1 && col > 0 && col < N - 1) {
//            ans = new int[4];
//            ans[0] = toIndex(row - 1, col);
//            ans[1] = toIndex(row, col - 1);
//            ans[2] = toIndex(row, col + 1);
//            ans[3] = toIndex(row + 1, col);
//        } else if (row == 0 && col != 0 && col != N - 1) {
//            ans = new int[3];
//            ans[0] = toIndex(row, col - 1);
//            ans[1] = toIndex(row, col + 1);
//            ans[2] = toIndex(row + 1, col);
//        } else if (row == N - 1 && col != 0 && col != N - 1) {
//            ans = new int[3];
//            ans[0] = toIndex(row - 1, col);
//            ans[1] = toIndex(row, col - 1);
//            ans[2] = toIndex(row, col + 1);
//        } else if (col == 0 && row != 0 && row != N - 1) {
//            ans = new int[3];
//            ans[0] = toIndex(row - 1, col);
//            ans[1] = toIndex(row, col + 1);
//            ans[2] = toIndex(row + 1, col);
//        } else if (col == N - 1 && row != 0 && row != N - 1) {
//            ans = new int[3];
//            ans[0] = toIndex(row - 1, col);
//            ans[1] = toIndex(row, col - 1);
//            ans[2] = toIndex(row + 1, col);
//        } else if (row == 0 && col == 0) {
//            ans = new int[2];
//            ans[0] = toIndex(row, col + 1);
//            ans[1] = toIndex(row + 1, col);
//        } else if (row == 0 && col == N - 1) {
//            ans = new int[2];
//            ans[0] = toIndex(row, col - 1);
//            ans[1] = toIndex(row + 1, col);
//        } else if (row == N - 1 && col == 0) {
//            ans = new int[2];
//            ans[0] = toIndex(row - 1, col);
//            ans[1] = toIndex(row, col + 1);
//        } else {
//            ans = new int[2];
//            ans[0] = toIndex(row - 1, col);
//            ans[1] = toIndex(row, col - 1);
//        }
//        return ans;
//    }
//
//    // open the site (row, col) if it is not open already
//    public void open(int row, int col) {
//        if (!(row >= 0 && row < N && col >= 0 && col < N)) {
//            throw new IllegalArgumentException();
//        }
//
//        if (isOpen(row, col)) {
//            return;
//        }
//
//        int index = toIndex(row, col);
//        isOpen[index] = true;
//        numbersOfOpenSites++;
//
//        if (row == 0) {
//            openInTop.add(toIndex(row, col));
//        //    isFull[index] = true;
//        }
//        if (row == N - 1) {
//            openInBottom.add(toIndex(row, col));
//        }
//        //Connect the adjacent sites.
//        int[] indexes = indexesOfAdjacent(row, col);
//        for (int i = 0; i < indexes.length; i++) {
//            if (isOpen[indexes[i]]) {
//                uf.union(index, indexes[i]);
//            }
//        }
//
//    }
//    // is the site (row, col) open?
//    public boolean isOpen(int row, int col) {
//        if (!(row >= 0 && row < N && col >= 0 && col < N)) {
//            throw new IllegalArgumentException();
//        }
//        return isOpen[toIndex(row, col)];
//    }
//    // is the site (row, col) full?
//    public boolean isFull(int row, int col) {
//        if (!(row >= 0 && row < N && col >= 0 && col < N)) {
//            throw new IllegalArgumentException();
//        }
//        int index = toIndex(row, col);
//        if (isFull[index]) {
//            return true;
//        }
//        for (int i = 0; i < openInTop.size(); i++) {
//            if (uf.connected(openInTop.get(i), index)) {
//                isFull[index] = true;
//                break;
//            }
//        }
//        return isFull[index];
//    }
//
//    // number of open sites
//    public int numberOfOpenSites() {
//        return numbersOfOpenSites;
//    }
//    // does the system percolate?
//    public boolean percolates() {
//        if (percolated == true) {
//            return true;
//        }
//
//        for (int i = 0; i < openInBottom.size(); i++) {
//            for (int j = 0; j < openInTop.size(); j++) {
//                if (uf.connected(openInTop.get(j), openInBottom.get(i))) {
//                    percolated = true;
//                    return true;
//                }
//            }
//        }
//        return false;
//
//    }
//    // use for unit testing (not required)
//    public static void main(String[] args)  {
//        Percolation p = new Percolation(5);
//
//        p.open(4, 1);
//        p.open(3, 1);
//        p.open(2, 1);
//        p.open(1, 1);
//        p.open(0, 2);
//        StdOut.println(p.isFull(2, 1));
//        StdOut.println(p.percolates());
//        StdOut.println(p.numberOfOpenSites());
//
//    }
//}
package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int[][] grid;
    private int open_num = 0;

    private WeightedQuickUnionUF uf;
    private int uf_bottom;
    private WeightedQuickUnionUF ufWithoutBottom;  // To avoid backwash!

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        grid = new int[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf_bottom = N * N + 1;
        ufWithoutBottom = new WeightedQuickUnionUF(N * N + 1);
    }

    /** Open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            unionAround(row, col);
            open_num += 1;
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufWithoutBottom.connected(0, ufIndex(row, col));
    }

    public int numberOfOpenSites() {
        return open_num;
    }

    public boolean percolates() {
        return uf.connected(0, uf_bottom);
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int ufIndex(int row, int col) {
        return row * N + col + 1;
    }

    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private void unionAround(int row, int col) {
        for (int[] dir : directions) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (0 <= x && x < N && 0 <= y && y < N && isOpen(x, y)) {
                uf.union(ufIndex(row, col), ufIndex(x, y));
                ufWithoutBottom.union(ufIndex(row, col), ufIndex(x, y));
            }
        }
        if (row == 0) {
            uf.union(0, ufIndex(row, col));
            ufWithoutBottom.union(0, ufIndex(row, col));
        }
        if (row == N - 1) {
            uf.union(ufIndex(row, col), uf_bottom);
        }
    }

    public static void main(String[] args) { }
}