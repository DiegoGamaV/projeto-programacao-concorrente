/**
 * This simple Producer class is responsible for producing random integer from
 * 0 to 9 and putting it inside a buffer.
 */
public class Producer implements Runnable {

    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

    }


    /**
     * This method generates a random int from 0 to 9 and
     * puts it inside a buffer.
     */
    public void produceNumber() {
        int number = (int) Math.random();
        buffer.put(number);
    }
}
