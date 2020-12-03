/**
 * This class allows you to test solution for No-starve Mutex
 */
public class Example {

	public static void main(String[] args) {
		MutexRooms mutex = new MutexRooms();
		
		Thread t1 = new Thread(new ThreadExample(mutex,1));
		t1.start();
		Thread t2 = new Thread(new ThreadExample(mutex,2));
		t2.start();
		Thread t3 = new Thread(new ThreadExample(mutex,3));
		t3.start();
		Thread t4 = new Thread(new ThreadExample(mutex,4));
		t4.start();
	}

}
