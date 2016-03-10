package freemap.opentrail03;



import java.util.ArrayList;
import freemap.data.Walkroute;
import freemap.data.WalkrouteSummary;
import freemap.datasource.FreemapDataset;

// Data which must be shared between activities.

public class Shared {

    public static FreemapDataset pois;
    public static ArrayList<WalkrouteSummary> walkroutes;
    public static SavedData savedData;
    public static Walkroute curWalkroute; // the currently selected walk route

    public static void initSavedDataInstance() {
        savedData = SavedData.getInstance();
    }

}
