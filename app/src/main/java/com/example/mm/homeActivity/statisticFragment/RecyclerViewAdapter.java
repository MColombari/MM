package com.example.mm.homeActivity.statisticFragment;

import android.content.Context;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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

        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        for (RecyclerViewRowRecordData i : recyclerViewRowDataArrayList.get(position).getRecordDataArrayList()) {
            entries.add(new Entry(index, i.getValue()));
            index++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Data");

        dataSet.setColors(context.getResources().getColor(R.color.cyan));
        dataSet.setValueTextColor(context.getResources().getColor(R.color.black));
        dataSet.setValueTextSize(10f);
        dataSet.setLineWidth(3);

        LineData lineData = new LineData(dataSet);

        holder.recyclerviewRowLineChart.getDescription().setEnabled(false);
        holder.recyclerviewRowLineChart.getLegend().setEnabled(false);
        holder.recyclerviewRowLineChart.setData(lineData);
        holder.recyclerviewRowLineChart.invalidate();

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
        LineChart recyclerviewRowLineChart;
        public ViewHolder(View view){
            super(view);
            course = view.findViewById(R.id.RecyclerviewRowCourse);
            value = view.findViewById(R.id.RecyclerviewRowValue);
            warning = view.findViewById(R.id.RecyclerviewRowWarning);
            recyclerViewTrendIcon = view.findViewById(R.id.RecyclerViewTrendIcon);
            recyclerviewRowMainLayout = view.findViewById(R.id.RecyclerviewRowMainLayout);
            recyclerviewRowExpandableLayout = view.findViewById(R.id.RecyclerviewRowExpandableLayout);
            recyclerviewRowLineChart = view.findViewById(R.id.RecyclerviewRowLineChart);

            recyclerviewRowMainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* "getAbsoluteAdapterPosition()" returns the Adapter position of the item represented
                     * by this ViewHolder */
                    RecyclerViewRowData recyclerViewRowData = recyclerViewRowDataArrayList.get(getAbsoluteAdapterPosition());
                    recyclerViewRowData.setExpanded(!recyclerViewRowData.isExpanded());
                    /* "notifyItemChanged()" will call "onBindViewHolder()" method so the Layout will be
                     * updated. */
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
