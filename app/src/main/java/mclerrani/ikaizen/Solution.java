package mclerrani.ikaizen;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * data class for storing solution information
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class Solution {

    // Meta data:
    private int itemID;

    // Primary data:
    private String todaysFix;
    private ArrayList<Countermeasure> possibleCountermeasures;
    private boolean cutMuri;
    private boolean threeXBetter;
    private boolean truePull;
    private float estimatedSavings;
    private DateTime dateSolved;
    private DateTime dateSolutionUpdated;
    private String signedOffBy;
    private int solvedEmote;

    /**
     * default constructor
     */
    public Solution() {
        cutMuri = false;
        threeXBetter = false;
        truePull = false;
        possibleCountermeasures = new ArrayList<>();
        dateSolutionUpdated = null;
        solvedEmote = R.drawable.ic_feels;
    }

    /**
     * constructor
     *
     * @param todaysFix -- the temporary solution
     * @param cutMuri -- lighten the burden?
     * @param threeXBetter -- 3 times more efficient?
     * @param truePull -- implemented true pull?
     * @param estimatedSavings -- monitary savings estimate
     * @param dateSolved -- the date the problem was solved
     * @param dateSolutionUpdated -- the date the solution was changed. eg: new countermeasure
     * @param signedOffBy -- lead/manager name
     * @param solvedEmote -- how does it feel?
     */
    public Solution(String todaysFix, boolean cutMuri, boolean threeXBetter, boolean truePull,
                    float estimatedSavings, DateTime dateSolved, DateTime dateSolutionUpdated,
                    String signedOffBy, int solvedEmote) {
        this.todaysFix = todaysFix;
        this.cutMuri = cutMuri;
        this.threeXBetter = threeXBetter;
        this.truePull = truePull;
        this.estimatedSavings = estimatedSavings;
        this.dateSolved = dateSolved;
        this.dateSolutionUpdated = dateSolutionUpdated;
        this.signedOffBy = signedOffBy;
        this.solvedEmote = solvedEmote;

        possibleCountermeasures = new ArrayList<>();
    }

    // getters and setters:

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public String getTodaysFix() { return todaysFix; }
    public void setTodaysFix(String todaysFix) { this.todaysFix = todaysFix; }

    public ArrayList<Countermeasure> getPossibleCounterMeasures() { return possibleCountermeasures; }
    public void setPossibleCounterMeasures(ArrayList<Countermeasure> cmList) { this.possibleCountermeasures = cmList; }

    public float getEstimatedSavings() { return estimatedSavings; }
    public void setEstimatedSavings(float estimatedSavings) { this.estimatedSavings = estimatedSavings; }

    public DateTime getDateSolved() { return dateSolved; }
    public void setDateSolved(DateTime dateSolved) {
        this.dateSolved = dateSolved;
    }



    public DateTime getDateSolutionUpdated() { return dateSolutionUpdated; }
    public void setDateSolutionUpdated(DateTime dateSolutionUpdated) { this.dateSolutionUpdated = dateSolutionUpdated; }

    public boolean isTruePull() { return truePull; }
    public void setTruePull(boolean truePull) {
        this.truePull = truePull;
    }

    public boolean isThreeXBetter() {
        return threeXBetter;
    }
    public void setThreeXBetter(boolean threeXBetter) {
        this.threeXBetter = threeXBetter;
    }

    public boolean isCutMuri() {
        return cutMuri;
    }
    public void setCutMuri(boolean cutMuri) {
        this.cutMuri = cutMuri;
    }

    public String getSignedOffBy() {
        return signedOffBy;
    }
    public void setSignedOffBy(String signedOffBy) {
        this.signedOffBy = signedOffBy;
    }

    public int getSolvedEmote() {
        return solvedEmote;
    }
    public void setSolvedEmote(int solvedEmote) {
        this.solvedEmote = solvedEmote;
    }

    /**
     * get a string representation of the date solved
     *
     * @return the date solved as a string
     */
    public String getDateSolvedAsString() {
        if (null != dateSolved)
            return dateSolved.toString("MM/dd/yyyy");
        return "N/A";
    }

    /**
     * check all improvements implemented across all countermeasures,
     * set their corresponding booleans in the solution,
     * then return a string showing which improvements have been fulfilled
     * 
     * @// TODO: 3/14/2016 change to only show improvements from implemented countermeasures
     *
     * @return the string representation of all implemented improvements
     */
    public String getImprovements() {
        int size = possibleCountermeasures.size();
        cutMuri = false;
        threeXBetter = false;
        truePull = false;
        for (int i = 0; i < size; i++) {
            Countermeasure cm = possibleCountermeasures.get(i);
            if (cm.isCutMuri())
                cutMuri = true;
            if (cm.isThreeXBetter())
                threeXBetter = true;
            if (cm.isTruePull())
                truePull = true;
        }

        String improvements = "";
        if (cutMuri) {
            improvements += "cut muri";
        }
        if (threeXBetter) {
            if ("" != improvements) {
                improvements += ", ";
            }
            improvements += "3X better";
        }
        if (truePull) {
            if ("" != improvements) {
                improvements += ", ";
            }
            improvements += "true pull";
        }
        if ("" == improvements)
            improvements = "none";

        return improvements;
    }
}