package mclerrani.ikaizen;

import org.joda.time.DateTime;

/**
 * data class for tracking countermeasures for a given kaizen
 *
 * @author Ian McLerran
 * @version 3/12/2016
 */
public class Countermeasure {

    // meta data:
    private static int count;
    private int itemID;
    private DateTime dateModified;
    private boolean deleteMe;

    // primary data:
    private String preventativeAction;
    private boolean cutMuri;
    private boolean threeXBetter;
    private boolean truePull;
    private float costToImplement;
    private boolean implemented;
    private String dateWalkedOn;

    //---------------------------------------------------------------
    // Future version: allow ongoing problems.
    // New Countermeasures can be added, and previously implemented
    // Countermeasures can be phased out.
    // may require the use of multiple Solutions to track changes
    // such as new iterations of "today's fix"
    //---------------------------------------------------------------
    // private boolean phasedOut;


    /**
     * default constructor
     */
    public Countermeasure() {
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

    /**
     * constructor
     *
     * @param dateModified -- the last time the counermeasure was modified
     * @param preventativeAction -- how to prevent the problem in future
     * @param cutMuri -- does it reduce burden?
     * @param threeXBetter -- three times more efficient?
     * @param truePull -- implement true pull?
     * @param costToImplement -- how much will it cost to implement
     * @param implemented -- has it been implemented?
     * @param dateWalkedOn -- the date the countermeasure was tested
     */
    public Countermeasure(DateTime dateModified, String preventativeAction, boolean cutMuri, boolean threeXBetter, boolean truePull,
                          float costToImplement, boolean implemented, String dateWalkedOn) {
        this.deleteMe = false;
        this.preventativeAction = preventativeAction;
        this.cutMuri = cutMuri;
        this.threeXBetter = threeXBetter;
        this.truePull = truePull;
        this.costToImplement = costToImplement;
        this.implemented = implemented;
        this.dateWalkedOn = dateWalkedOn;

        deleteMe = false;

        if(null != dateModified)
            this.dateModified = dateModified;
        else
            this.dateModified = DateTime.now();
    }

    // getters and setters:

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public DateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isDeleteMe() {
        return deleteMe;
    }

    public void setDeleteMe(boolean deleteMe) {
        this.deleteMe = deleteMe;
    }

    public String getPreventativeAction() {
        return preventativeAction;
    }

    public void setPreventativeAction(String preventativeAction) {
        this.preventativeAction = preventativeAction;
    }

    public boolean isCutMuri() {
        return cutMuri;
    }

    public void setCutMuri(boolean cutMuri) {
        this.cutMuri = cutMuri;
    }

    public boolean isThreeXBetter() {
        return threeXBetter;
    }

    public void setThreeXBetter(boolean threeXBetter) {
        this.threeXBetter = threeXBetter;
    }

    public boolean isTruePull() {
        return truePull;
    }

    public void setTruePull(boolean truePull) {
        this.truePull = truePull;
    }

    public float getCostToImplement() {
        return costToImplement;
    }

    public void setCostToImplement(float costToImplement) {
        this.costToImplement = costToImplement;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }

    public String getDateWalkedOn() {
        return dateWalkedOn;
    }

    public void setDateWalkedOn(String dateWalkedOn) {
        this.dateWalkedOn = dateWalkedOn;
    }

    /**
     * get a string containing the improvements attained by countermeasure
     * eg: "cut muri", "3X better", and "true pull"
     *
     * @return a string containing the implemented improvements
     */
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

    /**
     * update the date modified to the current time
     */
    public void updateDateModified() {
        dateModified = DateTime.now();
    }

    /**
     * create a string representation of the object
     *
     * @return the preventative action
     */
    @Override
    public String toString() {
        if (preventativeAction != null) {
            if ("" != preventativeAction)
                return preventativeAction;
        }
        return "No preventative action defined";
    }

    /**
     * create a new Countermeaure object with example values
     *
     * @return the new Countermeasure
     */
    public static Countermeasure getTestCountermeasure() {
        Countermeasure cm = new Countermeasure(null, "Doing this will eliminate a root cause of the problem.",
                false, false, false, 0.00f, false, null);
        return cm;
    }
}
