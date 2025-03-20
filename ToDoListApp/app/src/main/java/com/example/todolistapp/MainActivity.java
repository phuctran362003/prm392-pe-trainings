package com.example.todolistapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Khai báo biến
    private EditText edtTitle, edtContent, edtDate, edtType;
    private Button btnAdd, btnUpdate, btnDelete;
    private ListView lvTasks;
    private DatabaseHelper databaseHelper;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;

    // Lưu ID của công việc được chọn
    private int selectedTaskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gán giao diện

        // Ánh xạ các thành phần
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtDate = findViewById(R.id.edtDate);
        edtType = findViewById(R.id.edtType);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        lvTasks = findViewById(R.id.lvTasks);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Gọi hàm để load danh sách công việc vào ListView
        loadTaskList();

        // Xử lý sự kiện khi nhấn nút ADD
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        // Xử lý sự kiện khi chọn một công việc trong ListView
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectTask(position);
            }
        });

        // Xử lý sự kiện khi nhấn nút UPDATE
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
            }
        });

        // Xử lý sự kiện khi nhấn nút DELETE
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
    }

    // Hàm load danh sách công việc từ database lên ListView
    private void loadTaskList() {
        taskList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllTasks();

        if (cursor.getCount() == 0) {
            taskList.add("Không có công việc nào!");
        } else {
            while (cursor.moveToNext()) {
                int taskId = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String date = cursor.getString(3);
                String type = cursor.getString(4);

                String task = taskId + " - " + title + " - " + content + " - Ngày: " + date + " - Loại: " + type;
                taskList.add(task);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        lvTasks.setAdapter(adapter);
    }

    // Hàm thêm công việc mới
    private void addTask() {
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String type = edtType.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty() || date.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean insertSuccess = databaseHelper.insertTask(databaseHelper.getWritableDatabase(), title, content, date, type);
        if (insertSuccess) {
            Toast.makeText(this, "Thêm công việc thành công!", Toast.LENGTH_SHORT).show();
            loadTaskList();
            clearFields();
        } else {
            Toast.makeText(this, "Thêm công việc thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm cập nhật công việc
    private void updateTask() {
        // Kiểm tra xem có công việc nào được chọn không
        if (selectedTaskId == -1) {
            Toast.makeText(this, "Vui lòng chọn một công việc để cập nhật!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy dữ liệu từ EditText
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String type = edtType.getText().toString().trim();

        // Kiểm tra dữ liệu có trống không
        if (title.isEmpty() || content.isEmpty() || date.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi hàm update từ DatabaseHelper
        boolean updateSuccess = databaseHelper.updateTask(selectedTaskId, title, content, date, type);

        if (updateSuccess) {
            Toast.makeText(this, "Cập nhật công việc thành công!", Toast.LENGTH_SHORT).show();
            loadTaskList(); // Cập nhật lại ListView
            clearFields(); // Xóa thông tin nhập
        } else {
            Toast.makeText(this, "Cập nhật công việc thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm xóa công việc
    private void deleteTask() {
        // Kiểm tra xem có công việc nào được chọn không
        if (selectedTaskId == -1) {
            Toast.makeText(this, "Vui lòng chọn một công việc để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi hàm xóa từ DatabaseHelper
        boolean deleteSuccess = databaseHelper.deleteTask(selectedTaskId);

        if (deleteSuccess) {
            Toast.makeText(this, "Xóa công việc thành công!", Toast.LENGTH_SHORT).show();
            loadTaskList(); // Cập nhật lại ListView
            clearFields(); // Xóa thông tin nhập
        } else {
            Toast.makeText(this, "Xóa công việc thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm xóa nội dung sau khi thêm thành công
    private void clearFields() {
        edtTitle.setText("");
        edtContent.setText("");
        edtDate.setText("");
        edtType.setText("");
        selectedTaskId = -1; // Xóa ID đã chọn
    }

    // Hàm chọn công việc từ ListView và hiển thị lên EditText
    private void selectTask(int position) {
        String selectedItem = taskList.get(position);

        // Kiểm tra nếu dữ liệu là "Không có công việc nào!" thì không làm gì
        if (selectedItem.equals("Không có công việc nào!")) {
            return;
        }

        // Tách dữ liệu theo dấu "-"
        String[] parts = selectedItem.split(" - ");

        // Kiểm tra nếu dữ liệu hợp lệ
        if (parts.length >= 5) {
            try {
                selectedTaskId = Integer.parseInt(parts[0].trim()); // Lấy ID
                edtTitle.setText(parts[1].trim());
                edtContent.setText(parts[2].trim());
                edtDate.setText(parts[3].replace("Ngày: ", "").trim());
                edtType.setText(parts[4].replace("Loại: ", "").trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi khi chọn công việc!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }

}