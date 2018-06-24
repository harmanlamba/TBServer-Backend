package tbs.server;

import java.util.List;

public class Performance {
    private String _actID;
    private String _performanceID;
    private String _startTime;
    private String _theatreID;
    private String _cheapSeats;
    private String _premiumSeats;

public Performance(String actID, int performanceID, String startTime, String theatreID, String cheapSeats, String premiumSeats){
    _actID=actID;
    _performanceID="PERF"+performanceID;
    _startTime=startTime;
    _theatreID=theatreID;
    _cheapSeats=cheapSeats;
    _premiumSeats=premiumSeats;
}

public String getPerformanceID(Performance performance){
    return performance._performanceID;
}

public String getActID(Performance performance){
    return performance._actID;
}

public String getStartTime(Performance performance){
    return performance._startTime;
}

//Method that returns the ticket prices as an array in order to not have an extra method returning a second price
public int[] getTicketPrices(Performance performance){
    String cheapSeats=performance._cheapSeats;
    String premiumSeats=performance._premiumSeats;
    String filteredCheapSeats=cheapSeats.replaceAll("\\D+","");
    String filteredPremiumSeats=premiumSeats.replaceAll("\\D+","");
    int[] ticketPrices= new int[2];
    ticketPrices[0]=Integer.parseInt(filteredCheapSeats);
    ticketPrices[1]=Integer.parseInt(filteredPremiumSeats);

    return ticketPrices;
}

//Method that verifies that the performanceID does in fact correlate to previously added performances.
public boolean isValidPerformance(List<Performance> performanceList, String performanceID){
    for (Performance counter: performanceList){
        if(performanceID.equals(counter.getPerformanceID(counter))){
            return true;
        }
    }
    return false;
}

//Overloading has been done as in TBSServerImpl the theatreID is needed but in some methods different parameters are
//provided thus, overloading of the methods has been used.
public String getTheatreID(Performance performance){
    return performance._theatreID;
}

public String getTheatreID(List<Performance> performanceList, String performanceID){
    for(Performance counter:performanceList){
        if(performanceID.equals(counter.getPerformanceID(counter))){
            return counter._theatreID;
        }
    }
    return "ERROR: Theatre ID not found";
}
}
