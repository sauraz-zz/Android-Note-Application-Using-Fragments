package beer.com.mynotes.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import beer.com.mynotes.Constants.Constants;
import beer.com.mynotes.R;
import beer.com.mynotes.database_helper.sqlite.SharedPreferenceManager;

/**
 * Created by 1405473 on 18-04-2017.
 */

public class LoginFragment extends Fragment {

    AutoCompleteTextView userEmail;
    EditText userPassword;
    Button signInBtn;
    SharedPreferenceManager spm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        spm = new SharedPreferenceManager(getActivity());
        View rootView = getView();
        if (rootView != null) {
            userEmail = (AutoCompleteTextView) rootView.findViewById(R.id.email);
            userPassword = (EditText) rootView.findViewById(R.id.password);
            signInBtn = (Button) rootView.findViewById(R.id.email_sign_in_button);
            // SignIn button click listener
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = userEmail.getText().toString().trim();
                    String pass = userPassword.getText().toString().trim();

                    // Check if null values are passed when trying to login
                    if (!email.equals("") && !pass.equals("")) {
                        //  If values are not null create login session and save to shared preference
                        spm.createLoginSession(email);
                        //  open Notes Fragment after login
                        openNotes();
                    }
                }
            });
        }
    }

    public void openNotes() {
        NotesFragment noteFragment = new NotesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, noteFragment, Constants.NOTES_FRAGMENT);
        transaction.commit();
    }
}