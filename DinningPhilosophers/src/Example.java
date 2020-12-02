import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Simple testing class that runs the classic experiment with five Philosophers.
 */
public class Example {

    /**
     * Experiment that uses a Table that sits 5 Philosophers, where all of them are Rigthies. All the
     * Philosophers eat and thing for 2 seconds and repeat this behaviour 2 times, to make it easier
     * to understand the behaviour.
     *
     * Since none of them are Lefties, a deadlock is possible is all of them get their right fork at
     * the same time. If that case, it is expected that a TimeoutException is thrown.
     *
     * @throws TimeoutException if the experiment fails to finish before 2 minutes have passed.
     */
    private static void fiveRightiesDeadlock() throws TimeoutException {
        Table table = new Table(5);
        Philosopher philosopher0 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher1 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher2 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher3 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher4 = new Philosopher(table, 2, 2, false, 2);

        System.out.println("------ Five Philosophers ------");

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(philosopher0);
        es.execute(philosopher1);
        es.execute(philosopher2);
        es.execute(philosopher3);
        es.execute(philosopher4);
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(2, TimeUnit.MINUTES);

            if (finished)
                System.out.println("------ Threads Finished ------");
            else
                throw new TimeoutException("Timeout elapsed before all threads finished");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    /**
     * Experiment that uses a Table that sits 5 Philosophers, where one of them is a Leftie. All the
     * Philosophers eat and thing for 2 seconds and repeat this behaviour 2 times, to make it easier
     * to understand the behaviour.
     *
     * Since philosopher1 is a Leftie, the deadlock that happens if all of the Philosophers get their
     * right fork at the same time is not possible here. When that happens, philosopher1 will get its
     * left fork, either blocking or making it possible for philosopher0 to get its left fork.
     *
     * @throws TimeoutException if the experiment fails to finish before 2 minutes have passed.
     */
    private static void forRightiesOneLeftie() throws TimeoutException {
        Table table = new Table(5);
        Philosopher philosopher0 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher1 = new Philosopher(table, 2, 2, true, 2);
        Philosopher philosopher2 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher3 = new Philosopher(table, 2, 2, false, 2);
        Philosopher philosopher4 = new Philosopher(table, 2, 2, false, 2);

        System.out.println("------ Five Philosophers ------");

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(philosopher0);
        es.execute(philosopher1);
        es.execute(philosopher2);
        es.execute(philosopher3);
        es.execute(philosopher4);
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(2, TimeUnit.MINUTES);

            if (finished)
                System.out.println("------ Threads Finished ------");
            else
                throw new TimeoutException("Timeout elapsed before all threads finished");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            fiveRightiesDeadlock();
            forRightiesOneLeftie();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
