import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Request {
	static LinkedHashMap<String, LinkedList<Campervan>> reqlhm;



/**
 * The constructor of Request
 * @param reqlhm
 */
	public Request(LinkedHashMap<String, LinkedList<Campervan>> reqlhm) {

		this.reqlhm = reqlhm;
	}


/**
 * Get the reqlhm in the class Request
 * @return
 */
	public LinkedHashMap<String, LinkedList<Campervan>> getreq(){
		return reqlhm;
	}

	
/**
 * Cancel booking <id> (if it exists) and free up vehicles
 * @param id
 * @return
 */
	public LinkedHashMap<String, LinkedList<Campervan>> Cancel (int id){


		Iterator<Entry<String, LinkedList<Campervan>>> it = reqlhm.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			LinkedList <Campervan >tempreq = (LinkedList<Campervan>) pair.getValue();
			Campervan[] delcam = new Campervan[30];
			int h = 0;
			for(int l = 0;l<tempreq.size();l++){
				Campervan tempcam = tempreq.get(l);
				if(tempcam.id.contains(id)) {
					if(tempcam.id.size() == 1){	
						delcam[h++] = tempcam; 
						for(int n = 0;n < tempcam.dateList.size(); n++){
							if(tempcam.dateList.get(n).id == id){
								tempcam.dateList.remove(tempcam.dateList.get(n));
							}
						}
						
					}
					else{
						tempcam.id.remove(tempcam.id.indexOf(id));
						for(int n = 0;n < tempcam.dateList.size(); n++){
							if(tempcam.dateList.get(n).id == id){
								tempcam.dateList.remove(tempcam.dateList.get(n));
							}
						}
					}
					tempcam.Taken --;

				}
			}
			//remove extra
			for(int w = 0;delcam[w]!= null;w++){
			tempreq.remove(delcam[w]);
			}

		}


		return reqlhm;
	}


/**
 * Print the record of all vehicles in <depot>(location)
 * @param loc
 * @throws ParseException
 */
	public void reqlhmprint(String loc) throws ParseException{


		LinkedList <Campervan >templist = (LinkedList<Campervan>) reqlhm.get(loc);
		if(templist == null) return;

		//printout
		for(int l = 0;l < templist.size();l++){

			Campervan tempcam = templist.get(l);

			for(int m = tempcam.dateList.size()-1;m >= 0;m--){
				SimpleDateFormat format = new SimpleDateFormat("kk:mm MMM dd");
				String formatted1 = format.format(tempcam.dateList.get(m).begin.getTime());
				String formatted2 = format.format(tempcam.dateList.get(m).end.getTime());
				System.out.println(tempcam.location + " " + tempcam.name + " " + formatted1 + " " + formatted2);
			}


		}

	}

}
