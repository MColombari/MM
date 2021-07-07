package com.example.mm.homeActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.R;

public class MoreStatisticFragment extends Fragment {
    TextView moreStatisticLessText;
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_more_statistic, container, false);
     }
     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         moreStatisticLessText = view.findViewById(R.id.MoreStatisticLessText);

         moreStatisticLessText.setOnClickListener((Home) getActivity());
     }
}