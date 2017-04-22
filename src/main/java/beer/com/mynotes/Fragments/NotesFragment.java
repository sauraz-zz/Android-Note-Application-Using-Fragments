package beer.com.mynotes.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import beer.com.mynotes.Constants.Constants;
import beer.com.mynotes.Model.Note;
import beer.com.mynotes.NoteAdapter;
import beer.com.mynotes.R;
import beer.com.mynotes.database_helper.sqlite.DatabaseHelper;
import beer.com.mynotes.database_helper.sqlite.SharedPreferenceManager;

/**
 * Created by 1405473 on 18-04-2017.
 */

public class NotesFragment extends Fragment {
    SharedPreferenceManager spm;
    ListView listView;
    FloatingActionButton addNote;
    DatabaseHelper sqlite_data_helper;
    ArrayList<Note> noteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        sqlite_data_helper = new DatabaseHelper(getContext());
        spm = new SharedPreferenceManager(getActivity());
        View rootView = getView();
        if (rootView != null) {
            addNote = (FloatingActionButton) rootView.findViewById(R.id.add_note);
            listView = (ListView) rootView.findViewById(R.id.listView);
            // Get all notes from database in ArrayList
            noteList = sqlite_data_helper.getNotes();
            NoteAdapter customAdapter = new NoteAdapter(noteList, getContext(), this);
            listView.setAdapter(customAdapter);

            addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create new Note
                    long id = sqlite_data_helper.addNote();
                    Note note = new Note();
                    note.setId(id);
                    noteList.add(note);
                    // Open Edit Note Fragment and pass note to fragment
                    itemClicked(note);
                }
            });
        }
    }

    // Open Edit Note Fragment and pass note to fragment
    public void itemClicked(Note note) {
        EditNoteFragment singleNoteFragment = new EditNoteFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ADD_NOTE_FRAGMENT, note);
        singleNoteFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, singleNoteFragment, Constants.ADD_NOTE_FRAGMENT);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}