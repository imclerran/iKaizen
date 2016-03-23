package mclerrani.ikaizen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * a custom extension of the RecyclerView.Adapter class to display Kaizen objects in a RecyclerView
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class KaizenRecyclerAdapter extends RecyclerView.Adapter<KaizenRecyclerAdapter.KaizenViewHolder> {

    private ArrayList<Kaizen> kaizenList;

    /**
     * constructor
     *
     * @param kaizenList -- the list of Kaizen to display in the RecyclerView
     */
    public KaizenRecyclerAdapter(ArrayList<Kaizen> kaizenList) {
        this.kaizenList = kaizenList;
    }

    /**
     * called when a new object is added to the recycler. Inflates a view using the designated layout
     *
     * @param parent -- the containing view group
     * @param viewType -- used if displaying more view type in the recycler
     * @return a ViewHolder containing the newly inflated view
     */
    @Override
    public KaizenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View kaizenView = LayoutInflater.from(context).inflate(R.layout.kaizen_card_layout, parent, false);

        return new KaizenViewHolder(kaizenView);
    }

    /**
     * bind a ViewHolder to a Kaizen object and populate any data to the view
     *
     * @param holder -- the ViewHolder to use
     * @param position -- the position of the ViewHolder
     */
    @Override
    public void onBindViewHolder(KaizenViewHolder holder, int position) {
        final Kaizen k = kaizenList.get(position);
        holder.lblOwnerData.setText(" " + k.getOwner());
        holder.lblDateData.setText(" " + k.getDateCreatedAsString());
        holder.lblProblemStatementData.setText(k.toString());
        holder.lblTotalWaste.setText(String.valueOf(k.getTotalWaste()));

        holder.itemView.setLongClickable(true);
    }

    /**
     * get the number of Kaizen objects in the adapter
     *
     * @return the number of kaizen
     */
    @Override
    public int getItemCount() {
        return kaizenList.size();
    }

    /**
     * Sort the kaizenList
     *
     * @param sortBy -- an integer indicating which value to use when comparing kaizen
     */
    public void sortKaizenList(int sortBy) {
        Collections.sort(kaizenList, new KaizenComparator(sortBy));
    }

    /**
     * Add a kaizen to the adapter
     *
     * @param k -- the kaizen to add
     * @return the added kaizen
     */
    public Kaizen add(Kaizen k) {
        kaizenList.add(k);
        return kaizenList.get(kaizenList.size() - 1);
    }

    /**
     * get the ArrayList of Kaizen
     *
     * @return the ArrayList of Kaizen
     */
    public ArrayList<Kaizen> getKaizenList() {
        return kaizenList;
    }

    /**
     * A ViewHolder class containing information about a single view item in the RecyclerView
     */
    public static class KaizenViewHolder extends RecyclerView.ViewHolder {
        protected TextView lblOwnerData;
        protected TextView lblDateData;
        protected TextView lblProblemStatementData;
        protected TextView lblTotalWaste;

        /**
         * constructor
         *
         * @param itemView -- an inflated view associated with one Kaizen item
         */
        public KaizenViewHolder(View itemView) {
            super(itemView);
            lblOwnerData                = (TextView) itemView.findViewById(R.id.lblOwnerData);
            lblDateData                 = (TextView) itemView.findViewById(R.id.lblDateData);
            lblProblemStatementData     = (TextView) itemView.findViewById(R.id.lblProblemStatementData);
            lblTotalWaste               = (TextView) itemView.findViewById(R.id.lblTotalWaste);

        }
    }
}
