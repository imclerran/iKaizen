package mclerrani.ikaizen;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ian on 2/6/2016.
 */
public class Solution {

    // Meta data:
    private static int count;
    private int itemID;
    private DateTime dateModified;
    private boolean deleteMe;

    // Primary data:
    private String todaysFix;
    private String preventativeAction;
    private String chosenCounterMeasures;
    private boolean cutMuri;
    private boolean threeXBetter;
    private boolean truePull;
    private int estimatedSavings;
    private DateTime walkedOnDate;

    // TODO: implement signedOffBy and solvedEmote at a later date
    // private string signedOffBy;
    // private string solvedEmote;

    public Solution() {
        deleteMe = false;
        itemID = count++;
        dateModified = DateTime.now();
    }

    public Solution(String todaysFix, String preventativeAction,
                    String chosenCounterMeasures, boolean cutMuri,
                    boolean threeXBetter, boolean truePull,
                    int estimatedSavings, DateTime walkedOnDate)
    {
        this.todaysFix = todaysFix;
        this.preventativeAction = preventativeAction;
        this.chosenCounterMeasures = chosenCounterMeasures;
        this.cutMuri = cutMuri;
        this.threeXBetter = threeXBetter;
        this.truePull = truePull;
        this.estimatedSavings = estimatedSavings;
        this.walkedOnDate = walkedOnDate;

        deleteMe = false;
        itemID = count++;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        dateModified = DateTime.now();
        this.itemID = itemID;
    }

    public boolean isDeleteMe() {
        return deleteMe;
    }

    public void setDeleteMe(boolean deleteMe) {
        dateModified = DateTime.now();
        this.deleteMe = deleteMe;
    }

    public String getTodaysFix() {
        return todaysFix;
    }

    public void setTodaysFix(String todaysFix) {
        dateModified = DateTime.now();
        this.todaysFix = todaysFix;
    }

    public String getPreventativeAction() {
        return preventativeAction;
    }

    public void setPreventativeAction(String preventativeAction) {
        dateModified = DateTime.now();
        this.preventativeAction = preventativeAction;
    }

    public String getChosenCounterMeasures() {
        return chosenCounterMeasures;
    }

    public void setChosenCounterMeasures(String chosenCounterMeasures) {
        dateModified = DateTime.now();
        this.chosenCounterMeasures = chosenCounterMeasures;
    }

    public boolean isCutMuri() {
        return cutMuri;
    }

    public void setCutMuri(boolean cutMuri) {
        dateModified = DateTime.now();
        this.cutMuri = cutMuri;
    }

    public boolean isThreeXBetter() {
        return threeXBetter;
    }

    public void setThreeXBetter(boolean threeXBetter) {
        dateModified = DateTime.now();
        this.threeXBetter = threeXBetter;
    }

    public boolean isTruePull() {
        return truePull;
    }

    public void setTruePull(boolean truePull) {
        dateModified = DateTime.now();
        this.truePull = truePull;
    }

    public int getEstimatedSavings() {
        return estimatedSavings;
    }

    public void setEstimatedSavings(int estimatedSavings) {
        dateModified = DateTime.now();
        this.estimatedSavings = estimatedSavings;
    }

    public DateTime getWalkedOnDate() {
        return walkedOnDate;
    }

    public void setWalkedOnDate(DateTime walkedOnDate) {
        dateModified = DateTime.now();
        this.walkedOnDate = walkedOnDate;
    }

    public String getWalkedOnDateAsString() {
        return walkedOnDate.toString("MM/dd/yyyy");
    }

    public DateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        if (itemID != solution.itemID) return false;
        if (cutMuri != solution.cutMuri) return false;
        if (threeXBetter != solution.threeXBetter) return false;
        if (truePull != solution.truePull) return false;
        if (estimatedSavings != solution.estimatedSavings) return false;
        if (dateModified != null ? !dateModified.equals(solution.dateModified) : solution.dateModified != null)
            return false;
        if (todaysFix != null ? !todaysFix.equals(solution.todaysFix) : solution.todaysFix != null)
            return false;
        if (preventativeAction != null ? !preventativeAction.equals(solution.preventativeAction) : solution.preventativeAction != null)
            return false;
        if (chosenCounterMeasures != null ? !chosenCounterMeasures.equals(solution.chosenCounterMeasures) : solution.chosenCounterMeasures != null)
            return false;
        return !(walkedOnDate != null ? !walkedOnDate.equals(solution.walkedOnDate) : solution.walkedOnDate != null);

    }

    @Override
    public int hashCode() {
        int result = itemID;
        result = 31 * result + (dateModified != null ? dateModified.hashCode() : 0);
        result = 31 * result + (todaysFix != null ? todaysFix.hashCode() : 0);
        result = 31 * result + (preventativeAction != null ? preventativeAction.hashCode() : 0);
        result = 31 * result + (chosenCounterMeasures != null ? chosenCounterMeasures.hashCode() : 0);
        result = 31 * result + (cutMuri ? 1 : 0);
        result = 31 * result + (threeXBetter ? 1 : 0);
        result = 31 * result + (truePull ? 1 : 0);
        result = 31 * result + estimatedSavings;
        result = 31 * result + (walkedOnDate != null ? walkedOnDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "preventativeAction='" + preventativeAction + '\'' +
                '}';
    }
}