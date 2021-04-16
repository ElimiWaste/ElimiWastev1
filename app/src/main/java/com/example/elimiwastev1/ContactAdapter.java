package com.example.elimiwastev1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ContactAdapter extends BaseAdapter {


    ArrayList<LvItem> arrayList;
    fragment_Manual context;
    LayoutInflater layoutInflater;

    public ContactAdapter(ArrayList<LvItem> arrayList, fragment_Manual context) {

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context.getActivity());
    }

    private fragment_Manual getContext(fragment_Manual context) {
        return context;
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
       // View view1 = layoutInflater.inflate(R.layout.lvitem, viewGroup, false);
        View view1 = layoutInflater.inflate(R.layout.lvitem, null);

        TextView txtname = view1.findViewById(R.id.item);
        TextView txtnumber = view1.findViewById(R.id.date);

        txtname.setText(arrayList.get(i).getItemName());
        txtnumber.setText(arrayList.get(i).getItemDate());


        return view1;
    }
}
