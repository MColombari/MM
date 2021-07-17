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
        ImageView recyclerViewTrendIcon;
        ConstraintLayout recyclerviewRowMainLayout;
        ConstraintLayout recyclerviewRowExpandableLayout;
        public ViewHolder(View view){
            super(view);
            course = view.findViewById(R.id.RecyclerviewRowCourse);
            value = view.findViewById(R.id.RecyclerviewRowValue);
            recyclerViewTrendIcon = view.findViewById(R.id.RecyclerViewTrendIcon);
            recyclerviewRowMainLayout = view.findViewById(R.id.RecyclerviewRowMainLayout);
            recyclerviewRowExpandableLayout = view.findViewById(R.id.RecyclerviewRowExpandableLayout);

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
