package com.mirea.myapplication10;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Student_id")
    int id;

    @ColumnInfo(name = "Name")
    String StudentName;

    @ColumnInfo(name = "Grade")
    String StudentGrade;

    public Student(int id, String StudentName, String StudentGrade) {
        this.id = id;
        this.StudentName = StudentName;
        this.StudentGrade = StudentGrade;
    }

    @NonNull
    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(@NonNull String StudentName) {
        this.StudentName = StudentName;
    }

    public String getStudentGrade() {
        return StudentGrade;
    }

    public void setStudentGrade(String StudentGrade) {
        this.StudentGrade = StudentGrade;
    }
}