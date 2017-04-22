package beer.com.mynotes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import beer.com.mynotes.Fragments.NotesFragment;
import beer.com.mynotes.Model.Note;
import beer.com.mynotes.database_helper.sqlite.DatabaseHelper;

/**
 * Created by 1405473 on 18-04-2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    private ArrayList<Note> dataSet;
    private Context mContext;
    private DatabaseHelper sqlite_data_helper;
    private NotesFragment notesFragment;

    public NoteAdapter(ArrayList<Note> data, Context context, NotesFragment notesFragment) {
        super(context, R.layout.note_layout, data);
        this.dataSet = data;
        this.mContext = context;
        this.notesFragment = notesFragment;
        sqlite_data_helper = new DatabaseHelper(getContext());
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = dataSet.get(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.note_layout, null);
        }

        if (note != null) {
            TextView noteText = (TextView) view.findViewById(R.id.note_text);
            TextView noteDate = (TextView) view.findViewById(R.id.note_date);
            ImageView noteDelete = (ImageView) view.findViewById(R.id.note_delete);
            noteText.setText(note.getText());
            noteDate.setText(note.getDate());
            noteDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sqlite_data_helper.deleteNote(dataSet.get(position).getId());
                    dataSet.remove(position);
                    NoteAdapter.this.notifyDataSetChanged();
                }
            });
            noteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notesFragment.itemClicked(dataSet.get(position));
                }
            });
        }

        return view;
    }
}
