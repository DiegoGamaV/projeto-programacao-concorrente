/**
 * This simple Consumer class is responsible for consuming integers
 * contained within a Buffer class, and print them.
 */
public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

    }

    public void consume() {
        int number = buffer.take();
        System.out.println(number);
    }
}
