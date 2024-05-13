package com.mirea.myapplication10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        EditText nameInput = findViewById(R.id.name_input);
        EditText gradeInput = findViewById(R.id.grade_input);
        Button saveButton = findViewById(R.id.save_button);
        Button deleteButton = findViewById(R.id.delete_button);
        Button findButton = findViewById(R.id.find_button);
        RecyclerView studentsList = findViewById(R.id.students_list);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Student> students = dbHelper.getAllStudents();
        StudentAdapter adapter = new StudentAdapter((ArrayList<Student>) students);
        studentsList.setLayoutManager(new LinearLayoutManager(this));
        studentsList.setAdapter(adapter);
        //Прописываем логику для сохранения нового ученика
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String grade = gradeInput.getText().toString();
            if (dbHelper.addStudent(new Student(0, name, grade)))
            {
                students.add(new Student(0, name, grade));
                adapter.notifyItemInserted(students.size() - 1);
                Toast.makeText(this, "Student saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save student",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //удаляем ученикаВ
        deleteButton.setOnClickListener(v -> {
            String grade = gradeInput.getText().toString();
            if (dbHelper.deleteStudent(grade)) {
                int position = -1;
                for (int i = 0; i < students.size(); i++) {
                    if (students.get(i).getStudentGrade().equals(grade))
                    {
                        position = i;
                        students.remove(i);
                        break;
                    }
                }
                if (position != -1) {
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Student deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Student not found",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to delete student",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //ищем судента по номеру телефона
        findButton.setOnClickListener(v -> {
            String grade = gradeInput.getText().toString();
            Student foundStudent = dbHelper.findStudent(grade);
            if (foundStudent != null) {
                nameInput.setText(foundStudent.getStudentName());
                gradeInput.setText(foundStudent.getStudentGrade());
                Toast.makeText(this, "Student found: " +
                        foundStudent.getStudentName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Student not found",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //обновляем данные
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(v -> {
            String oldGrade = gradeInput.getText().toString(); //Считаем что это старый номер для поиска
            String newName = nameInput.getText().toString(); //Новое имя для обновления
            String newGrade = gradeInput.getText().toString(); //Новый номер для обновления
            if (dbHelper.updateStudent(oldGrade, newName, newGrade)) {
                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
                // Обновляем список и адаптер
                refreshStudentsList(dbHelper, students, adapter, studentsList);
            } else {
                Toast.makeText(this, "Failed to update student",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Метод для обновления списка учеников после изменения в базе данных
    private void refreshStudentsList(DatabaseHelper dbHelper, List<Student> students, StudentAdapter adapter, RecyclerView studentsList)
    {
        students = dbHelper.getAllStudents(); // Загружаем обновленный список
        adapter = new StudentAdapter((ArrayList<Student>) students);
        studentsList.setAdapter(adapter);
    }
}
