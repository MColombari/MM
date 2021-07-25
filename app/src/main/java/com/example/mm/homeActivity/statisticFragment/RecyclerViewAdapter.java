package com.example.mm.homeActivity.statisticFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mm.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.slider.LabelFormatter;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<RecyclerViewRowData> recyclerViewRowDataArrayList;
    public RecyclerViewAdapter(Context context, ArrayList<RecyclerViewRowData> recyclerViewRowDataArrayList) {
        this.context = context;
        this.recyclerViewRowDataArrayList = recyclerViewRowDataArrayList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.course.setText(recyclerViewRowDataArrayList.get(position).getCourse());
        holder.value.setText(recyclerViewRowDataArrayList.get(position).getValue().toString());
        Drawable res = context.getResources().getDrawable(recyclerViewRowDataArrayList.get(position).getImagesTrend());
        holder.recyclerViewTrendIcon.setImageDrawable(res);

        ArrayList<BarEntry> entries = new ArrayList<>();
        int index = 0;
        for (RecyclerViewRowRecordData i : recyclerViewRowDataArrayList.get(position).getRecordDataArrayList()) {
            entries.add(new BarEntry(index, i.getValue()));
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Data");

        dataSet.setColors(context.getResources().getColor(R.color.cyan));
        dataSet.setValueTextColor(context.getResources().getColor(R.color.black));
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);

        holder.recyclerviewRowBarChart.getDescription().setEnabled(false);
        holder.recyclerviewRowBarChart.getLegend().setEnabled(false);
        holder.recyclerviewRowBarChart.setData(barData);
        holder.recyclerviewRowBarChart.getXAxis().setEnabled(false);
        holder.recyclerviewRowBarChart.invalidate();

        if(recyclerViewRowDataArrayList.get(position).getRecordDataArrayList().isEmpty()){
            holder.warning.setTextColor(context.getResources().getColor(R.color.red));
            holder.warning.setText("No record found");
        }
        else{
            holder.warning.setText("");
        }

        boolean isExpanded = recyclerViewRowDataArrayList.get(position).isExpanded();
        holder.recyclerviewRowExpandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return recyclerViewRowDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView course;
        TextView value;
        TextView warning;
        ImageView recyclerViewTrendIcon;
        ConstraintLayout recyclerviewRowMainLayout;
        ConstraintLayout recyclerviewRowExpandableLayout;
        BarChart recyclerviewRowBarChart;
        public ViewHolder(View view){
            super(view);
            course = (TextView) view.findViewById(R.id.RecyclerviewRowCourse);
            value = (TextView) view.findViewById(R.id.RecyclerviewRowValue);
            warning = (TextView) view.findViewById(R.id.RecyclerviewRowWarning);
            recyclerViewTrendIcon = (ImageView) view.findViewById(R.id.RecyclerViewTrendIcon);
            recyclerviewRowMainLayout = (ConstraintLayout) view.findViewById(R.id.RecyclerviewRowMainLayout);
            recyclerviewRowExpandableLayout = (ConstraintLayout) view.findViewById(R.id.RecyclerviewRowExpandableLayout);
            recyclerviewRowBarChart = (BarChart) view.findViewById(R.id.RecyclerviewRowBarChart);

            recyclerviewRowMainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewRowDataArrayList.get(getAbsoluteAdapterPosition()).isExpanded == false) {
                        for (int i = 0; i < (recyclerViewRowDataArrayList.size()); i++) {
                            /* "getAbsoluteAdapterPosition()" returns the Adapter position of the item represented
                             * by this ViewHolder */
                            RecyclerViewRowData recyclerViewRowData = recyclerViewRowDataArrayList.get(i);
                            recyclerViewRowData.setExpanded(getAbsoluteAdapterPosition() == i ? (!recyclerViewRowData.isExpanded()) : false);
                            /* "notifyItemChanged()" will call "onBindViewHolder()" method so the Layout will be
                             * updated. */
                            notifyItemChanged(i);
                        }
                    }
                    else{
                        RecyclerViewRowData recyclerViewRowData = recyclerViewRowDataArrayList.get(getAbsoluteAdapterPosition());
                        recyclerViewRowData.setExpanded(false);
                        notifyItemChanged(getAbsoluteAdapterPosition());
                    }
                }
            });
        }
    }
}
