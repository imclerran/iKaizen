package mclerrani.ikaizen;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ian on 2/6/2016.
 */
public class Solution {

    // Meta data:
    private static int count;
    private int itemID;

    // Primary data:
    private String todaysFix;
    private ArrayList<Countermeasure> possibleCountermeasures;
    private ArrayList<Countermeasure> chosenCountermeasures;
    private boolean cutMuri;
    private boolean threeXBetter;
    private boolean truePull;
    private float estimatedSavings;
    private DateTime dateSolved;
    private DateTime dateSolutionUpdated;

    // TODO: implement signedOffBy and solvedEmote at a later date
    private String signedOffBy;
    private int solvedEmote;

    public Solution() {
        itemID = count++;

        cutMuri = false;
        threeXBetter = false;
        truePull = false;
        possibleCountermeasures = new ArrayList<>();
        chosenCountermeasures = new ArrayList<>();
        solvedEmote = R.drawable.ic_feels;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTodaysFix() {
        return todaysFix;
    }

    public void setTodaysFix(String todaysFix) {
        this.todaysFix = todaysFix;
    }

    public ArrayList<Countermeasure> getPossibleCounterMeasures() {
        return possibleCountermeasures;
    }

    public void setPossibleCounterMeasures(ArrayList<Countermeasure> cmList) {
        this.possibleCountermeasures = cmList;
    }

    public ArrayList<Countermeasure> getChosenCounterMeasures() {
        return chosenCountermeasures;
    }

    public void setChosenCounterMeasures(ArrayList<Countermeasure> cmList) {
        this.chosenCountermeasures = cmList;
    }

    public float getEstimatedSavings() {
        return estimatedSavings;
    }

    public void setEstimatedSavings(float estimatedSavings) {
        this.estimatedSavings = estimatedSavings;
    }

    public DateTime getDateSolved() {
        return dateSolved;
    }

    public void setDateSolved(DateTime dateSolved) {
        this.dateSolved = dateSolved;
    }

    public String getDateSolvedAsString() {
        if(null != dateSolved)
            return dateSolved.toString("MM/dd/yyyy");
        return "N/A";
    }

    public DateTime getDateSolutionUpdated() {
        return dateSolutionUpdated;
    }

    public void setDateSolutionUpdated(DateTime dateSolutionUpdated) {
        this.dateSolutionUpdated = dateSolutionUpdated;
    }

    public boolean isTruePull() {
        return truePull;
    }

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

    public String getImprovements() {
        int size = possibleCountermeasures.size();
        cutMuri = false;
        threeXBetter = false;
        truePull = false;
        for(int i = 0; i < size; i++) {
            Countermeasure cm = possibleCountermeasures.get(i);
            if(cm.isCutMuri())
                cutMuri = true;
            if(cm.isThreeXBetter())
                threeXBetter = true;
            if(cm.isTruePull())
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

    public ArrayList<Countermeasure> updateChosenCountermeasures() {
        int size = possibleCountermeasures.size();
        Countermeasure cm;

        for(int i = 0; i < size; i++) {
            cm = possibleCountermeasures.get(i);
            if(null != cm.getDateWalkedOn()) {
                if("" != cm.getDateWalkedOn()) {
                    if(!chosenCountermeasures.contains(cm))
                        chosenCountermeasures.add(cm);
                }
            }
        }

        size = chosenCountermeasures.size();
        for(int i = 0; i < size; i++) {
            cm = chosenCountermeasures.get(i);
            if(null == cm.getDateWalkedOn()) {
                if("" == cm.getDateWalkedOn()) {
                    chosenCountermeasures.remove(cm);
                }
            }
        }
        return chosenCountermeasures;
    }
}