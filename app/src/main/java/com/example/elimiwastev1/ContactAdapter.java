package com.example.elimiwastev1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {


    ArrayList<LvItem> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public ContactAdapter(ArrayList<LvItem> arrayList, Context context) {

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i){
        return i;
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = layoutInflater.inflate(R.layout.lvitem, viewGroup, false);
        TextView txtname = view1.findViewById(R.id.item);
        TextView txtnumber = view1.findViewById(R.id.date);

        txtname.setText(arrayList.get(i).getItemName());
        txtnumber.setText(arrayList.get(i).getItemDate());


        return view1;
    }
}
