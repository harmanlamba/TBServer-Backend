package tbs.server;

import java.util.List;

public class Theatre {
    private String _theatreID;
    private int _seatingDimensions;
    private int _floorArea;

    public Theatre(String theatreID, int seatingDimensions, int floorArea) {
        _theatreID=theatreID;
        _seatingDimensions=seatingDimensions;
        _floorArea=floorArea;

    }

    public String getTheaterID(Theatre theatre){
        return theatre._theatreID;
    }

    //Method that verifies if the theatreID is valid, i.e. is there a theatre with that ID
    public boolean isValidTheatre(List<Theatre> theatres, String theatreID){
        for(Theatre counter:theatres){
            if(counter.getTheaterID(counter).equals(theatreID)){
                return true;
            }

        }
        return false;
    }

    public int getSeatingDimension(List<Theatre> theatres, String theatreID){
        for(Theatre counter: theatres){
            if(theatreID.equals(counter._theatreID)){
                return counter._seatingDimensions;
            }
        }
        return -1;
    }
}
