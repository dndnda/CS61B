package hw2;

public class PercolationFactory {

    public Percolation getPercolation(int n) {
        return new Percolation(n);
    }

}
