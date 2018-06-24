package tbs.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class TBSServerImpl implements TBSServer {
    //Initializing Global objects in order to access the encapsulated collections and store data on the server
    private TheatreCollections theatreCollectionsOb = new TheatreCollections();
    private ArtistCollections artistCollectionsOb = new ArtistCollections();
    private ActCollections actCollectionsOb = new ActCollections();
    private PerformanceCollections performanceCollectionsOb = new PerformanceCollections();
    private TicketCollections ticketCollectionsOb = new TicketCollections();

    @Override
    public String initialise(String path) {
        try {
            List<String> theatreIDs = new Vector<String>();
            Scanner fScn = new Scanner(new File(path));
            String data;

            while (fScn.hasNextLine()) { //Reading the file and assigning the values to Theater objects fields
                data = fScn.nextLine();
                String[] values = data.split("\t");
                String theatreID = values[1];
                int seatingDimension;
                int floorArea;
                try {
                    seatingDimension = Integer.parseInt(values[2]);
                    floorArea = Integer.parseInt(values[3]);
                } catch (NumberFormatException e) {
                    return "ERROR: File format is not correct";
                }

                if (!(values[0] instanceof String)) {
                    return "ERROR: File format is not correct";
                } else if (!(theatreID instanceof String)) {
                    return "ERROR: File format is not correct";
                }
                //Creating a new Theatre object to save the values from the file
                Theatre theatre = new Theatre(theatreID, seatingDimension, floorArea);
                theatreCollectionsOb.addTheatres(theatre); //Adding object to the encapsulated list
                theatreIDs.add(theatreID);//List is eventually going to be used to check for duplicates

            }
            fScn.close();
            Set<String> comparisonSet = new HashSet<String>(theatreIDs);
            if (comparisonSet.size() < theatreIDs.size()) { //Checking for duplicate theatres
                return "ERROR: There is a duplicate theatre in the File";
            }
        } catch (FileNotFoundException e) {
            return "ERROR: File Not Found";
        }
        return "";
    }

    @Override
    public List<String> getTheatreIDs() {
        //Accessing the encapsulated theatre list
        List<Theatre> theatresList = theatreCollectionsOb.getTheatres();
        List<String> theatreIDs = new Vector<String>();
        //Looping and adding each theatre in the list which is going to be outputted
        for (Theatre counter : theatresList) {
            theatreIDs.add(counter.getTheaterID(counter));
        }
        Collections.sort(theatreIDs);
        return theatreIDs;
    }

    @Override
    public List<String> getArtistIDs() {
        //Accessing the encapsulated artist list
        List<Artist> artistList = artistCollectionsOb.getArtistList();
        List<String> artistIDs = new Vector<String>();
        //Looping around each artist and adding their ID to a separate list which will be outputted
        for (Artist counter : artistList) {
            artistIDs.add(counter.getArtistID(counter));
        }
        Collections.sort(artistIDs);
        return artistIDs;
    }

    @Override
    public List<String> getArtistNames() {
        //Accessing the encapsulated artist list
        List<Artist> artistList = artistCollectionsOb.getArtistList();
        List<String> artistsNames = new Vector<String>();
        //Looping and adding each artist name to its own list which will then be returned after sorting
        for (Artist counter : artistList) {
            String name = counter.getArtistName(counter);
            artistsNames.add(name);
        }
        Collections.sort(artistsNames);
        return artistsNames;
    }

    @Override
    public List<String> getActIDsForArtist(String artistID) {
        List<Act> actsList = actCollectionsOb.getActsList();
        List<Artist> artistList = artistCollectionsOb.getArtistList();
        List<String> actIDsforArtist = new Vector<String>();

        //Trying to see if any artists have been added making sure there is no OutOfBoundsException
        try {
            artistList.get(0).isArtistID(artistList, artistID);
        } catch (ArrayIndexOutOfBoundsException e) {
            actIDsforArtist.add("ERROR: No artist's have been added yet");
            return actIDsforArtist;
        }
        //Performing some preliminary checks
        if (artistID.isEmpty()) {
            actIDsforArtist.add("ERROR: The artist ID is empty.");
        } else if (!artistList.get(0).isArtistID(artistList, artistID)) {
            actIDsforArtist.add("ERROR: Artist with that artistID does not exist");
            return actIDsforArtist;
        } else { //Looping through all acts to find the acts that belong to the indicated Artist
            for (Act counter : actsList) {
                if (counter.getArtistID(counter).equals(artistID)) {
                    actIDsforArtist.add(counter.getActID(counter));
                }
            }
            Collections.sort(actIDsforArtist);
        }
        return actIDsforArtist;
    }

    @Override
    public List<String> getPeformanceIDsForAct(String actID) {
        List<Performance> performanceList = performanceCollectionsOb.getPerformanceList();
        List<Act> actList = actCollectionsOb.getActsList();
        List<String> performanceIDsForActs = new Vector<String>();

        //Checking to see if acts have been added to the server previously
        try {
            actList.get(0).isValidAct(actList, actID);
        } catch (ArrayIndexOutOfBoundsException e) {
            performanceIDsForActs.add("ERROR: No acts have been added yet");
            return performanceIDsForActs;
        }
        //Performing some preliminary checks
        if (actID.isEmpty()) {
            performanceIDsForActs.add("ERROR: actID cannot be empty");
        } else if (!actList.get(0).isValidAct(actList, actID)) {
            performanceIDsForActs.add("ERROR: There is no act with that ID");
        } else {
            for (Performance counter : performanceList) { //Looping through each performance and adding the ones with
                if (counter.getActID(counter).equals(actID)) { // the associated actID to its own list which will be outputted.
                    performanceIDsForActs.add(counter.getPerformanceID(counter));
                }
            }
        }
        Collections.sort(performanceIDsForActs);
        return performanceIDsForActs;
    }

    @Override
    public List<String> getTicketIDsForPerformance(String performanceID) {
        List<Performance> performanceList = performanceCollectionsOb.getPerformanceList();
        List<Ticket> ticketList = ticketCollectionsOb.getTicketList();
        List<String> TicketIDsForPerformance = new Vector<String>();
        //Checking that performanceID is not empty
        if (performanceID.isEmpty()) {
            TicketIDsForPerformance.add("ERROR: Performance ID cannot be empty");
            return TicketIDsForPerformance;
        }

        //Checking to see if performances have been previously added to the server
        if (performanceList.size() <= 0) {
            TicketIDsForPerformance.add("ERROR: No performances have been added yet");
            return TicketIDsForPerformance;
        }else if (!performanceList.get(0).isValidPerformance(performanceList, performanceID)) {
            TicketIDsForPerformance.add("ERROR: Performance ID does not exist");
            return TicketIDsForPerformance;
        }
        //Looping  through tickets until finding the matching performance ID
        for (Ticket counter : ticketList) {
            if (counter.getPerformanceID(counter).equals(performanceID)) {
                TicketIDsForPerformance.add(counter.getTicketID(counter)); //Adding the matching performanceID to own list
            }
        }
        Collections.sort(TicketIDsForPerformance);
        return TicketIDsForPerformance;
    }

    @Override
    public String addArtist(String name) {
        int ID = artistCollectionsOb.getLastID();
        List<Artist> artistList = artistCollectionsOb.getArtistList();
        Artist artist = new Artist(name, ID);

        if (name.isEmpty()) {
            return "ERROR: Name cannot be empty";
        }

        //In the case of the first artist being added
        if (artistList.size() <= 0) {
            artistCollectionsOb.addArtist(artist);
            artistCollectionsOb.addLastID();
            return artist.getArtistID(artist);
        } else {
            if (!artistList.get(0).isArtistNameUsed(artistList, name)) { //Checking for duplicate names
                artistCollectionsOb.addArtist(artist);
                artistCollectionsOb.addLastID();
                return artist.getArtistID(artist);
            } else if (artistList.get(0).isArtistNameUsed(artistList, name)) {
                return "ERROR: Artist name already exists";
            } else {
                return "ERROR: Artist ID already exists";
            }
        }
    }

    @Override
    public String addAct(String title, String artistID, int minutesDuration) {
        List<Artist> artistList = artistCollectionsOb.getArtistList();
        List<Act> actList = actCollectionsOb.getActsList();
        int actID = actCollectionsOb.getLastActID();

        //Checking to see that the title is not null or empty
        if (title.isEmpty()) {
            return "ERROR: Title is empty";
        }

        //In the case of the first act
        try {
            actList.get(0).isDuplicateAct(actList, title, artistID);
        } catch (ArrayIndexOutOfBoundsException e) {
            Act act = new Act(title, artistID, minutesDuration, actID);
            actCollectionsOb.addAct(act);
            actCollectionsOb.incrementLastActID();
            return act.getActID(act);
        }
        //Performing other preliminary checks
        if (!artistList.get(0).isArtistID(artistList, artistID)) { //Checking to make sure artistID is valid
            return "ERROR: Artist ID does not exist";
        } else if (minutesDuration <= 0) {
            return "ERROR: Minutes duration cannot be less than or equal to 0";
        } else if (actList.get(0).isDuplicateAct(actList, title, artistID)) { //Checking for duplicate Acts
            return "ERROR: Act Title already exist for that artist.";
        } else { //Creating the act and saving it in its encapsulated collection
            Act act = new Act(title, artistID, minutesDuration, actID);
            actCollectionsOb.addAct(act);
            actCollectionsOb.incrementLastActID();
            return act.getActID(act);
        }
    }

    @Override
    public String schedulePerformance(String actID, String theatreID, String startTimeStr, String
            premiumPriceStr, String cheapSeatsStr) {
        List<Act> actsList = actCollectionsOb.getActsList();
        List<Theatre> theatreList = theatreCollectionsOb.getTheatres();
        int performanceIDInt = performanceCollectionsOb.getLastID();
        int cheapSeatInt;
        int premiumPriceInt;

        //Checking for the correct format of the parameters and if theaters were previously added
        try {
            cheapSeatInt = Integer.parseInt(cheapSeatsStr.replaceAll("[$,]", ""));
            premiumPriceInt = Integer.parseInt(premiumPriceStr.replaceAll("[$,]", ""));
            theatreList.get(0).isValidTheatre(theatreList, theatreID);
        } catch (NumberFormatException e) {
            return "ERROR: Invalid Price";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "ERROR: No theatres have been added to the server yet";
        }
        //Performing other checks such as act validity, theatre validity, correct time format, and prices
        if (!actsList.get(0).isValidAct(actsList, actID)) {
            return "ERROR: Act with ActID does not exist";
        } else if (!theatreList.get(0).isValidTheatre(theatreList, theatreID)) {
            return "ERROR: Theatre with TheatreID does not exist";
        } else if (!startTimeStr.matches("([0-9]{4})-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}")) {
            return "ERROR: The startTimeStr is not in ISO8601 format.";
        } else if (cheapSeatsStr.isEmpty() || premiumPriceStr.isEmpty()) {
            return "ERROR: Invalid Price";
        } else if (cheapSeatInt < 0 || premiumPriceInt < 0) {
            return "ERROR: Invalid Price";
        }
        //Creating the new performance and adding it to its own collection
        Performance performance = new Performance(actID, performanceIDInt, startTimeStr, theatreID, cheapSeatsStr, premiumPriceStr);
        performanceCollectionsOb.addPerformance(performance);
        performanceCollectionsOb.updateLastID();
        return performance.getPerformanceID(performance);
    }

    @Override
    public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
        List<Performance> performanceList = performanceCollectionsOb.getPerformanceList();
        List<Theatre> theatreList = theatreCollectionsOb.getTheatres();
        List<Ticket> ticketList = ticketCollectionsOb.getTicketList();
        List<Ticket> ticketListAssociatedPerformance = new Vector<Ticket>();
        int ticketID = ticketCollectionsOb.getLastTicketID();
        //Checking to see if performances and theatres have been previously added to the server
        if (performanceList.size() <= 0) {
            return "ERROR: No performances have been added";
        } else if (theatreList.size() <= 0) {
            return "ERROR: No theatres have been added";
        }if (!performanceList.get(0).isValidPerformance(performanceList, performanceID)) {
            return "ERROR: Performance ID does not exist";
        }

        //Getting the seating dimensions from the Theatre Object
        String theatreID = performanceList.get(0).getTheatreID(performanceList, performanceID);
        int seatingDimensions = theatreList.get(0).getSeatingDimension(theatreList, theatreID);

        //Making sure the seat is valid and exists
        if ((rowNumber <= 0 || rowNumber > seatingDimensions) || (seatNumber <= 0 || seatNumber > seatingDimensions)) {
            return "ERROR: Seat does not exist";
        }

        //Creating a separate ticket list which is associated with the indicated performance ID to later check if
        //the seats have already been taken
        for (Ticket counter : ticketList) {
            if (performanceID.equals(counter.getPerformanceID(counter))) {
                ticketListAssociatedPerformance.add(counter);
            }
        }

        //In the case of the first ticket being issued
        try {
            ticketList.get(0).isIssuedTicket(ticketListAssociatedPerformance, rowNumber - 1, seatNumber - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            Ticket ticket = new Ticket(performanceID, ticketID, rowNumber - 1, seatNumber - 1);
            ticketCollectionsOb.addTicketToList(ticket);
            ticketCollectionsOb.updateLastTicketID();
            return ticket.getTicketID(ticket);
        }
        //Checking to see if the ticket has already been issued previously
        if (ticketList.get(0).isIssuedTicket(ticketListAssociatedPerformance, rowNumber - 1, seatNumber - 1)) { //Using the associated list to check for booked seats.
            return "ERROR: Seat had already been taken";
        }
        Ticket ticket = new Ticket(performanceID, ticketID, rowNumber - 1, seatNumber - 1);
        ticketCollectionsOb.addTicketToList(ticket);
        ticketCollectionsOb.updateLastTicketID();
        return ticket.getTicketID(ticket);
    }

    @Override
    public List<String> seatsAvailable(String performanceID) {
        List<Ticket> ticketList = ticketCollectionsOb.getTicketList();
        List<Ticket> ticketListForPerformance = new Vector<Ticket>();
        List<String> seatsAvailable = new Vector<String>();
        List<Performance> performanceList = performanceCollectionsOb.getPerformanceList();
        List<Theatre> theatreList = theatreCollectionsOb.getTheatres();
        //Performing preliminary checks
        if (performanceID.isEmpty()) {
            seatsAvailable.add("ERROR: Performance ID cannot be empty");
            return seatsAvailable;
        } else if (performanceList.size() <= 0) {
            seatsAvailable.add("ERROR: No performances have been added to the server");
            return seatsAvailable;
        } else if (theatreList.size() <= 0) {
            seatsAvailable.add("ERROR: No theatres have been added to the server");
        } else if (!performanceList.get(0).isValidPerformance(performanceList, performanceID)) {
            seatsAvailable.add("ERROR: Performance ID is not valid/non-existent");
            return seatsAvailable;
        }

        //Adding the Tickets that correspond to the performanceID in a separate list
        for (Ticket counter : ticketList) {
            if (counter.getPerformanceID(counter).equals(performanceID)) {
                ticketListForPerformance.add(counter);
            }
        }

        //Getting the seatingDimension for the specified performanceID to be able to iterate
        String theatreID = performanceList.get(0).getTheatreID(performanceList, performanceID);
        int seatingDimensions = theatreList.get(0).getSeatingDimension(theatreList, theatreID);

        //Creating a dummy ticket in order to access the instance method
        //A dummy ticket is used as its scope is only this function
        Ticket dummyTicket = new Ticket("NV", -1, -1, -1);
        //Adding the seats to the list by iterating
        for (int i = 0; i < seatingDimensions; i++) {
            for (int j = 0; j < seatingDimensions; j++) {
                //Checking to see if the seat has already been booked/taken
                if (!dummyTicket.isIssuedTicket(ticketListForPerformance, i, j)) {
                    seatsAvailable.add((i + 1) + "\t" + (j + 1));
                }
            }
        }
        return seatsAvailable;
    }

    @Override
    public List<String> salesReport(String actID) {
        List<Act> actList = actCollectionsOb.getActsList();
        List<String> salesReport = new Vector<String>();

        //Performing initial checks to make sure actID is valid
        if (actID.isEmpty() || actID.equals(null)) {
            salesReport.add("ERROR: actID cannot be an empty string or null");
            return salesReport;
        } else if (!actList.get(0).isValidAct(actList, actID)) {
            salesReport.add("ERROR: actID does not exist");
            return salesReport;

        }
        //Accessing performanceList , Theatres, and Tickets in order to find the tickets associated with the performances
        //and thus access the theatre object to find the seatingDimensions of the theatre.
        List<Performance> performanceList = performanceCollectionsOb.getPerformanceList();
        List<Performance> associatedPerformance = new Vector<Performance>();
        List<Theatre> theatreList = theatreCollectionsOb.getTheatres();
        List<Ticket> ticketList = ticketCollectionsOb.getTicketList();

        //Finding and separating all performances associated with actID
        for (Performance counter : performanceList) {
            if (actID.equals(counter.getActID(counter))) {
                associatedPerformance.add(counter);
            }
        }

        //Finding and adding all the tickets sold corresponding to the performances based on the performanceID
        for (Performance performanceCounter : associatedPerformance) {
            //Initializing variables as each performance is different. Hence each iteration needs a new declaration.
            int cheapTicketSold = 0;
            int premiumTicketSold = 0;
            int[] ticketPrice = performanceCounter.getTicketPrices(performanceCounter);
            int cheapSeatsPrice = ticketPrice[0];
            int premiumSeatsPrice = ticketPrice[1];
            for (Ticket ticketCounter : ticketList) {
                //Checking that the ticket matches the performance
                if (performanceCounter.getPerformanceID(performanceCounter).equals(ticketCounter.getPerformanceID(ticketCounter))) {
                    String theatreID = performanceCounter.getTheatreID(performanceCounter);
                    int seatingDimensions = theatreList.get(0).getSeatingDimension(theatreList, theatreID);
                    int[] seatingValues = ticketCounter.getSeatingValues(ticketCounter);
                    int rowNumberTicket = seatingValues[0];
                    //Counting how many of each type of ticket has been sold
                    if (rowNumberTicket >= 0 && rowNumberTicket < Math.floor(seatingDimensions / 2)) {
                        premiumTicketSold++;
                    } else {
                        cheapTicketSold++;
                    }
                }
            }
            String performanceID = performanceCounter.getPerformanceID(performanceCounter);
            String startTime = performanceCounter.getStartTime(performanceCounter);
            int totalSalesReceipt = (premiumTicketSold * premiumSeatsPrice) + (cheapSeatsPrice * cheapTicketSold);
            //Formatting the report and adding the performance. As all performance associated to the act must be returned
            //This statement is located inside the outer for loop
            salesReport.add(performanceID + "\t" + startTime + "\t" + (premiumTicketSold + cheapTicketSold) + "\t" + "$" + totalSalesReceipt);
        }
        return salesReport;
    }

    @Override
    public List<String> dump() {
        return null;
    }
}
