import java.util.concurrent.Semaphore;

/**
 * This class is responsible for managing the 
 * reading and writing, and also for saving the data.
 */
public class Database {
	
    private Semaphore readerSem;
    private Semaphore writerSem;
    private Semaphore writerTurn;
	private int readers;
	private int data;
	
	/**
	 * Creates a Database instance
	 */
	public Database(){
		this.readerSem = new Semaphore(1);
        this.writerSem = new Semaphore(1);
        this.writerTurn = new Semaphore(1);
        this.readers = 0;
        this.data = 0;
    }
	
	/**
     * Updates the database data.
     * @param value the number to update the database value.
     */
	public void write(int data) throws InterruptedException {
		this.writerTurn.acquire();
		this.writerSem.acquire();
		
		this.data = data;
		
		this.writerTurn.release();
		this.writerSem.release();
	}
	
	/**
     * Reads the database data.
     */
	public int read() {
		return this.data;
	}
	
	/**
     * Locks to read only.
     * Used before read function.
     */
	public void beforeRead() throws InterruptedException {
		this.writerTurn.acquire();
		this.writerTurn.release();
		
		this.readerSem.acquire();

		this.readers++;
		if(this.readers == 1) {
			this.writerSem.acquire();
		}
		
		this.readerSem.release();
	}
	
	/**
     * Unlocks from read-only
     * Used after read function.
     */
	public void afterRead() throws InterruptedException {
		this.readerSem.acquire();

		this.readers--;
		if(this.readers == 0) {
			this.writerSem.release();
		}
		
		this.readerSem.release();
	}
	
}
