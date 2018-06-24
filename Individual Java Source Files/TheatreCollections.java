package tbs.server;

import java.util.List;
import java.util.Vector;

public class TheatreCollections {
    private List<Theatre> theatreList = new Vector<Theatre>(); //Theatres are saved here to have encapsulation

public void addTheatres(Theatre theater){
    theatreList.add(theater);
}

public List<Theatre> getTheatres(){
    return theatreList;
}


}
