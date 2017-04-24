# VanRentalSystem
This is a prototype system that could serve as the "back-end" of a campervan rental system. Customers can make, change and delete campervan bookings. Campervans can be either Manual or Automatic. A vehicle must be picked up and dropped off at the same depot. Each booking has an ID number and is for one or more campervans (manual and/or automatic) for a period of time given by a start date and an end date (assume 2017 is the year for all dates, and dates are in the format HH MMM DD). A booking request is either granted in full or is completely rejected by the system; there are no bookings partially filled.

All input will be a sequence of lines of the following form, where, in addition, a comment (starting with a '#' character) can appear at the end of a line. Your program should be able to process and discard such comments (this includes whole lines that consist only of a comment).

Location <depot> <name> <type>
        # specify that <depot> has a campervan with <name> that has transmission <type>
Request <id> <hour1> <month1> <date1> <hour2> <month2> <date2> <num1> <type1> [<num2> <type2>]
        # booking request <id> is from <hour1> <month1> <date1> to <hour2> <month2> <date2> for <num1> vehicles of type <type1>, etc.
Change <id> <hour1> <month1> <date1> <hour2> <month2> <date2> <num1> <type1> [<num2> <type2>]
        # change booking <id> to be from <hour1> <month1> <date1> to <hour2> <month2> <date2> with <num1> vehicles of type <type1>, etc.
Cancel <id>
        # cancel booking <id> (if it exists) and free up vehicles
Print <depot>
        # print record of all vehicles in <depot>
        
