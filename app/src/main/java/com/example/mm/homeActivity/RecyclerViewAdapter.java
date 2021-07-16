package com.example.mm.homeActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mm.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<String> courses;
    ArrayList<Float> values;
    ArrayList<Integer> imagesTrends;

    public RecyclerViewAdapter(Context context, ArrayList<String> courses, ArrayList<Float> values, ArrayList<Integer> imagesTrends) {
        this.context = context;
        this.courses = courses;
        this.values = values;
        this.imagesTrends = imagesTrends;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.course.setText(courses.get(position));
        holder.value.setText(values.get(position).toString());
        Drawable res = context.getResources().getDrawable(imagesTrends.get(position));
        holder.recyclerViewTrendIcon.setImageDrawable(res);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, courses.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView course;
        TextView value;
        ImageView recyclerViewTrendIcon;
        ConstraintLayout constraintLayout;
        public ViewHolder(View view){
            super(view);
            course = view.findViewById(R.id.RecyclerviewRowCourse);
            value = view.findViewById(R.id.RecyclerviewRowValue);
            recyclerViewTrendIcon = view.findViewById(R.id.RecyclerViewTrendIcon);
            constraintLayout = view.findViewById(R.id.RecyclerviewRowId);
        }
    }
}
