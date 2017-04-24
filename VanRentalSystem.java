
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VanRentalSystem {


	@SuppressWarnings("null")
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		
		/**
		 * Initialization
		 */
		String name = args[0];
		LinkedList<String> locatList = new LinkedList<String>();
		int changeflag = 0;
		LinkedHashMap<String, LinkedList<Campervan>> lhm = new LinkedHashMap<String, LinkedList<Campervan>>();
		LinkedHashMap<String, LinkedList<Campervan>> reqlhm = new LinkedHashMap<String, LinkedList<Campervan>>();
		Request newRequest = new Request(reqlhm);
		Database CampList= new Database(lhm);

		
		/**
		 * Get the input by calling fileread method
		 */
		 String[] mystr = new String[100];
		 mystr = fileread(name);
		 

		 /**
		  * Check what inputs are and do based on them
		  */
		for(int j = 0;mystr[j]!= null;j++){
			String[] temp;
			String[] temp2;
			temp = mystr[j].split("#");     
			temp2 = temp[0].split(" ");	      

			// If Location:
			if((temp2.length != 0) && (temp2[0].equals("Location"))){

				//Create the Campervan
				Campervan cam = new Campervan(temp2[1],temp2[2],temp2[3],new LinkedList<Integer>(), 0, new LinkedList<rentDate>());

				// If exist or not
				if(locatList.contains(cam.location))
				{
					LinkedList <Campervan >templist = (LinkedList) lhm.get(cam.location);
					templist.add(cam);
				}
				else
				{
					LinkedList<Campervan> dataList = new LinkedList<Campervan>();
					locatList.add(cam.location);
					dataList.add(cam) ;
					lhm.put(cam.location, dataList);
				}
				CampList.lhm = lhm;
			}



			/**
			 * if get the "Change" in input, do the Change.
			 */
			if((temp2.length != 0) && (temp2[0].equals("Change"))){
				changeflag = 1;
				//Cancel
				if(newRequest != null){
					int id = Integer.parseInt(temp2[1]);

					newRequest.reqlhm = newRequest.Cancel(id);
				}


				temp2[0] = "Request";
			}



			// If Request
			/**
			 * If get the "Request" in input, do the Request
			 */
			if((temp2.length != 0) && (temp2[0].equals("Request"))){

				//inatializaiton		
				int AutoNum = 0;
				int ManNum = 0;    						
				String mydata = temp[0];
				int id = Integer.parseInt(temp2[1]);
				String priStr = "";

				/**
				 * Build cal1 and cal2 by the method getcal1 and getcal2
				 */
				Calendar cal1 = getcal1(temp2);
				Calendar cal2 = getcal2(temp2);

				/**
				 * Get AutoNum and ManNum by getAutoNum and getManNum
				 */
				AutoNum = getAutoNum(mydata);
				ManNum = getManNum(mydata);


				/**
				 * Check whether it is Change or Booking
				 */
				if(changeflag == 1){
					System.out.printf("Change ");
					changeflag = 0; 
				}
				else{
					System.out.printf("Booking ");
				}


				priStr = priStr + temp2[1];

				int count1 = 0;
				int count2 = 0;

				String locflag = "";

				// Get campervans
				for(int l = 0;l < locatList.size(); l++){
					LinkedList<Campervan> tempList = (LinkedList<Campervan>) CampList.getlhm().get(locatList.get(l));
					for (int n = 0; n < tempList.size(); n++)
					{
						Campervan tempcam = tempList.get(n);

						//Get Manual
						if(tempcam.AuorMa.equals("Manual") && count1 < ManNum  && tempcam.available(cal1,cal2,id))
						{						
							if(!tempcam.location.equals(locflag))
							{
								if(!locflag.equals("")){									
									priStr = priStr + ";";
								}
								priStr = priStr + " " + tempcam.location + " ";
								locflag = tempcam.location;
							}
							else{
								priStr = priStr + ", ";
							}

							count1++;
							tempcam.id.add(id);


							if(reqlhm.containsKey(tempcam.location))
							{
								LinkedList <Campervan >templist2 = (LinkedList<Campervan>) reqlhm.get(tempcam.location);

								if(tempcam.Taken != 0){
									for (int m = 0; m < templist2.size();m++){
										if(templist2.get(m).name.equals(tempcam.name)){
											templist2.get(m).dateList.add(new rentDate(cal1,cal2,id));
										}
									}
								}
								else{
									templist2.add(tempcam);
								}
							}
							else
							{
								LinkedList<Campervan> dataList = new LinkedList<Campervan>();
								dataList.add(tempcam) ;
								reqlhm.put(tempcam.location, dataList);

							}
							tempcam.Taken++;
							priStr = priStr + tempcam.name;
						}

						//Get Auto
						if(tempcam.AuorMa.equals("Automatic") && count2 < AutoNum && tempcam.available(cal1,cal2,id))   
						{
							if(!tempcam.location.equals(locflag))
							{
								if(!locflag.equals("")){
									priStr = priStr + ";";
								}

								priStr = priStr + " " + tempcam.location + " ";
								locflag = tempcam.location;
							}
							else{
								priStr = priStr + ", ";
							}
							tempcam.id.add(id);
							count2++;

							if(reqlhm.containsKey(tempcam.location))
							{
								LinkedList <Campervan >templist2 = (LinkedList<Campervan>) reqlhm.get(tempcam.location);

								if(tempcam.Taken != 0){
									for (int m = 0; m < templist2.size();m++){
										if(templist2.get(m).name.equals(tempcam.name)){
											templist2.get(m).dateList.add(new rentDate(cal1,cal2,id));
										}
									}
								}
								else{
									templist2.add(tempcam);

									
								}
							}
							else
							{
								LinkedList<Campervan> dataList = new LinkedList<Campervan>();
								dataList.add(tempcam) ;				
								reqlhm.put(tempcam.location, dataList);							
							}
							tempcam.Taken++;

							priStr = priStr + tempcam.name;
						}    				  
					}
				}

				if(count1 != ManNum || count2 != AutoNum)
				{
					System.out.println("rejected");
				}
				else{
					System.out.println(priStr);
				}

				newRequest.reqlhm = reqlhm;
			}
			/**
			 * If get "Print" in input, do the print by calling reqlhmprint() method in newRequest
			 */
			if((temp2.length != 0) && (temp2[0].equals("Print"))){
				if(newRequest != null){
					newRequest.reqlhmprint(temp2[1]);
				}
			}

			/**
			 * If get "Cancel" in input, do the Cancel by calling the Cancel(id) method in the newRequest 
			 */
			if((temp2.length != 0) && (temp2[0].equals("Cancel"))){
				if(newRequest != null){
					System.out.println("Cancel " + temp2[1]);
					int id = Integer.parseInt(temp2[1]);
					newRequest.reqlhm = newRequest.Cancel(id);
				}
			}




		}

	}
	
	
	
	
	/**
	 * Read the input from a file whose name passed as an argument(name) to the main method in the call to java
	 * */					
	
	public static String[] fileread(String name){
		
		Scanner sc = null;
		String textStr = "";
		String[] mystr = new String[100];
		int i = 0;
		
		try
		{
			sc = new Scanner(new FileReader(name));    // args[0] is the first command line argument
			while(sc.hasNextLine()){
				textStr = sc.nextLine();
				if(mystr[i] == null){
					mystr[i] = "";
				}
				mystr[i] = mystr[i] + textStr + " ";
				i++;
			}
		}
		catch (FileNotFoundException e) {System.out.println("File not found");}
		finally
		{
			if (sc != null) sc.close();

		}

		return mystr;
		
	}
	
	/**
	 * Get the Calendar cal1 based on temp2 which is from the input
	 * @param temp2
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getcal1(String[] temp2) throws ParseException{
		
		int hour1 = Integer.parseInt(temp2[2]);				
		Calendar tempcal1 = Calendar.getInstance();
		tempcal1.setTime(new SimpleDateFormat("MMM").parse(temp2[3]));
		int month1 = tempcal1.get(Calendar.MONTH);
		int day1 = Integer.parseInt(temp2[4]);
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2017,month1,day1,hour1, 00,00);
		return cal1;		
	}
	
	/**
	 * Get the Calendar cal2 based on temp2 which is from the input
	 * @param temp2
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getcal2(String[] temp2) throws ParseException{
		int hour2 = Integer.parseInt(temp2[5]);				
		Calendar tempcal2 = Calendar.getInstance();
		tempcal2.setTime(new SimpleDateFormat("MMM").parse(temp2[6]));
		int month2 = tempcal2.get(Calendar.MONTH);	
		int day2 = Integer.parseInt(temp2[7]);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2017,month2,day2,hour2, 00,00);
		return cal2;		
	}
	
	/**
	 * Get the AutoNum(the number of Automatic campervans in the booking )
	 * @param mydata
	 * @return
	 */
	public static int getAutoNum(String mydata){
		int AutoNum = 0;
		String temp3[];
		Pattern pattern1 = Pattern.compile("([0-9] Automatic)");
		Matcher matcher1 = pattern1.matcher(mydata);
		if (matcher1.find())
		{
			temp3 = matcher1.group(1).split(" ");
			AutoNum = Integer.parseInt(temp3[0]);
			return AutoNum;
		}
		return AutoNum;
	}
	
	/**
	 * Get the ManNum(the number of Manual campervans in the booking)
	 * @param mydata
	 * @return
	 */
	public static int getManNum(String mydata){
		int ManNum = 0;
		String temp4[];
		Pattern pattern2 = Pattern.compile("([0-9] Manual)");
		Matcher matcher2 = pattern2.matcher(mydata);
		if (matcher2.find())
		{
			temp4 = matcher2.group(1).split(" ");
			ManNum = Integer.parseInt(temp4[0]);

		}
		return ManNum;
	}
	

	
}
