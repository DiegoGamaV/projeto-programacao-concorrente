import java.util.concurrent.TimeUnit;

/**
 * Philosopher is a simple class that is able to think and eat, and that requires two forks of a
 * Table to eat. If it is a rightie, it requires first its right fork and then the left one, but
 * if it is a leftie, it requires first the left fork and then the right one.
 *
 * The Philospher thinks for a time, then get its forks in an order, then eats for a time, and returns
 * its forks in the same order. This behaviour repeats indefinitely or a limited number of times, if the
 * number of loops is provided.
 */
public class Philosopher implements Runnable{
    private final Table table;
    private final int position;
    private final int numNeighbours;
    private final int eatingTime;
    private final int thinkingTime;
    private final boolean isLeftie;
    private boolean hasLimitedLoops = false;
    private int loops;

    /**
     * Simple constructor that creates an instance of Philosopher that runs indefinitely.
     * @param table the round Table it is going to sit at.
     * @param eatingTime the time it will spend eating.
     * @param thinkingTime the time it will spend thinking.
     * @param isLeftie if it requires and returns first the left fork, rather than the right one.
     */
    public Philosopher(Table table, int eatingTime, int thinkingTime, boolean isLeftie) {
        this.table = table;
        this.position = this.table.addPhilosopher(this);
        this.numNeighbours = this.table.getCapacity();
        this.eatingTime = eatingTime;
        this.thinkingTime = thinkingTime;
        this.isLeftie = isLeftie;
    }

    /**
     * Simple constructor that creates an instance of Philosopher that runs a limited number of times.
     * @param table the round Table it is going to sit at.
     * @param eatingTime the time it will spend eating.
     * @param thinkingTime the time it will spend thinking.
     * @param isLeftie if it requires and returns first the left fork, rather than the right one.
     * @param loops the number of times it will repeat its behaviour before stopping.
     */
    public Philosopher(Table table, int eatingTime, int thinkingTime, boolean isLeftie, int loops) {
        this.table = table;
        this.position = this.table.addPhilosopher(this);
        this.numNeighbours = this.table.getCapacity();
        this.eatingTime = eatingTime;
        this.thinkingTime = thinkingTime;
        this.isLeftie = isLeftie;
        this.loops = loops;
        this.hasLimitedLoops = true;
    }

    /**
     * Method for the Philosopher to get its forks. If isLeftie is true, it gets first its
     * left fork and then the right fork; otherwise, it gets its right fork first, and then
     * the left fork.
     */
    public void getForks(){
        try {
            if (!isLeftie) {
                this.table.getFork(getRight(), this.position);
                this.table.getFork(getLeft(), this.position);
            } else {
                this.table.getFork(getLeft(), this.position);
                this.table.getFork(getRight(), this.position);
            }
        } catch (InterruptedException interruptedException) { }
    }

    /**
     * Method for the Philosopher to return its fokrs. If isLeftie is true, it puts back first its
     * left fork and then the right one; otherwise, it puts back first its right fork and then the
     * left one.
     */
    public void putForks() {
        if (!isLeftie) {
            this.table.putFork(getRight(), this.position);
            this.table.putFork(getLeft(), this.position);
        } else {
            this.table.putFork(getLeft(), this.position);
            this.table.putFork(getRight(), this.position);
        }
    }

    /**
     * Method for the Philosopher to spend some time eating. It waits a number of seconds
     * equivalent to eatingTime eating, and prints at the standard output when starts and
     * finishes eating.
     */
    public void eat() {
        try {
            System.out.println("[PHILOSOPHER " + this.position + "] Starts eating");
            TimeUnit.SECONDS.sleep(eatingTime);
            System.out.println("[PHILOSOPHER " + this.position + "] Finished eating");
        } catch (InterruptedException interruptedException) { }
    }

    /**
     * Method for the Philosopher to spend some time thinking. It waits a number of seconds
     * equivalent to thinkingTime thinking, and prints at the standard output when starts and
     * finishes thinking.
     */
    public void think() {
        try {
            System.out.println("[PHILOSOPHER " + this.position + "] Starts thinking");
            TimeUnit.SECONDS.sleep(thinkingTime);
            System.out.println("[PHILOSOPHER " + this.position + "] Finished thinking");
        } catch (InterruptedException interruptedException) { }
    }

    /**
     * Auxiliary method to get the relative position of the Philosopher's right fork.
     * @return the position of the right fork.
     */
    private int getRight() {
        return this.position;
    }

    /**
     * Auxiliary method to get the relative position of the Philosopher's left fork.
     * @return the position of the left fork.
     */
    private int getLeft() {
        return (this.position + 1) % this.numNeighbours;
    }

    /**
     * Method for the Philosopher to sit at the table and start to perform its behaviour.
     * At every iteration, it thinks for some time, get its forks in some order
     * depending if it is leftie or not, eat using the forks for some time, and returns
     * the forks in the same order.
     * If the loops value is passed, it runs for a number of iterations equivalent to its
     * value. Otherwise, it runs indefinitely.
     */
    @Override
    public void run() {
        System.out.println("[PHILOSOPHER " + this.position + "] Sat at the table");
        if (!hasLimitedLoops) {
            while (true) {
                think();
                getForks();
                eat();
                putForks();
            }
        } else {
            for (int i = 0; i < this.loops; i++) {
                think();
                getForks();
                eat();
                putForks();
            }
        }
        System.out.println("[PHILOSOPHER " + this.position + "] Left the table");
    }
}