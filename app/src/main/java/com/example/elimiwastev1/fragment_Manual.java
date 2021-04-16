package com.example.elimiwastev1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_Manual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Manual extends Fragment {
    EditText itemName, itemDate;
    Button save;
    ListView groceryList;
    String itemName2, itemDate2;
    ArrayList<LvItem> arrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_Manual() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Manual.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_Manual newInstance(String param1, String param2) {
        fragment_Manual fragment = new fragment_Manual();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

   // Error fixed from: https://itqna.net/questions/35978/error-calling-getlayoutinflater-inside-fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        itemName = getView().findViewById(R.id.item);
        itemDate = getView().findViewById(R.id.date);
        save = getView().findViewById(R.id.save);
        groceryList = getView().findViewById(R.id.LW);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                itemName2 = itemName.getText().toString();
                itemDate2 = itemDate.getText().toString();

                LvItem lvItem = new LvItem();
                lvItem.setItemName(itemName2);
                lvItem.setItemName(itemDate2);
                arrayList.add(lvItem);


                ContactAdapter contactAdapter = new ContactAdapter(arrayList,fragment_Manual.this);
                groceryList.setAdapter(contactAdapter);






            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__manual, container, false);
    }
}