/**
 * This simple Consumer class is responsible for consuming integers
 * contained within a Buffer class, and print them.
 */
public class Consumer implements Runnable {

    private int id;
    private final Buffer buffer;
    private int loops;
    private boolean hasLimitedLoops = false;

    /**
     * Creates a Consumer instance that will run indefinitely when executed
     * @param buffer the buffer the Consumer will use to consume numbers from.
     * @param id the unique id of the Consumer.
     */
    public Consumer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    /**
     * Creates a Consumer instance that will run a limited number of times when executed.
     * @param buffer the buffer the Consumer will use to consume numbers from.
     * @param id the unique id of the Consumer.
     * @param loops the amount of loops the Consumer will run when executed.
     */
    public Consumer(Buffer buffer, int id, int loops) {
        this.buffer = buffer;
        this.id = id;
        this.loops = loops;
        this.hasLimitedLoops = true;
    }

    @Override
    public void run() {
        if (!hasLimitedLoops) {
            while (true) {
                try {
                    int number = consumeNumber();
                    System.out.println("[CONSUMER " + this.id + "] Consumed " + number);
                } catch (InterruptedException e) {
                    System.out.println("[CONSUMER " + this.id + "] Blocked");
                }
            }
        } else {
            for (int i = 0; i < loops; i++) {
                try {
                    int number = consumeNumber();
                    System.out.println("[CONSUMER " + this.id + ", loop " + i + "] Consumed " + number);
                } catch (InterruptedException e) {
                    System.out.println("[CONSUMER " + this.id + ", loop " + i + "] Blocked");
                }
            }
        }
    }

    /**
     * Consumes a number from the buffer and returns it.
     * @return the consumed number.
     * @throws InterruptedException if the buffer is already empty.
     */
    public int consumeNumber() throws InterruptedException {
        return buffer.take();
    }
}
