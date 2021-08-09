package com.example.mm.homeActivity.statisticFragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

import com.example.mm.homeActivity.localDatabaseInteraction.GetCourseStatistic;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/* https://www.anychart.com/technical-integrations/samples/android-charts/ */

public class StatisticFragment extends Fragment implements View.OnClickListener {
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

        ImageView questionMark = (ImageView) view.findViewById(R.id.Statistic_Fragment_Question_Mark);

        statisticMoreText.setOnClickListener((Home) getActivity());
        questionMark.setOnClickListener(this);

        Thread t = new Thread(new GetCourseStatistic(getContext(), this));
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Statistic_Fragment_Question_Mark){
            /* Show information about  */
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(20);
            }
            popupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
            popupWindow.update();
            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
            TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
            TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            title.setText("More about user statistics.");
            text.setText(   "The radar graph shown rapresents the average points per courses, the " +
                            "points range is [0-100].\n" +
                            "Click 'More' to see more information per course.\n" +
                            "If no data is shown it means that there aren't any record about any " +
                            "question the used has answered, this could happen when all the courses " +
                            "were deleted, or because you never answer any question.");

        }
    }
}