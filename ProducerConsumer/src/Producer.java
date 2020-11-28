import java.util.Random;

/**
 * This simple Producer class is responsible for producing random integer from
 * 0 to 9 and putting it inside a buffer.
 */
public class Producer implements Runnable {

    private int id;
    private final Buffer buffer;
    private int loops;
    private boolean hasLimitedLoops = false;
    private Random randomizer = new Random();

    /**
     * Creates a Producer instance that will run indefinitely when executed.
     * @param buffer the buffer the Producer will use to store its produced numbers.
     * @param id the unique id of the Producer.
     */
    public Producer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
        this.randomizer = new Random();
    }

    /**
     * Creates a Producer instance that will run a limited number of times when executed.
     * @param buffer the buffer the Producer will use to store its produced numbers.
     * @param id the unique id of the Producer.
     * @param loops the amount of loops the Producer will run when executed.
     */
    public Producer(Buffer buffer, int id, int loops) {
        this.id = id;
        if (buffer == null)
            throw new IllegalArgumentException("Buffer cannot be null");
        this.buffer = buffer;
        if (loops <= 0)
            throw new IllegalArgumentException("The number of loops must be positive");
        this.loops = loops;
        this.hasLimitedLoops = true;
        this.randomizer = new Random();
    }

    @Override
    public void run() {
        if (!hasLimitedLoops) {
            while (true) {
                try {
                    int number = produceNumber();
                    System.out.println("[PRODUCER " + id + "] Produced " + number);
                } catch (InterruptedException e) {
                    System.out.println("[PRODUCER " + id + "] Blocked");
                }
            }
        } else {
            for (int i = 0; i < loops; i++) {
                try {
                    int number = produceNumber();
                    System.out.println("[PRODUCER " + id + ", loop " + i +"] Produced " + number);
                } catch (InterruptedException interruptedException) {
                    System.out.println("[PRODUCER " + id + ", loop " + i + "] Blocked");
                } catch (IllegalArgumentException illegalArgumentException) {
                    System.out.println("[PRODUCER " + id + " loop " + i + "] Produced illegal number");
                }
            }
        }
    }


    /**
     * Produces a random integer within the range of 0 and 9, and
     * puts it in the buffer.
     * @return the number produced
     * @throws InterruptedException if the buffer is already full.
     */
    public int produceNumber() throws InterruptedException {
        int number = randomizer.nextInt(10);
        buffer.put(number);
        return number;
    }
}
