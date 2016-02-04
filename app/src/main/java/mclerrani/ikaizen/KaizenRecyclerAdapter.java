package mclerrani.ikaizen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ian on 2/2/2016.
 */
public class KaizenRecyclerAdapter extends RecyclerView.Adapter<KaizenRecyclerAdapter.KaizenViewHolder> {

    private ArrayList<Kaizen> kaizenList;

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

        holder.itemView.setLongClickable(true);
        holder.itemView.setClickable(true);

        /*holder.btnEditKaizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // button click event
                }
            }
        });

        holder.btnViewKaizenDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// button click event
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return kaizenList.size();
    }

    public void sortKaizenList(int sortBy) {
        Collections.sort(kaizenList, new KaizenComparator(sortBy));
    }

    // ViewHolder class definition
    public static class KaizenViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        protected TextView lblOwnerData;
        protected TextView lblDeptData;
        protected TextView lblDateData;
        protected TextView lblProblemStatementData;
        //protected Button btnViewKaizenDetails;
        //protected Button btnEditKaizen;

        public KaizenViewHolder(View view) {
            super(view);
            lblOwnerData                = (TextView) view.findViewById(R.id.lblOwnerData);
            //lblDeptData                 = (TextView) view.findViewById(R.id.lblDeptData);
            lblDateData                 = (TextView) view.findViewById(R.id.lblDateData);
            lblProblemStatementData     = (TextView) view.findViewById(R.id.lblProblemStatementData);
            //btnViewKaizenDetails    = (Button) view.findViewById(R.id.btnViewKaizenDetails);
            //btnEditKaizen           = (Button) view.findViewById(R.id.btnEditKaizen);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

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


    public Kaizen add(Kaizen k) {
        kaizenList.add(k);
        return kaizenList.get(kaizenList.size()-1);
    }

    public ArrayList<Kaizen> getKaizenList() {
        return kaizenList;
    }
}
