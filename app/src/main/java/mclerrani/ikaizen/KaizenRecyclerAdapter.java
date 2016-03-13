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
 * Created by Ian on 2/2/2016.
 */
public class KaizenRecyclerAdapter extends RecyclerView.Adapter<KaizenRecyclerAdapter.KaizenViewHolder> {

    // member vars
    private ArrayList<Kaizen> kaizenList;

    // constructor
    public KaizenRecyclerAdapter(ArrayList<Kaizen> kaizenList) {
        this.kaizenList = kaizenList;
    }

    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    @Override
    public KaizenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View kaizenView = LayoutInflater.from(context).inflate(R.layout.kaizen_card_layout, parent, false);

        return new KaizenViewHolder(kaizenView);
    }

    @Override
    public void onBindViewHolder(KaizenViewHolder holder, int position) {
        final Kaizen k = kaizenList.get(position);
        holder.lblOwnerData.setText(" " + k.getOwner());
        //holder.lblDeptData.setText(" " + k.getDept());
        holder.lblDateData.setText(" " + k.getDateCreatedAsString());
        holder.lblProblemStatementData.setText(k.toString());
        holder.lblTotalWaste.setText(String.valueOf(k.getTotalWaste()));

        holder.itemView.setLongClickable(true);
        holder.itemView.setClickable(true);
    }

    @Override
    public int getItemCount() {
        return kaizenList.size();
    }

    public void sortKaizenList(int sortBy) {
        Collections.sort(kaizenList, new KaizenComparator(sortBy));
    }

    public Kaizen add(Kaizen k) {
        kaizenList.add(k);
        return kaizenList.get(kaizenList.size() - 1);
    }

    public ArrayList<Kaizen> getKaizenList() {
        return kaizenList;
    }

    // ViewHolder class definition
    public static class KaizenViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        protected TextView lblOwnerData;
        protected TextView lblDeptData;
        protected TextView lblDateData;
        protected TextView lblProblemStatementData;
        protected TextView lblTotalWaste;
        //protected Button btnViewKaizenDetails;
        //protected Button btnEditKaizen;

        public KaizenViewHolder(View itemView) {
            super(itemView);
            lblOwnerData                = (TextView) itemView.findViewById(R.id.lblOwnerData);
            //lblDeptData                 = (TextView) view.findViewById(R.id.lblDeptData);
            lblDateData                 = (TextView) itemView.findViewById(R.id.lblDateData);
            lblProblemStatementData     = (TextView) itemView.findViewById(R.id.lblProblemStatementData);
            lblTotalWaste               = (TextView) itemView.findViewById(R.id.lblTotalWaste);
            //btnViewKaizenDetails    = (Button) itemView.findViewById(R.id.btnViewKaizenDetails);
            //btnEditKaizen           = (Button) itemView.findViewById(R.id.btnEditKaizen);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //Context context = v.getContext();
        }

        @Override
        public boolean onLongClick(View v) {

            return false;
        }
    }
}
