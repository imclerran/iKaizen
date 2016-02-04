package mclerrani.ikaizen;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Ian on 2/4/2016.
 */
public class KaizenComparator implements Comparator<Kaizen> {// may be it would be Model

    public static final int COMPARE_DATE_MODIFIED = 0;
    public static final int COMPARE_TOTAL_WASTE = 1;

    private int compareBy;

    public KaizenComparator(int compareBy) {
        super();
        this.compareBy = compareBy;
    }

    @Override
    public int compare(Kaizen first, Kaizen second) {
        switch(compareBy) {
            case COMPARE_DATE_MODIFIED:
                long firstTime = first.getTimeModified();
                long secondTime = second.getTimeModified();
                return firstTime > secondTime ? -1 : firstTime < secondTime ? 1 : 0;
            case COMPARE_TOTAL_WASTE:
                return first.getTotalWaste() > second.getTotalWaste() ? -1 : first.getTotalWaste() < second.getTotalWaste() ? 1 : 0;
            default:
                return first.getDateModified().compareTo(second.getDateModified());
        }
    }
}