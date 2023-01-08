package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private boolean hasCycle;

    public MazeCycles(Maze m) {
        super(m);
        s = maze.xyTo1D(1, 1);
        edgeTo[s] = s;
        //distTo[s] = 0;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(s);
    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;

        if (hasCycle) {
            announce();
            return;
        }
        for (int i: maze.adj(v)) {
            if (!marked[i]) {
                //distTo[i] = distTo[v] + 1;
                edgeTo[i] = v;
                dfs(i);
            }
            else if (edgeTo[v] != i) {
                hasCycle = true;
                edgeTo[i] = v;
                return;
            }
        }
    }

}

