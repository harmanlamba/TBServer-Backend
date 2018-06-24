package tbs.server;

import java.util.List;
import java.util.Vector;

public class ActCollections {
    private List<Act> acts= new Vector<Act>(); // ACT List, all the acts are saved here for the server
    private int _actID=1; //ACT ID that is saved in the collection and is used during the creation of every Act

//Adds act to the acts collection list
public void addAct(Act act){
    acts.add(act);
}

public int getLastActID(){
    return _actID;
}

//Used to update the actID field, so actID's do not repeat
public void incrementLastActID(){
    _actID++;
}

public List<Act> getActsList(){
    return acts;
}


}