/**
 * Table is a simple class that models a round table with a certain capacity of Philosophers.
 * It has one fork for every Philosophers, in a way that allows every philosopher to use two forks,
 * but that forces them to share the left fork with the left neighbour, and the right fork with the
 * right neighbour.
 *
 * Only one Philosophers can hold the same fork at a time. A fork on the table has the Available state,
 * and a fork being held by a Philosopher has the Unavailable state. When a Philosopher tries to get an
 * Unavailable fork, its Thread is blocked until a fork is returned to the Table by another Philosopher.
 *
 * The Table controls the number of philosophers using an array of Philosophers, and the number and states
 * of forks using an array of Availability.
 */
public class Table {
    private Philosopher[] philosophers;
    private Availability[] forks;

    /**
     * A simple constructor that creates a Table instance.
     * @param capacity the number of Philosophers that can sit on the Table, and
     *                 also the number of forks on the Table.
     */
    public Table(int capacity) {
        this.philosophers = new Philosopher[capacity];
        for (int i = 0; i < size(); i++) {
            this.philosophers[i] = null;
        }

        this.forks = new Availability[capacity];
        for (int i = 0; i < size(); i++) {
            this.forks[i] = Availability.AVAILABLE;
        }
    }

    /**
     * Method to sit a Philosopher at the Table. It sits on the
     * first avaialble position.
     * @param philosopher the Philosopher to be added at the array.
     * @return the index of the Philosopher in the array.
     */
    public int addPhilosopher(Philosopher philosopher) {
        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (this.philosophers[i] == null) {
                this.philosophers[i] = philosopher;
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * Method that marks a fork with the Unavailable state if it is Available, and prints at the
     * standard output which Philosopher got which fork. Otherwise, it gets blocked until another
     * Philosopher calls putFork() on any fork.
     * @param fork the index of the fork to be made Unavailable.
     * @param philosopher the index of the hungy Philosopher.
     * @throws InterruptedException if the required fork is already Unavailable.
     */
    public synchronized void getFork(int fork, int philosopher) throws InterruptedException {
        while (forks[fork] == Availability.UNAVAILABLE) {
            wait();
        }
        forks[fork] = Availability.UNAVAILABLE;
        System.out.println("[TABLE] Fork " + fork + " taken by philosopher " + philosopher);
    }

    /**
     * Method that marks a fork with the Available state and prints at the standard output which
     * Philosopher returned which fork. It then unblocks all the blocked Philosophers sitting at
     * the table.
     * @param fork the index of the fork to be made Available.
     * @param philosopher the index of the satisfied Philosopher.
     */
    public synchronized void putFork(int fork, int philosopher) {
        forks[fork] = Availability.AVAILABLE;
        System.out.println("[TABLE] Fork " + fork + " returned by philosopher " + philosopher);
        notifyAll();
    }

    /**
     * Auxiliary method to get the number of Philosophers sitting at the Table.
     * @return the number of Philosophers at the table.
     */
    public int getCapacity() {
        return size();
    }

    /**
     * Auxiliary method to get the length of the philosophers array.
     * @return the length of the philosophers array.
     */
    private int size() {
        return this.philosophers.length;
    }
}
