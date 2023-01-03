package hw2;

public class PercolationFactory {

    public static Percolation getPercolation(int n) {
        return new Percolation(n);
    }

}
