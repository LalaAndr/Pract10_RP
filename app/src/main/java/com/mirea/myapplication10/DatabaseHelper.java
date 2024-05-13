package com.mirea.myapplication10;
// Класс помощника для работы с базой данных
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Пропишем в отдельные переменные название БД и её столбов
    private static final String TABLE_NAME = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GRADE = "grade";

    public DatabaseHelper(Context context) {
        super(context, "students.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_GRADE + " TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    // Добавление нового ученика
    public boolean addStudent(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, student.getStudentName());
        cv.put(COLUMN_GRADE, student.getStudentGrade());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }
    // Удаление информации об ученике
    public boolean deleteStudent(String grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_GRADE + " = ?",
                new String[]{grade});
        db.close();
        return result > 0;
    }
    // Поиск ученика
    public Student findStudent(String grade) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new
                        String[]{COLUMN_ID, COLUMN_NAME, COLUMN_GRADE},
                COLUMN_GRADE + " = ?", new String[]{grade}, null,
                null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            db.close();
            return student;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }
    // Получение всех учеников
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }
    public boolean updateStudent(String oldGrade, String newName, String newGrade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, newName);
        cv.put(COLUMN_GRADE, newGrade);
        // Обновляем запись, где класс ученика равен oldGrade
        int result = db.update(TABLE_NAME, cv, COLUMN_GRADE + " = ?", new String[]{oldGrade});
        db.close();
        return result > 0;
    }
}
