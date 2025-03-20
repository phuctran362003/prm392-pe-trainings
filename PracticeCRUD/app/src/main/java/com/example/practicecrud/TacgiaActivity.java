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

import com.example.practicecrud.adapter.TacgiaAdapter;
import com.example.practicecrud.model.Tacgia;

import java.util.ArrayList;

public class TacgiaActivity extends AppCompatActivity {
    Database database;
    ListView lvTacgia;
    ArrayList<Tacgia> arrayTacgia;
    TacgiaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tacgia);

        Toolbar toolbar = findViewById(R.id.toolbarTacgia);
        setSupportActionBar(toolbar);

        Button btnBack = findViewById(R.id.buttonBackInTG);

        lvTacgia = (ListView) findViewById(R.id.listViewTacgia);
        arrayTacgia = new ArrayList<>();

        database = new Database(this, "SachTacgia.sqlite", null, 1);
        database.QueryData("Create table if not exists Tacgia(id Integer Primary Key Autoincrement, " +
                "TenTacgia nvarchar(200), Email nvarchar(30), DiaChi nvarchar(50), SoDienThoai nvarchar(20))");

        adapter = new TacgiaAdapter(this, R.layout.dong_tacgia, arrayTacgia);
        lvTacgia.setAdapter(adapter);

        GetDataTacgia();

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

    private void GetDataTacgia() {
        Cursor dataTacgia = database.GetData("Select * from Tacgia");
        arrayTacgia.clear();
        while (dataTacgia.moveToNext()) {
            String tentg =  dataTacgia.getString(1);
            int id = dataTacgia.getInt(0);
            String email = dataTacgia.getString(2);
            String diachi = dataTacgia.getString(3);
            String sodienthoai = dataTacgia.getString(4);
            arrayTacgia.add(new Tacgia(id, tentg, email, diachi, sodienthoai));
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
            DialogThemTG();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThemTG() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_tacgia);
        EditText edtTenTG = (EditText) dialog.findViewById(R.id.editTextAddNameTG);
        EditText edtEmail = (EditText) dialog.findViewById(R.id.editTextAddEmail);
        EditText edtDiaChi = (EditText) dialog.findViewById(R.id.editTextAddDiaChi);
        EditText edtSoDienThoai = (EditText) dialog.findViewById(R.id.editTextAddSodienthoai);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonAddTG);
        Button btnClose = (Button) dialog.findViewById(R.id.buttonCloseTG);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTG = edtTenTG.getText().toString();
                String email = edtEmail.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String soDienThoai = edtSoDienThoai.getText().toString();

//                int selectedPosition = edtSpinner.getSelectedItemPosition();
//                int authorId = tacgiaList.get(selectedPosition).getIDTacgia();

                if(tenTG.equals("")) {
                    Toast.makeText(TacgiaActivity.this, "Vui lòng nhập tên Tác giả", Toast.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Toast.makeText(TacgiaActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else if (diaChi.equals("")) {
                    Toast.makeText(TacgiaActivity.this, "Vui lòng nhập Địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (soDienThoai.equals("")) {
                    Toast.makeText(TacgiaActivity.this, "Vui lòng nhập Số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO Tacgia VALUES(null, '" + tenTG + "', '" + email + "', '" + diaChi + "', " + soDienThoai + ")");
                    Toast.makeText(TacgiaActivity.this, "Thêm Tác giả thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataTacgia();
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

    public void DialogXoaTacgia(String tenTacgia, int Id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Ban co muon xoa Tac gia "+ tenTacgia + " khong ?");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Tacgia WHERE Id = '"+  Id +"' ");
                Toast.makeText(TacgiaActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataTacgia();
            }
        });
        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public void DialogSuaTacgia(int id, String ten, String email, String diachi, String sodienthoai) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_tacgia);

        EditText edtTenTacgia = (EditText) dialog.findViewById(R.id.editTextEditNameTG);
        EditText edtEmail = (EditText) dialog.findViewById(R.id.editTextEditEmail);
        EditText edtDiachi = (EditText) dialog.findViewById(R.id.editTextEditDiaChi);
        EditText edtSoDienThoai = (EditText) dialog.findViewById(R.id.editTextEditSodienthoai);
        Button btnEdit = (Button) dialog.findViewById(R.id.buttonEditTG);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonCloseEditTG);

        edtTenTacgia.setText(ten);
        edtEmail.setText(email);
        edtDiachi.setText(diachi);
        edtSoDienThoai.setText(sodienthoai);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentgMoi = edtTenTacgia.getText().toString().trim();
                String emailMoi = edtEmail.getText().toString().trim();
                String diachiMoi = edtDiachi.getText().toString().trim();
                String sodienthoaiMoi = edtSoDienThoai.getText().toString();

                database.QueryData("UPDATE Tacgia SET TenTacgia = '" + tentgMoi + "', Email = '" + emailMoi +
                        "', DiaChi = '" + diachiMoi + "', SoDienThoai = " + sodienthoaiMoi +
                        " WHERE id = " + id);
                Toast.makeText(TacgiaActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataTacgia();
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