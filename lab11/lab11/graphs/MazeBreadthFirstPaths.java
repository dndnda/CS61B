package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        s = m.xyTo1D(sourceX, sourceY);
        t = m.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int s) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        announce();

        if (s == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            marked[v] = true;
            announce();
            if (v == t) {
                targetFound = true;
                return;
            }

            for (int i: maze.adj(v)) {
                if (!marked[i]) {
                    queue.enqueue(i);
                    distTo[i] = distTo[v] + 1;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

