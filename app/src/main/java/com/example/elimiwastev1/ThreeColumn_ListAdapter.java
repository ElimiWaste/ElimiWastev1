package com.example.elimiwastev1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.elimiwastev1.R;
import com.example.elimiwastev1.User;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;
    private ArrayList<User> users;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<User> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int posiiton, View convertView, ViewGroup parent){
        convertView = inflater.inflate(mViewResourceId, null);

        User user = users.get(posiiton);

        if (user != null){
            TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            TextView itemDate = (TextView) convertView.findViewById(R.id.itemDate);

            if(itemName != null){
                itemName.setText(user.getItemName());
            }

            if(itemDate != null){
                itemDate.setText((user.getDate()));
            }

        }
        return convertView;
    }

}
