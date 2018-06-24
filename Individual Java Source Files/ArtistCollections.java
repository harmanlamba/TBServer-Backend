package tbs.server;

import java.util.List;
import java.util.Vector;

public class ArtistCollections {
    private List<Artist> artistList= new Vector<Artist>(); //Artist List, all the artists are saved here for the server
    private int _lastID=1;//Artist ID that is saved in the collection and is used during the creation of every artist

    public void addArtist(Artist artist) {
        artistList.add(artist);
    }

    public List<Artist> getArtistList(){
        return artistList;
    }

    public int getLastID(){
        return _lastID;
    }
    //Method is used to update the ID field, in order to ensure that no two ID's are the same.
    public void addLastID(){
        _lastID++;
    }
}
