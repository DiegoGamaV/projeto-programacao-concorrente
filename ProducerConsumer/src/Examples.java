import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This is a simple class that allows for testing the behaviour of this
 * solution for the Producer-Consumer problem.
 */
public class Examples {

    /**
     * Test that runs 2 Producers with 5 loops and 1 Consumer with 10 loops.
     * @throws TimeoutException if 1 minute passes before all threads finish.
     */
    private static void twoProducersOneConsumer() throws TimeoutException {
        Buffer buffer = new Buffer(5);

        System.out.println("------ 2 Producers and 1 Consumer ------");

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Consumer(buffer, 1, 10));
        es.execute(new Producer(buffer, 1, 5));
        es.execute(new Producer(buffer, 2, 5));
        es.shutdown();

        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            if (finished)
                System.out.println("------ Threads Finished ------");
            else
                throw new TimeoutException("Timeout elapsed before all threads finished");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    /**
     * Test that runs 1 Producer with 10 loops and 2 Consumers with 5 loops.
     * @throws TimeoutException if 1 minute passes before all threads finish.
     */
    private static void oneProducerTwoConsumers() throws TimeoutException {
        Buffer buffer = new Buffer(5);

        System.out.println("------ 1 Producer and 2 Consumers ------");

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Consumer(buffer, 1, 5));
        es.execute(new Consumer(buffer, 2, 5));
        es.execute(new Producer(buffer, 1, 10));
        es.shutdown();

        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            if (finished)
                System.out.println("------ Threads Finished ------");
            else
                throw new TimeoutException("Timeout elapsed before all threads finished");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    /**
     * Test that runs 1 Producer with 10 loops and 1 Consumer with 10 loops.
     * @throws TimeoutException if 1 minute passes before both threads finish.
     */
    private static void oneProducerOneConsumer() throws TimeoutException {
        Buffer buffer = new Buffer(5);

        System.out.println("------ 1 Producer and 1 Consumer ------");

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Consumer(buffer, 1, 10));
        es.execute(new Producer(buffer, 1, 10));
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            if (finished)
                System.out.println("------ Threads Finished ------");
            else
                throw new TimeoutException("Timeout elapsed before all threads finished");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            oneProducerOneConsumer();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            oneProducerTwoConsumers();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            twoProducersOneConsumer();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
