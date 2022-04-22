package com.example.vikramassignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder>{

    //variable declaration
    List<Modal> gradeList;
    private Context context;

    public GradeAdapter(List<Modal> gradeList, Context context) {
        this.gradeList = gradeList;
        this.context = context;
    }

    // create function view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //setting item view of holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //setting name to text view
        holder.fname.setText(gradeList.get(position).getfName());
        holder.lname.setText(gradeList.get(position).getlName());
        holder.course.setText(gradeList.get(position).getCourse());
        holder.credit.setText(String.valueOf(gradeList.get(position).getCredit()));
        holder.grades.setText(String.valueOf(gradeList.get(position).getGrades()));


    }

    //getting total size
    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView fname, lname, course, credit, grades;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //finding view by id
            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);
            course = itemView.findViewById(R.id.course);
            credit = itemView.findViewById(R.id.credit);
            grades = itemView.findViewById(R.id.grades);

        }
    }

}
