
/**
 * This class allows you to test solution for Readers-Writers
 */
public class Example {

	public static void main(String[] args) {
		Database database = new Database();
		
		Thread tw1 = new Thread(new Writer(database,1));
		tw1.start();
		Thread tr1 = new Thread(new Reader(database,1));
		tr1.start();
		Thread tr2 = new Thread(new Reader(database,2));
		tr2.start();
	}

}
