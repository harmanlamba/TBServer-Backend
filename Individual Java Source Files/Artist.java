package tbs.server;

import java.util.List;

public class Artist {
    private String _artistID;
    private String _artistName;

    public Artist (String name, int ID){
        _artistName=name;
        _artistID="ART"+ID;
    }

    public String getArtistID(Artist artist){
        return artist._artistID;
    }

    public String getArtistName(Artist artist){
        return artist._artistName;
    }

    //Method that checks if the indicated artistID is in fact an artistID on the server
    public boolean isArtistID(List<Artist> artistList, String artistID){
        for(Artist counter:artistList){
            if(counter.getArtistID(counter).equals(artistID)) {
                return true;
            }
        }
        return false;
    }

    //Method that checks if the indicated artist name has previously been used
    public boolean isArtistNameUsed(List<Artist> artistList, String name){
        for(Artist counter:artistList){
            if(counter.getArtistName(counter).equals(name)){
                return true;
            }
        }
        return false;
    }


}
