/**
 *	This class is responsible for 
 *	simulate the use of the critical section by a thread
 */
public class ThreadExample implements Runnable {
	
	private MutexRooms mutex;
	private int id;
	
	/**
	 * Creates a ThreadExample instance that will run indefinitely when executed
	 * @param MutexRooms the MutexRooms the ThreadExample will use to enter the critical section safely.
	 * @param id the unique id of the ThreadExample.
	 */
	public ThreadExample(MutexRooms mutex, int id) {
		this.mutex = mutex;
		this.id = id;
	}

	@Override
	public void run() {
		while(true) {
			try { 
				
				System.out.println("Thread " + this.id + " wants to get the critical section.");
				
				this.mutex.getCriticalSection();
				System.out.println("Thread " + this.id + " is in the critical section.");
				this.mutex.endCriticalSection();
				
				Thread.sleep(4000); 
				
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
	
}
