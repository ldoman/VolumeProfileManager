package indiana.edu.volumeprofilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * This is the fragment that makes of the Profiles tab on the main page of the app.
 *
 */
public class frgProfiles extends Fragment implements AbsListView.OnItemClickListener,
        AbsListView.OnItemLongClickListener,
        Button.OnClickListener  {

    private OnFragmentInteractionListener mListener;

     //The fragment's ListView/GridView
    private AbsListView mListView;

     //The Adapter which will be used to populate the ListView/GridView with Views
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public frgProfiles() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Refresh displayed profile list
        refreshList();
    }

    // Get list of all profiles and update adapter to use new data source
    public void refreshList()
    {
        ArrayList<String> profileStrings = Constants.getProfileStrings();

        // Set list adapter data source to new arraylist
        mAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_list, android.R.id.text1, profileStrings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volumeprofile, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        // Set OnItemClickListener so we can be notified on item long clicks
        mListView.setOnItemLongClickListener(this);

        // Find add profile button and assign click listener
        Button btnSearch = (Button) view.findViewById(R.id.btnAddProfile);
        btnSearch.setOnClickListener(this);
        //btnSearch.getBackground().setColorFilter(0xB80D5CFF, PorterDuff.Mode.MULTIPLY);

        return view;
    }

    // Process Add Profile click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddProfile:
                Intent myIntent = new Intent();
                myIntent.setClassName("indiana.edu.volumeprofilemanager", "indiana.edu.volumeprofilemanager.actDefineProfile");
                startActivityForResult(myIntent, Constants.REQUEST_NEW_PROFILE);
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        refreshList();
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
        if (null != mListener)
        {
            // Enable clicked profile
            Constants.enableProfile(Constants.getProfiles().get(position));

            // Give toast message confirmation
            Toast.makeText(getActivity(), Constants.getProfiles().get(position).getName() + " enabled",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener)
        {
            // Vars that need to be accessed inside dialog builder
            final View view2 = view;
            final int pos = position;
            final Activity act = this.getActivity();

            // List of options for dialog
            CharSequence[] options = new String[2];
            options[0] = "Edit";
            options[1] = "Delete";

            // Make dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Profile options")
                    .setItems(options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which)
                            {
                                // User selects Edit
                                case 0:
                                    Intent myIntent = new Intent();
                                    myIntent.setClassName("indiana.edu.volumeprofilemanager",
                                            "indiana.edu.volumeprofilemanager.actDefineProfile");
                                    myIntent.putExtra("profile", Constants.getProfiles().get(pos).getName());
                                    startActivityForResult(myIntent, Constants.REQUEST_EDIT_PROFILE);
                                    break;
                                // User selects Delete
                                case 1:
                                    // Listener for deletion confirmation
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    Constants.deleteProfile(Constants.getProfiles().get(pos).getName());
                                                    refreshList();
                                                    ((MainActivity)act).switchTab(1);
                                                    ((MainActivity)act).switchTab(0);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // If they select no do nothing
                                                    break;
                                            }
                                        }
                                    };

                                    // Prompt user for confirmation about delete
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(view2.getContext());
                                    builder2.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                            .setNegativeButton("No", dialogClickListener).show();

                                    break;
                            }
                        }
                    });

            // Show profile options
            builder.show();
        }

        // Temporarily always return true TODO: check this functions result before clearing profiles cache
        return true;
    }

    // When returning from an activity refresh the profile list
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        refreshList();

        // This is the only way I could manage to trick the listview into refreshing
        ((MainActivity)getActivity()).switchTab(1);
        ((MainActivity)getActivity()).switchTab(0);
    }

    // Need this for ListView
    public interface OnFragmentInteractionListener {
        public void onProfileFragmentInteraction(String id);
    }
}
