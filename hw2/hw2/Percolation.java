package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private boolean percolated;
    private final int N;
    private boolean[] isOpen;
    private boolean[] isFull;
    private List<Integer> openInTop;
    private List<Integer> openInBottom;
    private int numbersOfOpenSites;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        uf = new WeightedQuickUnionUF(N * N);
        this.N = N;
        isOpen = new boolean[N * N];
        isFull = new boolean[N * N];
        openInTop = new ArrayList<>();
        openInBottom = new ArrayList<>();
        numbersOfOpenSites = 0;
        percolated = false;
    }

    private int toIndex(int row, int col) {
        return row * N + col;
    }

    /**
     * Return the indexes of the Adjacent to the given site.
     * @param row
     * @param col
     * @return
     */
    private int[] indexesOfAdjacent(int row, int col) {
        int[] ans;
        if (row > 0 && row < N - 1 && col > 0 && col < N - 1) {
            ans = new int[4];
            ans[0] = toIndex(row - 1, col);
            ans[1] = toIndex(row, col - 1);
            ans[2] = toIndex(row, col + 1);
            ans[3] = toIndex(row + 1, col);
        } else if (row == 0 && col != 0 && col != N - 1) {
            ans = new int[3];
            ans[0] = toIndex(row, col - 1);
            ans[1] = toIndex(row, col + 1);
            ans[2] = toIndex(row + 1, col);
        } else if (row == N - 1 && col != 0 && col != N - 1) {
            ans = new int[3];
            ans[0] = toIndex(row - 1, col);
            ans[1] = toIndex(row, col - 1);
            ans[2] = toIndex(row, col + 1);
        } else if (col == 0 && row != 0 && row != N - 1) {
            ans = new int[3];
            ans[0] = toIndex(row - 1, col);
            ans[1] = toIndex(row, col + 1);
            ans[2] = toIndex(row + 1, col);
        } else if (col == N - 1 && row != 0 && row != N - 1) {
            ans = new int[3];
            ans[0] = toIndex(row - 1, col);
            ans[1] = toIndex(row, col - 1);
            ans[2] = toIndex(row + 1, col);
        } else if (row == 0 && col == 0) {
            ans = new int[2];
            ans[0] = toIndex(row, col + 1);
            ans[1] = toIndex(row + 1, col);
        } else if (row == 0 && col == N - 1) {
            ans = new int[2];
            ans[0] = toIndex(row, col - 1);
            ans[1] = toIndex(row + 1, col);
        } else if (row == N - 1 && col == 0) {
            ans = new int[2];
            ans[0] = toIndex(row - 1, col);
            ans[1] = toIndex(row, col + 1);
        } else {
            ans = new int[2];
            ans[0] = toIndex(row - 1, col);
            ans[1] = toIndex(row, col - 1);
        }
        return ans;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!(row >= 0 && row < N && col >= 0 && col < N)) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        int index = toIndex(row, col);
        isOpen[index] = true;
        numbersOfOpenSites++;

        if (row == 0) {
            openInTop.add(toIndex(row, col));
        }
        if (row == N - 1) {
            openInBottom.add(toIndex(row, col));
        }
        //Connect the adjacent sites.
        int[] indexes = indexesOfAdjacent(row, col);
        for (int i = 0; i < indexes.length; i++) {
            if (isOpen[indexes[i]]) {
                uf.union(index, indexes[i]);
            }
        }

        for (int i = 0; i < openInTop.size(); i++) {
            if (uf.connected(openInTop.get(i), index)) {
                isFull[index] = true;
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!(row >= 0 && row < N && col >= 0 && col < N)) {
            throw new IllegalArgumentException();
        }
        return isOpen[toIndex(row, col)];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!(row >= 0 && row < N && col >= 0 && col < N)) {
            throw new IllegalArgumentException();
        }
        return isFull[toIndex(row, col)];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numbersOfOpenSites;
    }
    // does the system percolate?
    public boolean percolates() {
        if (percolated = true) {
            return true;
        }

        for (int i = 0; i < openInBottom.size(); i++) {
            for (int j = 0; j < openInTop.size(); j++) {
                if (uf.connected(openInTop.get(j), openInBottom.get(i))) {
                    percolated = true;
                    return true;
                }
            }
        }
        return false;

    }
    // use for unit testing (not required)
    public static void main(String[] args)  {
        Percolation p = new Percolation(5);
        p.open(0, 2);
        p.open(1, 2);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(4, 1);

        StdOut.println(p.isFull(4, 1));
        StdOut.println(p.percolates());
        StdOut.println(p.numberOfOpenSites());

    }
}
