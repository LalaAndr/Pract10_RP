package com.mirea.myapplication10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private ArrayList<Student> students;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView gradeTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.student_name);
            gradeTextView = view.findViewById(R.id.student_grade);
        }

        public void bind(Student student) {
            nameTextView.setText(student.getStudentName());
            gradeTextView.setText(student.getStudentGrade());
        }
    }

    public StudentAdapter(ArrayList<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Students list cannot be null");
        }
        this.students = students;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setStudents(ArrayList<Student> newStudents) {
        if (newStudents == null) {
            throw new IllegalArgumentException("Students list cannot be null");
        }
        this.students = newStudents;
        notifyDataSetChanged();
    }

}