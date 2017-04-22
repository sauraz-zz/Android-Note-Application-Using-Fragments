package beer.com.mynotes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import beer.com.mynotes.Constants.Constants;
import beer.com.mynotes.Model.Note;
import beer.com.mynotes.R;
import beer.com.mynotes.database_helper.sqlite.DatabaseHelper;
import beer.com.mynotes.database_helper.sqlite.SharedPreferenceManager;

/**
 * Created by 1405473 on 18-04-2017.
 */

public class EditNoteFragment extends Fragment {
    SharedPreferenceManager spm;
    EditText editNote;
    Note note;
    DatabaseHelper sqlite_data_helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            // Get values if any values passed to fragment
            note = new Note();
            note = (Note) getArguments().getSerializable(Constants.ADD_NOTE_FRAGMENT);
        }
        return inflater.inflate(R.layout.edit_note_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        sqlite_data_helper = new DatabaseHelper(getContext());
        spm = new SharedPreferenceManager(getActivity());
        View rootView = getView();
        if (rootView != null) {
            editNote = (EditText) rootView.findViewById(R.id.edit_note);
            //  Request Focus for edit text
            editNote.requestFocus();
            //  Open keyboard
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editNote, InputMethodManager.SHOW_IMPLICIT);
            if (note != null) {
                editNote.setText(note.getText());
                //  Set cursor to end of edit text
                editNote.setSelection(editNote.getText().length());
            }
        }
    }

    // Saving the data passed to Fragment
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.ADD_NOTE_FRAGMENT, note);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Update database with new changes in note
        sqlite_data_helper.updateNote(editNote.getText().toString().trim(), note.getId());
    }
}