package mclerrani.ikaizen;

import java.util.Comparator;

/**
 * Created by Ian on 2/14/2016.
 */
public class CountermeasureComparator implements Comparator<Countermeasure> {

    public static final int COMPARE_DATE_MODIFIED = 0;
    public static final int COMPARE_COST_ASCENDING = 1;
    public static final int COMPARE_COST_DESCENDING = 2;

    private int compareBy;

    public CountermeasureComparator(int compareBy) {
        super();
        this.compareBy = compareBy;
    }

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
