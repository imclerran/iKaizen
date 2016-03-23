package mclerrani.ikaizen;

import java.util.Comparator;

/**
 * comparator for sorting Countermeasure objects
 *
 * @author Ian McLerran
 * @version 2/16/2016
 */
public class CountermeasureComparator implements Comparator<Countermeasure> {

    public static final int COMPARE_DATE_MODIFIED = 0;
    public static final int COMPARE_COST_ASCENDING = 1;
    public static final int COMPARE_COST_DESCENDING = 2;

    private int compareBy;

    /**
     * constructor
     *
     * @param compareBy and integer indicating the value to compare the countermeasures by
     */
    public CountermeasureComparator(int compareBy) {
        super();
        this.compareBy = compareBy;
    }

    /**
     * compare two Countermeasure objects
     *
     * @param lhs -- the left hand object to compare
     * @param rhs -- the right hand object to compare
     * @return an integer indicating the sort order of the objects
     */
    @Override
    public int compare(Countermeasure lhs, Countermeasure rhs) {
        float rhsCost;
        float lhsCost;
        switch (compareBy) {
            case COMPARE_DATE_MODIFIED:
                return rhs.getDateModified().compareTo(lhs.getDateModified());
            case COMPARE_COST_ASCENDING:
                lhsCost = lhs.getCostToImplement();
                rhsCost = rhs.getCostToImplement();
                return lhsCost < rhsCost ? -1 : lhsCost > rhsCost ? 1 : 0;
            case COMPARE_COST_DESCENDING:
                lhsCost = lhs.getCostToImplement();
                rhsCost = rhs.getCostToImplement();
                return lhsCost > rhsCost ? -1 : lhsCost < rhsCost ? 1 : 0;
            default:
        }
        return 0;
    }
}
