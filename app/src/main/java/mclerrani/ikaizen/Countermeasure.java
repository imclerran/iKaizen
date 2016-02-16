package mclerrani.ikaizen;

import org.joda.time.DateTime;

/**
 * Created by Ian on 2/9/2016.
 */
public class Countermeasure {

    // meta data
    private static int count;
    private int itemID;
    private DateTime dateModified;
    private boolean deleteMe;

    // primary data
    private String preventativeAction;
    private boolean cutMuri;
    private boolean threeXBetter;
    private boolean truePull;
    private float costToImplement;
    private boolean implemented;
    private String dateWalkedOn;

    //---------------------------------------------------------------
    // Future iteration: allow ongoing problems.
    // New Countermeasures can be added, and previously implemented
    // Countermeasures can be phased out.
    // may require the use of multiple Solutions to track changes
    // such as new iterations of "today's fix"
    //---------------------------------------------------------------
    // private boolean phasedOut;

    public Countermeasure() {
        itemID = count++;
        deleteMe = false;

        preventativeAction = "";
        cutMuri = false;
        threeXBetter = false;
        truePull = false;
        costToImplement = 0.0f;
        implemented = false;
        dateWalkedOn = "";

        dateModified = DateTime.now();
    }

    public Countermeasure(String preventativeAction, boolean cutMuri, boolean threeXBetter, boolean truePull,
                          float costToImplement, boolean implemented, String dateWalkedOn) {
        this.deleteMe = false;
        this.preventativeAction = preventativeAction;
        this.cutMuri = cutMuri;
        this.threeXBetter = threeXBetter;
        this.truePull = truePull;
        this.costToImplement = costToImplement;
        this.implemented = implemented;
        this.dateWalkedOn = dateWalkedOn;

        itemID = count++;
        deleteMe = false;
        dateModified = DateTime.now();
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        updateDateModified();
        this.itemID = itemID;
    }

    public DateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(DateTime dateModified) {
        updateDateModified();
        this.dateModified = dateModified;
    }

    public boolean isDeleteMe() {
        return deleteMe;
    }

    public void setDeleteMe(boolean deleteMe) {
        updateDateModified();
        this.deleteMe = deleteMe;
    }

    public String getPreventativeAction() {
        return preventativeAction;
    }

    public void setPreventativeAction(String preventativeAction) {
        updateDateModified();
        this.preventativeAction = preventativeAction;
    }

    public boolean isCutMuri() {
        return cutMuri;
    }

    public void setCutMuri(boolean cutMuri) {
        updateDateModified();
        this.cutMuri = cutMuri;
    }

    public boolean isThreeXBetter() {
        return threeXBetter;
    }

    public void setThreeXBetter(boolean threeXBetter) {
        updateDateModified();
        this.threeXBetter = threeXBetter;
    }

    public boolean isTruePull() {
        return truePull;
    }

    public void setTruePull(boolean truePull) {
        updateDateModified();
        this.truePull = truePull;
    }

    public float getCostToImplement() {
        return costToImplement;
    }

    public void setCostToImplement(float costToImplement) {
        updateDateModified();
        this.costToImplement = costToImplement;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setImplemented(boolean implemented) {
        updateDateModified();
        this.implemented = implemented;
    }

    public String getDateWalkedOn() {
        return dateWalkedOn;
    }

    public void setDateWalkedOn(String dateWalkedOn) {
        updateDateModified();
        this.dateWalkedOn = dateWalkedOn;
    }

    public String getImprovements() {
        String improvements = "";
        if (cutMuri) {
            improvements += "cut muri";
        }
        if (threeXBetter) {
            if ("" != improvements)
                improvements += ", ";
            improvements += "3X better";
        }
        if (truePull) {
            if ("" != improvements)
                improvements += ", ";
            improvements += "true pull";
        }
        if ("" == improvements)
            improvements = "none";

        return improvements;
    }

    public void updateDateModified() {
        dateModified = DateTime.now();
    }

    @Override
    public String toString() {
        if (preventativeAction != null) {
            if ("" != preventativeAction)
                return preventativeAction;
        }
        return "No preventative action defined";
    }

    public static Countermeasure getTestCountermeasure() {
        Countermeasure cm = new Countermeasure("Doing this will eliminate a root cause of the problem.",
                false, false, false, 0.00f, false, null);
        return cm;
    }
}
