package com.example.mm.homeActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.homeActivity.localDatabaseInteraction.getCourseMoreStatistic;
import com.example.mm.homeActivity.localDatabaseInteraction.getCourseStatistic;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MoreStatisticFragment extends Fragment {
    View view;
    TextView moreStatisticLessText;
    TextView moreStatisticStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_statistic, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        moreStatisticLessText = view.findViewById(R.id.MoreStatisticLessText);
        moreStatisticStatus = view.findViewById(R.id.moreStatisticStatus);
        moreStatisticLessText.setOnClickListener((Home) getActivity());

        Thread t = new Thread(new getCourseMoreStatistic(getContext(), this));
        t.start();
    }

    public void updateStatus(String status, int color){
        moreStatisticStatus.setText(status);
        moreStatisticStatus.setTextColor(getResources().getColor(color));
    }

    public void updateRecycleView(ArrayList<String> courses, ArrayList<Float> values, ArrayList<Integer> imagesTrends){
        RecyclerView recyclerView = view.findViewById(R.id.MoreStatisticRecycleView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(view.getContext(), courses, values, imagesTrends);
        recyclerView.setAdapter(recyclerViewAdapter);
        /* I need to use LinearLayout because doesn't exits any Manager for ConstraintLayout. */
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}