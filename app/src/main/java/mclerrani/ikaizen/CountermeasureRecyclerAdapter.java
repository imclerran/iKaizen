package mclerrani.ikaizen;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * a custom extension of the RecyclerView.Adapter class to display Countermeasure objects in a RecyclerView
 *
 * @author Ian McLerran
 * @version 2/16/16
 */
public class CountermeasureRecyclerAdapter extends RecyclerView.Adapter<CountermeasureRecyclerAdapter.CountermeasureViewHolder> {

    // member vars
    private ArrayList<Countermeasure> countermeasureList;

    /**
     * Constructor
     *
     * @param countermeasureList -- the list of countermeasures to use with the recycler
     */
    public CountermeasureRecyclerAdapter(ArrayList<Countermeasure> countermeasureList) {
        this.countermeasureList = countermeasureList;
    }

    /**
     * called when a new object is added to the recycler. Inflates a view using the designated layout
     *
     * @param parent -- the containing view group
     * @param viewType -- used if displaying more view type in the recycler
     * @return a viewholder containing the newly inflated view
     */
    @Override
    public CountermeasureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View countermeasureView = LayoutInflater.from(context).inflate(R.layout.countermeasure_card_layout, parent, false);

        return new CountermeasureViewHolder(countermeasureView);
    }

    /**
     * bind a viewholder to a Countermeasure object and populate any data to the view
     *
     * @param holder -- the viewholder to use
     * @param position -- the position of the viewholder
     */
    @Override
    public void onBindViewHolder(CountermeasureViewHolder holder, int position) {
        Countermeasure cm = countermeasureList.get(position);
        holder.lblPreventativeActionData.setText(cm.toString());
        String improvements = cm.getImprovements();
        holder.lblImprovementsData.setText(improvements);
        String cost = "$";
        cost += String.format("%.2f", cm.getCostToImplement());
        holder.lblCostToImplementData.setText(cost);
        holder.lblDateWalkedOnData.setText(cm.getDateWalkedOn());

        holder.itemView.setLongClickable(true);
    }

    /**
     * get the number of Countermeasure items stored in the adapter
     *
     * @return the number of countermeasures
     */
    @Override
    public int getItemCount() {
        return countermeasureList.size();
    }

    /**
     * get the ArrayList of Countermeasure objects
     *
     * @return the countermeasureList
     */
    public ArrayList<Countermeasure> getCountermeasureList() {
        return countermeasureList;
    }

    /**
     * Add a Countermeasure object to the Adapter ArrayList
     *
     * @param cm -- the Countermeasure to add
     * @return the added Countermeasure
     */
    public Countermeasure add(Countermeasure cm) {
        countermeasureList.add(cm);
        return countermeasureList.get(countermeasureList.size() - 1);
    }

    /**
     * sort the Countermeasure list
     *
     * @param sortBy -- an integer indicating what value to use to sort the Countermeasure list
     */
    public void sortCountermeasureList(int sortBy) {
        Collections.sort(countermeasureList, new CountermeasureComparator(sortBy));
    }

    // ViewHolder class definition

    /**
     * A ViewHolder class containing information about a single view item in the RecyclerView
     */
    public static class CountermeasureViewHolder extends RecyclerView.ViewHolder {
        protected CardView crdCountermeasure;
        protected TextView lblPreventativeActionData;
        protected TextView lblImprovementsData;
        protected TextView lblCostToImplementData;
        protected TextView lblDateWalkedOnData;

        /**
         * Constructor
         *
         * @param itemView -- an inflated view associated with one Countermeasure item
         */
        public CountermeasureViewHolder(View itemView) {
            super(itemView);
            crdCountermeasure           = (CardView) itemView.findViewById(R.id.crdCountermeasure);
            lblPreventativeActionData   = (TextView) itemView.findViewById(R.id.lblPreventativeActionData);
            lblImprovementsData         = (TextView) itemView.findViewById(R.id.lblImprovementsData);
            lblCostToImplementData      = (TextView) itemView.findViewById(R.id.lblCostToImplementData);
            lblDateWalkedOnData         = (TextView) itemView.findViewById(R.id.lblDateWalkedOnData);
        }

    }
}
