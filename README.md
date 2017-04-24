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
        

Sample Input

Below is an example of the input form and meaning. Note that you will have to submit at least three input test files with your assignment. These test files should include one or more comments to specify what scenario is being tested (see Requests 1 and 2 for illustration). However, the following sample input includes many comments added only to explain the input format; your input test files do not need to contain this many comments.

Location CBD Wicked Automatic         # Location CBD has Wicked campervan with automatic transmission
Location CBD Zeppelin Automatic       # Location CBD has Zeppelin campervan with automatic transmission
Location CBD Floyd Automatic          # Location CBD has Floyd campervan with automatic transmission
Location Penrith Queen Manual         # Location Penrith has Queen campervan with manual transmission
Location Cremorne Ramones Automatic   # Location Cremorne has Ramones campervan with automatic transmission
Location Cremorne Nirvana Automatic   # Location Cremorne has Nirvana campervan with automatic transmission
Location Sutherland Purple Manual     # Location Sutherland has Purple campervan with manual transmission
Location Sutherland Hendrix Manual    # Location Sutherland has Hendrix campervan with manual transmission
Location Sutherland Eagle Manual      # Location Sutherland has Eagle campervan with manual transmission
# Test whether campervans can be rented from different depots when one depot exhausts its allocation of vehicles
Request 1 23 Mar 25 12 Mar 26 3 Automatic 1 Manual
        # Request 1 is for 3 Automatic and 1 Manual campervan from 23:00 on Mar 25 to 12:00 on Mar 26
        # Assign Wicked, Zeppelin, Floyd of CBD and Queen of Penrith
        # Output Booking 1 CBD Wicked, Zeppelin, Floyd; Penrith Queen
# Test whether vans already booked are skipped and the next available van in order of declaration is assigned to booking
Request 2 12 Mar 24 15 Mar 27 1 Manual
        # Request 2 is for 1 Manual campervan from 12:00 on Mar 24 to 15:00 on Mar 27
        # Assign Purple of Sutherland since Queen of Penrith is booked
        # Output Booking 2 Sutherland Purple
Request 3 13 Mar 26 21 Mar 26 2 Automatic 2 Manual
        # Request 3 is for 2 Automatic and 2 Manual vans from 13:00 on Mar 26 to 21:00 on Mar 26
        # Assign Wicked and Zeppelin of CBD, Queen of Penrith and Hendrix of Sutherland
        # Output Booking 3 CBD Wicked, Zeppelin; Penrith Queen; Sutherland Hendrix
Change 1 23 Mar 27 23 Mar 29 3 Manual 2 Automatic
        # Change booking 1 to 3 Manual and 2 Automatic vans from 23:00 on Mar 27 to 23:00 on Mar 29
        # Deassign Wicked, Zeppelin, Floyd of CBD and Queen of Penrith, and assign Queen of Penrith,
        # Purple and Hendrix of Sutherland and Wicked and Zeppelin of CBD
        # Output Change 1 CBD Wicked, Zeppelin; Penrith Queen; Sutherland Purple, Hendrix
Request 4 11 Mar 25 09 Mar 26 1 Automatic
        # Request 4 is for 1 Automatic campervan from 11:00 on Mar 25 to 9:00 on Mar 26
        # Assign Wicked of CBD
        # Output Booking 4 CBD Wicked
Cancel 3
        # Cancel booking 3
        # Deassign Wicked and Zeppelin of CBD, Queen of Penrith and Hendrix of Sutherland
        # Output Cancel 3
Request 5 11 Mar 26 09 Mar 27 4 Manual
        # Request 5 is for 4 Manual campervans from 11:00 on Mar 26 to 9:00 on Mar 27
        # Request cannot be fulfilled
        # Output Booking rejected
Print CBD
        # Print out bookings of all campervans at CBD, in order of campervan declarations, then date/time;
        # for each booking giving the start and end time and date in the format described above

Sample Output

The output corresponding to the above input is as follows:

Booking 1 CBD Wicked, Zeppelin, Floyd; Penrith Queen
Booking 2 Sutherland Purple
Booking 3 CBD Wicked, Zeppelin; Penrith Queen; Sutherland Hendrix
Change 1 CBD Wicked, Zeppelin; Penrith Queen; Sutherland Purple, Hendrix
Booking 4 CBD Wicked
Cancel 3
Booking rejected
CBD Wicked 11:00 Mar 25 09:00 Mar 26
CBD Wicked 23:00 Mar 27 23:00 Mar 29
CBD Zeppelin 23:00 Mar 27 23:00 Mar 29
