package mclerrani.ikaizen;

import android.util.Log;

import java.util.Comparator;

/**
 * comparator class for sorting Kaizen objects
 * @author Ian McLerran
 * @version 2/18/16
 */
public class KaizenComparator implements Comparator<Kaizen> {// may be it would be Model

    public static final int COMPARE_DATE_MODIFIED = 0;
    public static final int COMPARE_TOTAL_WASTE = 1;

    private int compareBy;

    /**
     * constructor
     * @param compareBy -- an integer indicating which value to compare the Kaizen by
     */
    public KaizenComparator(int compareBy) {
        super();
        this.compareBy = compareBy;
    }

    /**
     * compare two Kaizen objects
     *
     * @param lhs -- the left hand object to compare
     * @param rhs -- the right hand object to compare
     * @return an integer indicating the sort order of the objects
     */
    @Override
    public int compare(Kaizen lhs, Kaizen rhs) {
        switch(compareBy) {
            case COMPARE_DATE_MODIFIED:
                return rhs.getDateModified().compareTo(lhs.getDateModified());
            case COMPARE_TOTAL_WASTE:
                return lhs.getTotalWaste() > rhs.getTotalWaste() ? -1 : lhs.getTotalWaste() < rhs.getTotalWaste() ? 1 : 0;
            default:
                return lhs.getDateModified().compareTo(rhs.getDateModified());
        }
    }
}