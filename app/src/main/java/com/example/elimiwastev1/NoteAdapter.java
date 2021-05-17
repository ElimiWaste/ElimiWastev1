package com.example.elimiwastev1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * NoteAdapter class extends ArrayAdapter for Note object, stores user entry items
 */
//Tutorial followed to create class: https://www.youtube.com/watch?v=4k1ZMpO9Zn0&t=884s
public class NoteAdapter extends ArrayAdapter<Note> {

    /**
     * Parameter constructor
     * Takes the following 2 inputs to create NoteAdapter object:
     * @param context the context of the NoteAdapter object stored as Context object
     * @param notes a List of Note objects
     */
    public NoteAdapter(Context context, List<Note> notes)
    {

        super(context, 0, notes);
    }

    /**
     * getView method to inflate view from note_cell resource file
     * @param position the position of the view, passed as an int to retrieve corresponding Note object at position
     * @param convertView assists in setting layout for NoteAdapter, take features from layout file
     * @param parent NonNull, ViewGroup object
     * @return convertView
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //Gets note at current position
        Note note = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);

        //initializes features from note_cell layout file
        TextView title = convertView.findViewById(R.id.cellTitle); // defines TextView object title as cellTitle
        TextView desc = convertView.findViewById(R.id.cellDesc); // defines TextView object desc as ceelDesc
        TextView expiry = convertView.findViewById(R.id.exDate);  // defines TextView object expiry as exDate
        TextView label1 = convertView.findViewById(R.id.textView); // defines TextView object label1 as textView
        TextView label2 = convertView.findViewById(R.id.textView2); // defines TextView object label2 as textView2

        //Setter methods to store user input in Note object
        title.setText(note.getTitle());
        desc.setText(note.getDescription());
        expiry.setText(note.getExpiration());

        return convertView;
    }

}
