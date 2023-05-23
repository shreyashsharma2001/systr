import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;

public class TokenRing {
    private static final int NUM_NODES = 5;
    private static AtomicInteger token = new AtomicInteger(0);
    private static AtomicBoolean[] in_cs = new AtomicBoolean[NUM_NODES];
    private static final int NUM_ITERATIONS = 5; // number of iterations to run

    public static void main(String[] args) {
        // initialize nodes
        for (int i = 0; i < NUM_NODES; i++) {
            in_cs[i] = new AtomicBoolean(false);
            new Node(i).start();
        }
        // start token passing
        token.set(0);
    }

    static class Node extends Thread {
        private int id;
        private Node(int id) {
            this.id = id;
        }
        public void run() {
            int num_iterations = 0;
            while (num_iterations < NUM_ITERATIONS) {
                // wait for the token
                while (token.get() != id) {
                    // do nothing
                }
                System.out.println("Node " + id + " received the token.");
                // enter critical section
                in_cs[id].set(true);
                System.out.println("Node " + id + " enters critical section.");
                // display which nodes are in the critical section
                System.out.print("Nodes in critical section: ");
                for (int i = 0; i < NUM_NODES; i++) {
                    if (in_cs[i].get()) {
                        System.out.print(i + " ");
                    }
                }
                System.out.println();
                // perform some exclusive task
                // ...
                // exit critical section
                in_cs[id].set(false);
                System.out.println("Node " + id + " exits critical section.");
                // pass token to next node
                int next = (id + 1) % NUM_NODES;
                System.out.println("Node " + id + " passes the token to node " + next + ".");
                token.set(next);
                num_iterations++;
            }
        }
    }
}
