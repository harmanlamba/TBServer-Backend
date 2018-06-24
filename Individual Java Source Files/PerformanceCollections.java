package tbs.server;

import java.util.List;
import java.util.Vector;

public class PerformanceCollections {
    private List<Performance> performancesList = new Vector<Performance>();//Performances are saved in this List for the server.
    private int _lastID = 1; //PerformanceID that is saved in the collection and is used during the creation of every new performance


    public void addPerformance(Performance performance) {
        performancesList.add(performance);
    }
// The ID methods are used to ensure that no two performances have the same ID. Thus the last ID is always updated during
// the creation of a new performance.
    public int getLastID() {
        return _lastID;
    }

    public void updateLastID() {
        _lastID++;
    }

    public List<Performance> getPerformanceList(){
        return performancesList;
    }


}
