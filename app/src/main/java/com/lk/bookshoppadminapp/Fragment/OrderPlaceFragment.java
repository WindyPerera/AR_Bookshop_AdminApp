package com.lk.bookshoppadminapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.bookshoppadminapp.R;


public class OrderPlaceFragment extends Fragment {


    private View view;

    public OrderPlaceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_order_place, container, false);


        return view;
    }
}