/**
 * Simple buffer class that is able to hold a limited amount of positive
 * integers in order and functions as a thread-safe list. A position is
 * empty if it contains -1. It blocks producers when is full and blocks
 * consumers when is empty.
 */
public class Buffer {

    private int[] list;

    /**
     * Creates a Buffer instance with a given size.
     * @param size the size of the Buffer.
     */
    public Buffer(int size){
        this.list = new int[size];

        for (int i = 0; i < this.list.length; i++) {
            this.list[i] = -1;
        }
    }

    /**
     * Removes the first number from the list and returns it. If the list
     * is empty, it blocks the producer. Otherwise if works normally,
     * and it notifies all threads whose objects are related to it.
     * @return the first number from the list.
     * @throws InterruptedException if the list is empty.
     */
    public synchronized int take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        printState();
        int number = this.list[head()];
        shiftLeft();
        notifyAll();
        return number;
    }

    /**
     * Adds the given number at the end of the list. If the list
     * is full, it blocks the producer. Otherwise if works normally,
     * and it notifies all threads whose objects are related to it.
     * @param number the number added to the list.
     * @throws InterruptedException if the list is full.
     * @throws IllegalArgumentException if the number is lower than or equal to 0.
     */
    public synchronized void put(int number) throws InterruptedException, IllegalArgumentException {
        while (isFull()) {
            wait();
        }
        if (number < 0) {
            throw new IllegalArgumentException("Number must be higher than 0");
        } else {
            printState();
            for (int i = 0; i < size(); i++) {
                if (this.list[i] == -1) {
                    this.list[i] = number;
                    break;
                }
            }
            notifyAll();
        }
    }

    /**
     * Shifts all values to the left, overriding the first value and replacing
     * the last one with -1.
     */
    private void shiftLeft() {
        for (int i = 0; i < size() - 1; i++){
            this.list[i] = this.list[i + 1];
        }
        this.list[end()] = -1;
    }

    /**
     * Checks if the list is full. It checks if all values
     * are -1.
     * @return true if the list is full and false otherwise.
     */
    private boolean isFull() {
        boolean result = true;
        for (int i = 0; i < size(); i++){
            if (this.list[i] == -1) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if the list is empty. It checks if any value
     * is -1.
     * @return true if the list is empty and false otherwise.
     */
    private boolean isEmpty() {
        boolean result = true;
        for (int i = 0; i < size(); i++){
            if (this.list[i] != -1) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void printState() {
        String state = "[BUFFER STATE] :";
        for (int i = 0; i < size() - 1; i++) {
            state += this.list[i] + ", ";
        }
        state += this.list[end()];

        System.out.println(state);
    }

    /**
     * Returns the size of the list.
     * @return the size of the list.
     */
    private int size() {
        return this.list.length;
    }

    /**
     * Returns the first position of the list.
     * @return the first position of the list.
     */
    private int head() {
        return 0;
    }

    /**
     * Returns the last position of the list.
     * @return the last position of the list.
     */
    private int end() {
        return size() - 1;
    }

}
