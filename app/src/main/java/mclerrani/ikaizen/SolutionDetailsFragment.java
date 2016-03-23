package mclerrani.ikaizen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A fragment class for displaying solution details
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class SolutionDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_KAIZEN_ID = "kaizenId";

    // TODO: Rename and change types of parameters
    private int kaizenId;

    private OnFragmentInteractionListener mListener;

    private Kaizen kaizen;
    private Solution solution;
    private DataManager dm;
    private int mShortAnimationDuration;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param kaizenId -- ID used to fetch kaizen from DataManager
     * @return a new instance of fragment SolutionDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolutionDetailsFragment newInstance(int kaizenId) {
        SolutionDetailsFragment fragment = new SolutionDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KAIZEN_ID, kaizenId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * default constructor
     */
    public SolutionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        dm = DataManager.getInstance(getContext());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kaizenId = getArguments().getInt(ARG_KAIZEN_ID);
            kaizen = dm.getKaizen(kaizenId);

            solution = kaizen.getSolution();
            if(null == solution) {
                solution = new Solution();
                dm.insertSolution(solution, kaizen);
                dm.insertCountermeasure(Countermeasure.getTestCountermeasure(), kaizen);
                kaizen.setSolution(solution);
            }
        }

        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    /**
     * Android lifecycle onCreateView() method
     *
     * @param inflater -- the LayoutInflater
     * @param container -- the ViewGroup which contains the fragment
     * @param savedInstanceState -- the saved application state
     * @return the inflated fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solution_details, container, false);
        populate(view);
        return view;
    }

    /**
     * populate the fragment layout with Solution data
     *
     * @param view
     */
    public void populate(View view) {
        TextView txtTodaysFix           = (TextView) view.findViewById(R.id.lblTodaysFix);
        txtTodaysFix.setText(solution.getTodaysFix());
        TextView txtDateSolved          = (TextView) view.findViewById(R.id.lblDateSolved);
        txtDateSolved.setText(solution.getDateSolvedAsString());
        TextView txtSignedOffBy         = (TextView) view.findViewById(R.id.lblSignedOffBy);
        txtSignedOffBy.setText(solution.getSignedOffBy());
        TextView txtEstimateSavings     = (TextView) view.findViewById(R.id.lblEstimatedSavings);
        String savings = String.format("%.2f", solution.getEstimatedSavings());
        TextView lblImprovementsData    = (TextView) view.findViewById(R.id.lblImprovementsData);
        lblImprovementsData.setText(solution.getImprovements());
        ImageButton btnFeelsEmoji       = (ImageButton) view.findViewById(R.id.btnFeelsEmoji);
        btnFeelsEmoji.setImageResource(solution.getSolvedEmote());
    }

    /**
     * Android lifecycle onAttach() method
     *
     * @param context -- the context in which the fragment has been attached
     */
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

    /**
     * Android lifecycle onDetach() method
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Android lifecycle onResume() methods
     */
    @Override
    public void onResume() {
        super.onResume();
        populate(getView());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    /**
     * Android lifecycle onPrepareOptionsMenu() method
     * set the visibility of the menu items relevant to this fragment
     *
     * @param menu -- the options menu used by the activity
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.solution_details_group, true);
        menu.setGroupVisible(R.id.countermeasure_list_group, false);
        super.onPrepareOptionsMenu(menu);
    }
}
