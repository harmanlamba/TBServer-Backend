package tbs.server;

import java.util.List;

public class Ticket {
    private String _performanceID;
    private String _ticketID;
    private int _rowNumber;
    private int _seatNumber;

    public Ticket(String performanceID,int ticketID, int rowNumber, int seatNumber){
        _performanceID=performanceID;
        _ticketID="TICK"+ticketID;
        _rowNumber=rowNumber;
        _seatNumber=seatNumber;
    }

    //Method that verifies if a ticket has previously been issued
    public boolean isIssuedTicket(List<Ticket> ticketList, int rowNumber, int seatNumber){
        for (Ticket counter:ticketList){
            int[] seatingValues=counter.getSeatingValues(counter);
            int counterRow=seatingValues[0];
            int counterSeatNumber= seatingValues[1];
            if(counterRow==rowNumber && counterSeatNumber== seatNumber){
                return true;
            }
        }
        return false;
    }

    public String getTicketID(Ticket ticket){
        return ticket._ticketID;
    }

    public String getPerformanceID(Ticket ticket){
        return ticket._performanceID;
    }

    //Method that returns the seating value, once having the ticket.
    //The method outputs as an array to avoid having 2 methods one for the row and one for the seating value.
    public int[] getSeatingValues(Ticket ticket){
        int[] seatingValues= new int[2];
        seatingValues[0]=ticket._rowNumber;
        seatingValues[1]=ticket._seatNumber;
        return seatingValues;
    }
}
