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
 * Created by Ian on 2/11/2016.
 */
public class CountermeasureRecyclerAdapter extends RecyclerView.Adapter<CountermeasureRecyclerAdapter.CountermeasureViewHolder> {

    // member vars
    private ArrayList<Countermeasure> countermeasureList;

    // Constructor
    public CountermeasureRecyclerAdapter(ArrayList<Countermeasure> countermeasureList) {
        this.countermeasureList = countermeasureList;
    }

    @Override
    public CountermeasureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View countermeasureView = LayoutInflater.from(context).inflate(R.layout.countermeasure_card_layout, parent, false);

        return new CountermeasureViewHolder(countermeasureView);
    }

    @Override
    public void onBindViewHolder(CountermeasureViewHolder holder, int position) {
        Countermeasure cm = countermeasureList.get(position);
        /*if(null != cm.getDateWalkedOn()) {
            if("" != cm.getDateWalkedOn()) {
                holder.crdCountermeasure.setCardBackgroundColor(R.color.cardview_light_background);
            }
            else {
                holder.crdCountermeasure.setCardBackgroundColor(R.color.colorCardDissabled);
            }
        }
        else {
            holder.crdCountermeasure.setCardBackgroundColor(R.color.colorCardDissabled);
        }*/
        holder.lblPreventativeActionData.setText(cm.toString());
        String improvements = cm.getImprovements();
        holder.lblImprovementsData.setText(improvements);
        String cost = "$";
        cost += String.format("%.2f", cm.getCostToImplement());
        holder.lblCostToImplementData.setText(cost);
        holder.lblDateWalkedOnData.setText(cm.getDateWalkedOn());
    }

    @Override
    public int getItemCount() {
        return countermeasureList.size();
    }

    public ArrayList<Countermeasure> getCountermeasureList() {
        return countermeasureList;
    }

    public Countermeasure add(Countermeasure cm) {
        countermeasureList.add(cm);
        return countermeasureList.get(countermeasureList.size() - 1);
    }

    public void sortCountermeasureList(int sortBy) {
        Collections.sort(countermeasureList, new CountermeasureComparator(sortBy));
    }

    // ViewHolder class definition
    public static class CountermeasureViewHolder extends RecyclerView.ViewHolder
            /*implements View.OnClickListener, View.OnLongClickListener*/ {
        protected CardView crdCountermeasure;
        protected TextView lblPreventativeActionData;
        protected TextView lblImprovementsData;
        protected TextView lblCostToImplementData;
        protected TextView lblDateWalkedOnData;

        public CountermeasureViewHolder(View itemView) {
            super(itemView);
            crdCountermeasure           = (CardView) itemView.findViewById(R.id.crdCountermeasure);
            lblPreventativeActionData   = (TextView) itemView.findViewById(R.id.lblPreventativeActionData);
            lblImprovementsData         = (TextView) itemView.findViewById(R.id.lblImprovementsData);
            lblCostToImplementData      = (TextView) itemView.findViewById(R.id.lblCostToImplementData);
            lblDateWalkedOnData         = (TextView) itemView.findViewById(R.id.lblDateWalkedOnData);
        }

        /*@Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }*/
    }
}
