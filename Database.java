import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Database {
	LinkedHashMap<String, LinkedList<Campervan>> lhm;



	/**
	 * The constructor of Database
	 * @param lhm
	 */
	public Database(LinkedHashMap<String, LinkedList<Campervan>> lhm) {
		
		this.lhm = lhm;
	}


/**
 * Get the lhm in the Database
 * @return
 */
	public LinkedHashMap<String, LinkedList<Campervan>> getlhm(){
		return lhm;
	}
	
	
}
