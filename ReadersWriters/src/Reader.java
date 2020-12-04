/**
 * This class is responsible for read the integer
 * contained within a DataBase Class, and print it.
 */
public class Reader implements Runnable {
	
	private final Database database;
	private int id;
    
	/**
	 * Creates a Reader instance that will run indefinitely when executed
	 * @param database the database the Reader will use to read a integer from.
	 * @param id the unique id of the Reader.
	 */
	public Reader(Database database, int id) {
		this.database = database;
		this.id = id;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.database.beforeRead();
			} catch (InterruptedException e1) {}

			int value = this.database.read();
			System.out.println("Reader " + this.id + " read: " + value);

			try {
				this.database.afterRead();
			} catch (InterruptedException e1) {}
    		}
	}
}
