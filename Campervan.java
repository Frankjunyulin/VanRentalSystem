import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Campervan{
	public String location;
	public String name;
	public String AuorMa;
	public int Taken;
	//public Calendar begin;
	//public Calendar end;
	public rentDate rentdate = new rentDate(null,null,0);
	LinkedList<rentDate> dateList;
	LinkedList<Integer> id;
	//LinkedHashMap<rentDate, Integer> idtime = new LinkedHashMap<rentDate, Integer>();



/**
 * The constructor of Campervan
 * @param location
 * @param name
 * @param auorMa
 * @param id
 * @param taken
 * @param dateList
 */
	public Campervan(String location, String name, String auorMa,  LinkedList<Integer> id, int taken, LinkedList<rentDate> dateList) {
		super();
		this.location = location;
		this.name = name;
		AuorMa = auorMa;
		Taken = taken;
		this.dateList = dateList;
		this.id = id;
	}



	//Check available and add rentdate if empty
	/**
	 * Check if the Campervan is available 
	 * 
	 * And if the dateList is empty, add the rentdate. 
	 * @param c1
	 * @param c2
	 * @param id
	 * @return
	 */
	public boolean available(Calendar c1, Calendar c2,int id){
		rentdate = new rentDate(c1,c2,id);

		if (this.Taken == 0){
			dateList.add(rentdate);

			return true;
		}
		for(int l = 0;l < dateList.size();l++){
			Calendar calendar1 = this.dateList.get(l).end; // this would default to now


			calendar1.add(Calendar.HOUR_OF_DAY, +1);


			if (!(c1.after(this.dateList.get(l).end) || c2.before(this.dateList.get(l).begin))){
				calendar1.add(Calendar.HOUR_OF_DAY, -1);
				return false;
			}
			calendar1.add(Calendar.HOUR_OF_DAY, -1);
		}



		return true;
	}

}







