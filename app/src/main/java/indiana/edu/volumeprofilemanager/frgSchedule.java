package indiana.edu.volumeprofilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class frgSchedule extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;

    //The fragment's ListView/GridView
    private AbsListView mListView;

    //The Adapter which will be used to populate the ListView/GridView with Views
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public frgSchedule() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Refresh displayed schedules list
        refreshList();
    }

    // Get list of all schedules and update adapter to use new data source
    public void refreshList()
    {
        ArrayList<String> schedules = Constants.getScheduleStrings();

        // Set list adapter data source to new arraylist
        mAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_list_2, android.R.id.text1, schedules);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Open activity for that day
            Intent myIntent = new Intent();
            myIntent.setClassName("indiana.edu.volumeprofilemanager", "indiana.edu.volumeprofilemanager.actAddRule");
            myIntent.putExtra("day", Constants.getSchedules().get(position).getName());
            startActivity(myIntent);
        }
    }

    // Need this for ListView
    public interface OnFragmentInteractionListener {
        public void onScheduleFragmentInteraction(String id);
    }

}
