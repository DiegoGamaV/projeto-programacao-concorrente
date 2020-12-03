import java.util.concurrent.Semaphore;

/**
 *	This class is responsible for managing
 *	the entry and exit of threads from the critical section.
 */
public class MutexRooms {

	private int room1Counter;
	private int room2Counter;
	private Semaphore mutex;
	private Semaphore t1;
	private Semaphore t2;
	
	/**
	 * Creates a MutexRooms instance
	 */
	public MutexRooms() {
		this.room1Counter = 0;
		this.room2Counter = 0;
		this.mutex = new Semaphore(1);
		this.t1 = new Semaphore(1);
		this.t2 = new Semaphore(0);
	}
	
	/**
	 * Allows a thread to safely enter the critical section
	 */
	public void getCriticalSection() throws InterruptedException {
		this.firstRoom();
		
		this.secondRoom();
		
		this.startCriticalSection();
	}
	
	private void firstRoom() throws InterruptedException {
		this.mutex.acquire();
		
		this.room1Counter++;
		
		this.mutex.release();
	}
	
	private void secondRoom() throws InterruptedException {
		this.t1.acquire();
		this.room2Counter++;
		
		this.mutex.acquire();
		this.room1Counter--;
		
		
		if(this.room1Counter == 0) {
			this.mutex.release();
			this.t2.release();
		} else {
			this.mutex.release();
			this.t1.release();
		}
	}
	
	private void startCriticalSection() throws InterruptedException {
		this.t2.acquire();
		this.room2Counter--;
	}
	
	/**
	 * Finalizes the use of the thread for the critical section
	 */	
	public void endCriticalSection() throws InterruptedException {
		if(this.room2Counter == 0) {
			this.t1.release();
		}else {
			this.t2.release();
		}
	}
}
