package tbs.server;

import java.util.List;
import java.util.Vector;

public class TicketCollections {
    private List<Ticket> ticketList= new Vector<Ticket>();// Tickets are saved here in order to have Encapsulation
    private int _lastTicketID=1;//ID is used and updated during every ticket creation.

    public List<Ticket> getTicketList(){
        return ticketList;
    }

    public void addTicketToList(Ticket ticket){
        ticketList.add(ticket);
    }
    // ID's are kept and updated via this class in order to ensure distinct ID's during the creation of every ticket.
    public int getLastTicketID(){
        return _lastTicketID;
    }

    public void updateLastTicketID(){
        _lastTicketID++;
    }

}
