package mclerrani.ikaizen;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CountermeasureListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CountermeasureListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountermeasureListFragment extends Fragment {

    public final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    public final static String EXTRA_COUNTERMEASURE_POSITION = "mclerrani.ikaizen.COUNTERMEASURE_POSITION";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_KAIZEN_ID = "kaizenId";

    private int kaizenId;
    private int sortBy = CountermeasureComparator.COMPARE_DATE_MODIFIED;

    private DataManager dm;
    //private PreferencesManager pm;
    private Kaizen kaizen;
    //private Countermeasure cm;
    private ArrayList<Countermeasure> countermeasureList;
    private static CountermeasureRecyclerAdapter recAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ContextMenuRecyclerView recCountermeasureList;
    private StaggeredGridLayoutManager sglm;
    //private LinearLayoutManager llm;
    private Countermeasure toDelete;

    //private int mShortAnimationDuration;

    private OnFragmentInteractionListener mListener;

    public CountermeasureListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param kaizenId Parameter 1.
     * @return A new instance of fragment CountermeasureListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountermeasureListFragment newInstance(int kaizenId) {
        CountermeasureListFragment fragment = new CountermeasureListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KAIZEN_ID, kaizenId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dm = DataManager.getInstance(getContext());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kaizenId = getArguments().getInt(ARG_KAIZEN_ID);
            kaizen = dm.getKaizen(kaizenId);
        }
        
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_countermeasure_list, container, false);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        countermeasureList = kaizen.getSolution().getPossibleCounterMeasures();

        recCountermeasureList = (ContextMenuRecyclerView) view.findViewById(R.id.recCountermeasureList);
        recCountermeasureList.setHasFixedSize(false);
        int orientation = this.getResources().getConfiguration().orientation;
        if(Configuration.ORIENTATION_PORTRAIT == orientation) {
            sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }
        if(Configuration.ORIENTATION_LANDSCAPE == orientation) {
            sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        recCountermeasureList.setLayoutManager(sglm);
        //llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //recCountermeasureList.setLayoutManager(llm);

        recAdapter = new CountermeasureRecyclerAdapter(countermeasureList);
        recCountermeasureList.setAdapter(recAdapter);

        // TODO: add on touch listeners
        // Kaizen CardView touch event handler
        recCountermeasureList.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //cm = recAdapter.getCountermeasureList().get(position);
                        if (null != kaizen) {
                            editCountermeasure(position);
                        }
                    }
                })
        );

        sort();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        registerForContextMenu(recCountermeasureList);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_countermeasure_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        ContextMenuRecyclerView.RecyclerContextMenuInfo info
                = (ContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        //cm = recAdapter.getCountermeasureList().get(position);

        switch (id) {
            case R.id.action_edit_countermeasure:
                editCountermeasure(position);
                return true;
            case R.id.action_delete_countermeasure:
                // TODO: implement delete method
                //deleteCountermeasure(kaizen);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    public void deleteCountermeasure(Countermeasure cm) {
        cm.setDeleteMe(true);
        deleteCountermeasure();
    }

    /**
     * remove a Kaizen from kaizenList
     * give the user a chance to undo the delete with a snackbar action
     */
    public boolean deleteCountermeasure() {
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        // if there is a Kaizen object with deleteMe flag set
        // remove it from the kaizenList, keep it in a temp var
        for (int i = 0; i < countermeasureList.size(); i++) {
            if (countermeasureList.get(i).isDeleteMe()) {
                toDelete = countermeasureList.remove(i);
                recAdapter.notifyDataSetChanged();
                break;
            }
        }

        // if a kaizen has been flagged for deletion
        if (null != toDelete) {
            // notify user Kaizen has been deleted and prompt for undo
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Countermeasure deleted!", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // if user chooses to undo the delete operation
                            // unflag the Kaizen for deletion
                            toDelete.setDeleteMe(false);
                        }
                    });

            // when the delete/undo snackbar is dismissed
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    // check if kaizen is STILL flagged for deletion
                    if (null != toDelete) {
                        if (!toDelete.isDeleteMe()) {
                            // if not flagged for deletion, restore kaizen to list
                            recAdapter.add(toDelete);
                            recAdapter.sortCountermeasureList(sortBy);
                            recAdapter.notifyDataSetChanged();

                            // Notify user the kaizen has been restored
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Countermeasure restored!", Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                        else {
                            dm.deleteCountermeasure(toDelete, kaizen);
                        }
                    }
                    toDelete = null;
                }
            });
            snackbar.show();
        }

        return true;
    }

    public void editCountermeasure(int position) {
        Intent intent = new Intent(getContext(), CountermeasureEditActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizenId);
        intent.putExtra(EXTRA_COUNTERMEASURE_POSITION, position);
        startActivityForResult(intent, CountermeasureEditActivity.EDIT_COUNTERMEASURE_REQUEST);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.solution_details_group, false);
        menu.setGroupVisible(R.id.countermeasure_list_group, true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        sort();
        //recAdapter.notifyDataSetChanged();
    }

    public void setSortBy(int sortBy) { this.sortBy = sortBy; }

    public void sortBy(int sortBy) {
        this.sortBy = sortBy;
        sort();
    }

    private void sort() {
        recAdapter.sortCountermeasureList(this.sortBy);
        recAdapter.notifyDataSetChanged();
    }

    public static CountermeasureRecyclerAdapter getRecyclerAdapter() { return recAdapter; }
}
