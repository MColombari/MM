package com.example.mm.homeActivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import localDatabase.TestApp;
import localDatabaseInteraction.getCourse;

/* https://www.anychart.com/technical-integrations/samples/android-charts/ */

public class StatisticFragment extends Fragment {
    TextView status;
    TextView currentCourse;
    RadarChart radarChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = view.findViewById(R.id.statisticStatus);
        currentCourse = view.findViewById(R.id.currentCourse);
        radarChart = view.findViewById(R.id.StatisticGraph);

        Thread t = new Thread(new getCourse(getContext(), this));
        t.start();
    }

    public void updateStatistic(String statusText, int color, String courseText){
        status.setText(statusText);
        status.setTextColor(color);
        currentCourse.setText(courseText);
    }

    public void updateGraph(ArrayList<String> courseName, ArrayList<Float> values){
        ArrayList radarEntries = new ArrayList();
        for(float f : values){
            radarEntries.add(new RadarEntry(f));
        }

        RadarData radarData;
        RadarDataSet radarDataSet;

        radarDataSet = new RadarDataSet(radarEntries, "");
        radarData = new RadarData(radarDataSet);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(courseName));

        radarDataSet.setColors(R.color.blue);
        radarDataSet.setValueTextColor(Color.BLACK);
        radarDataSet.setValueTextSize(10f);
        radarDataSet.setHighlightCircleFillColor(R.color.cyan);
        radarDataSet.setLineWidth(3);

        /* Continua da qui: https://www.programmersought.com/article/95216335605/ */

        radarChart.getDescription().setEnabled(false);
        radarChart.getLegend().setEnabled(false);
        radarChart.setData(radarData);
        radarChart.invalidate();
    }
}