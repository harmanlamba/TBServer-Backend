package tbs.server;

import java.util.List;

public class Act {
    private String _title;
    private String _artistID;
    private int _minutesDuration;
    private String _actID;

    public Act (String title, String artistID, int minutesDuration, int actID){
        _title=title;
        _artistID=artistID;
        _minutesDuration=minutesDuration;
        _actID="ACT"+actID;
    }

    public String getArtistID(Act act){
        return act._artistID;
    }

    public String getActID(Act act){
        return act._actID;
    }

    public String getActTitle(Act act){
        return act._title;
    }

    //Method that checks if the actID is valid actID
    public boolean isValidAct(List<Act> actsList,String actID){
        for(Act counter:actsList){
            if(counter.getActID(counter).equals(actID)){
                return true;
            }
        }
        return false;
    }

    //Method that checks if an act already exists
    public boolean isDuplicateAct(List<Act> actsList,String title, String artistID){
        for(Act counter:actsList){
            if(counter.getActTitle(counter).equalsIgnoreCase(title)&& counter.getArtistID(counter).equals(artistID)){
                return true;
            }
        }
        return false;
    }

    }
