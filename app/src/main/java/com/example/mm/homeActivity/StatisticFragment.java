package com.example.mm.homeActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.R;

import localDatabase.TestApp;
import localDatabaseInteraction.getCourse;

/* https://www.anychart.com/technical-integrations/samples/android-charts/ */

public class StatisticFragment extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textView6);

        Thread t = new Thread(new getCourse(getContext(), this, textView));
        t.start();
    }
}