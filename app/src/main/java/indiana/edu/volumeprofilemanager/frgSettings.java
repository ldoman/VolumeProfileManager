package indiana.edu.volumeprofilemanager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;


/**
 * This is the fragment that makes of the Settings tab on the main page of the app.
 */
public class frgSettings extends Fragment {

    // Fragment Listener
    private OnFragmentInteractionListener mListener;

    // Spinner vars
    Spinner spnBoot;
    Spinner spnAux;
    Spinner spnBlue;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public frgSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Fill the spinners with the current profiles, plus a "None" option
    public void populateSpinners()
    {
        if (spnBoot != null && spnAux != null && spnBlue != null)
        {
            List<String> spinnerArray =  Constants.getProfileStrings();
            spinnerArray.add(0, "None");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnBoot.setAdapter(adapter);
            spnAux.setAdapter(adapter);
            spnBlue.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Capture view
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        // Assign vars to view contents
        spnBoot = (Spinner)v.findViewById(R.id.spinnerBoot);
        spnAux = (Spinner)v.findViewById(R.id.spinner35mm);
        spnBlue = (Spinner)v.findViewById(R.id.spinnerBluetooth);

        // Fill spinners
        populateSpinners();

        // Inflate the layout for this fragment
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSettingsFragmentInteraction(uri);
        }
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

    public interface OnFragmentInteractionListener {
        public void onSettingsFragmentInteraction(Uri uri);
    }

}
