import java.util.Random;

/**
 * This class is responsible for write an integer
 * into a DataBase Class.
 */
public class Writer implements Runnable {
	
	private final Database database;
	private int id;
    
  /**
	* Creates a Writer instance that will run indefinitely when executed
	* @param database the database the Writer will use to write a integer.
	* @param id the unique id of the Writer.
	*/
	public Writer(Database database, int id) {
		this.database = database;
		this.id = id;
	}

	@Override
	public void run() {
		while (true) {
			int value = new Random().nextInt(40);
      try {
				this.database.write(value);
				System.out.println("Writer " + this.id + " write: " + value);
			} catch (InterruptedException e) {}
    }
	}
	
}
