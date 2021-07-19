package com.example.mm.homeActivity.statisticFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.homeActivity.Home;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import com.example.mm.homeActivity.localDatabaseInteraction.getCourseStatistic;

/* https://www.anychart.com/technical-integrations/samples/android-charts/ */

public class StatisticFragment extends Fragment {
    TextView status;
    TextView statisticMoreText;
    RadarChart radarChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = view.findViewById(R.id.statisticStatus);
        radarChart = view.findViewById(R.id.StatisticGraph);
        statisticMoreText = view.findViewById(R.id.StatisticMoreText);

        statisticMoreText.setOnClickListener((Home) getActivity());

        Thread t = new Thread(new getCourseStatistic(getContext(), this));
        t.start();
    }

    public void updateStatus(String statusText, int color){
        status.setText(statusText);
        status.setTextColor(getResources().getColor(color));
    }

    public void updateGraph(ArrayList<String> courseName, ArrayList<Float> values){
        ArrayList<RadarEntry> radarEntries = new ArrayList<RadarEntry>();
        for(float f : values){
            radarEntries.add(new RadarEntry(f));
        }

        RadarData radarData;
        RadarDataSet radarDataSet;

        radarDataSet = new RadarDataSet(radarEntries, "");
        radarData = new RadarData(radarDataSet);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(courseName));

        radarDataSet.setColors(getResources().getColor(R.color.cyan));
        radarDataSet.setValueTextColor(Color.BLACK);
        radarDataSet.setValueTextSize(10f);
        radarDataSet.setHighlightCircleFillColor(R.color.cyan);
        radarDataSet.setLineWidth(3);

        /* Continua da qui: https://www.programmersought.com/article/95216335605/ */

        radarChart.getDescription().setEnabled(false);
        radarChart.getLegend().setEnabled(false);
        radarChart.setData(radarData);

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setAxisMaximum(100);
        yAxis.resetAxisMaximum();
        yAxis.setAxisMinimum(0);
        yAxis.resetAxisMinimum();
        yAxis.setLabelCount(10, false);

        radarChart.invalidate();
    }
}