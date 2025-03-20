package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "ToDoList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASKS = "tasks";


    // Cột trong bảng
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";

    // Tạo bảng SQL
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_CONTENT + " TEXT, "
            + COLUMN_DATE + " TEXT, "
            + COLUMN_TYPE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        insertSampleData(db); // Thêm dữ liệu mẫu khi tạo database
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Thêm dữ liệu mẫu
    private void insertSampleData(SQLiteDatabase db) {
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học Java", "Học Java cơ bản", "27/02/2023", "Dễ");
        insertTask(db, "Học React Native", "Học React Native cơ bản", "24/03/2023", "Trung bình");
    }

    // Hàm thêm công việc vào database
    public boolean insertTask(SQLiteDatabase db, String title, String content, String date, String type) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TYPE, type);
        long result = db.insert(TABLE_TASKS, null, values);
        return result != -1; // Trả về true nếu chèn thành công
    }

    // Hàm cập nhật công việc trong database
    public boolean updateTask(int id, String title, String content, String date, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("date", date);
        values.put("type", type);

        int rowsAffected = db.update("tasks", values, "id=?", new String[]{String.valueOf(id)});
        db.close();

        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
    }

    // Hàm xóa công việc khỏi database
    public boolean deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("tasks", "id=?", new String[]{String.valueOf(id)});
        db.close();

        return rowsDeleted > 0; // Trả về true nếu có ít nhất một dòng bị xóa
    }


    // Hàm lấy danh sách công việc từ database
    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS + " ORDER BY " + COLUMN_TITLE + " ASC", null);
    }
}
