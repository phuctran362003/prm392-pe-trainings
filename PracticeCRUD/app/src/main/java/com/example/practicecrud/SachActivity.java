package com.example.practicecrud;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.practicecrud.adapter.SachAdapter;
import com.example.practicecrud.model.Sach;
import com.example.practicecrud.model.Tacgia;

import java.util.ArrayList;
import java.util.Date;

public class SachActivity extends AppCompatActivity {
    Database database;
    ListView lvSach;
    ArrayList<Sach> arraySach;
    SachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sach);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnBack = findViewById(R.id.buttonBackInSach);

        lvSach = (ListView) findViewById(R.id.listViewSach);
        arraySach = new ArrayList<>();

        database = new Database(this, "SachTacgia.sqlite", null, 1);
        database.QueryData("Create table if not exists Sach(id Integer Primary Key Autoincrement, " +
                "TenSach nvarchar(200), NgayXB nvarchar(30), Theloai nvarchar(50), IdTacgia Integer)");

        adapter = new SachAdapter(this, R.layout.dong_sach, arraySach);
        lvSach.setAdapter(adapter);

        GetDataSach();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void GetDataSach() {
        Cursor dataSach = database.GetData("Select * from Sach");
        arraySach.clear();
        while (dataSach.moveToNext()) {
            String ten =  dataSach.getString(1);
            int id = dataSach.getInt(0);
            String ngayxb = dataSach.getString(2);
            String theloai = dataSach.getString(3);
            int idTacgia = dataSach.getInt(4);
            arraySach.add(new Sach(id, ten, ngayxb, theloai, idTacgia));
//            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sach, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them);
        EditText edtTenSach = (EditText) dialog.findViewById(R.id.editTextAddName);
        EditText edtTheLoai = (EditText) dialog.findViewById(R.id.editTextAddTheloai);
        EditText edtNgayXB = (EditText) dialog.findViewById(R.id.editTextAddDate);
        Spinner edtSpinner = (Spinner) dialog.findViewById(R.id.spinnerTacgiaSach);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonAdd);
        Button btnClose = (Button) dialog.findViewById(R.id.buttonClose);

        ArrayList<Tacgia> tacgiaList = getAuthorsFromDatabase();
        ArrayList<String> arrayTenTacgia = new ArrayList<>();
        for (Tacgia author : tacgiaList) {
            arrayTenTacgia.add(author.getTenTacgia());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayTenTacgia);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtSpinner.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                String theloai = edtTheLoai.getText().toString();
                String NgayXB = edtNgayXB.getText().toString();

                int selectedPosition = edtSpinner.getSelectedItemPosition();
                int authorId = tacgiaList.get(selectedPosition).getIDTacgia();

                if(tenSach.equals("")) {
                    Toast.makeText(SachActivity.this, "Vui lòng nhập tên Sách", Toast.LENGTH_SHORT).show();
                } else if (theloai.equals("")) {
                    Toast.makeText(SachActivity.this, "Vui lòng nhập thể loại Sách", Toast.LENGTH_SHORT).show();
                } else if (NgayXB.equals("")) {
                    Toast.makeText(SachActivity.this, "Vui lòng nhập Ngày xuất bản Sách", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO Sach VALUES(null, '" + tenSach + "', '" + NgayXB + "', '" + theloai + "', " + authorId + ")");
                    Toast.makeText(SachActivity.this, "Thêm Sách thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataSach();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private ArrayList<Tacgia> getAuthorsFromDatabase() {
        ArrayList<Tacgia> authors = new ArrayList<>();
        Cursor cursor = database.GetData("SELECT * FROM Tacgia");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            authors.add(new Tacgia(id, name));
        }
        cursor.close();
        return authors;
    }

    public void DialogXoaSach(String tenSach, int Id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Ban co muon xoa Sach "+ tenSach + " khong ?");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Sach WHERE Id = '"+  Id +"' ");
                Toast.makeText(SachActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataSach();
            }
        });
        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public void DialogSuaSach(int id, String ten, String ngayxb, String theloai, int idTacgia) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_sach);

        EditText edtTenSach = (EditText) dialog.findViewById(R.id.editTextEditName);
        EditText edtNgayXB = (EditText) dialog.findViewById(R.id.editTextEditDate);
        EditText edtTheLoai = (EditText) dialog.findViewById(R.id.editTextEditTheloai);
        Spinner edtSpinner = (Spinner) dialog.findViewById(R.id.spinnerEditTacgiaSach);
        Button btnEdit = (Button) dialog.findViewById(R.id.buttonEdit);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonCloseEdit);

        ArrayList<Tacgia> tacgiaList = getAuthorsFromDatabase();
        ArrayList<String> arrayTenTacgia = new ArrayList<>();
        for (Tacgia author : tacgiaList) {
            arrayTenTacgia.add(author.getTenTacgia());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayTenTacgia);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtSpinner.setAdapter(adapter);

        for (int i = 0; i < tacgiaList.size(); i++) {
            if (tacgiaList.get(i).getIDTacgia() == idTacgia) {
                edtSpinner.setSelection(i);
                break;
            }
        }

        edtTenSach.setText(ten);
        edtNgayXB.setText(ngayxb);
        edtTheLoai.setText(theloai);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTenSach.getText().toString().trim();
                String ngayXbMoi = edtNgayXB.getText().toString().trim();
                String theLoaiMoi = edtTheLoai.getText().toString().trim();
                int selectedPosition = edtSpinner.getSelectedItemPosition();
                int selectedAuthorId = tacgiaList.get(selectedPosition).getIDTacgia();

                database.QueryData("UPDATE Sach SET TenSach = '" + tenMoi + "', NgayXB = '" + ngayXbMoi +
                        "', TheLoai = '" + theLoaiMoi + "', IdTacgia = " + selectedAuthorId +
                        " WHERE id = " + id);
                Toast.makeText(SachActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataSach();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}