package com.example.practicecrud.model;

import java.util.Date;

public class Sach {
    private int IDsach;
    private String Tensach;
    private String NgayXB;
    private String Theloai;
    private int idTacgia;

    public Sach(int IDsach, String tensach, String ngayXB, String theloai, int idTacgia) {
        this.IDsach = IDsach;
        Tensach = tensach;
        NgayXB = ngayXB;
        Theloai = theloai;
        this.idTacgia = idTacgia;
    }

    public int getIDsach() {
        return IDsach;
    }

    public void setIDsach(int IDsach) {
        this.IDsach = IDsach;
    }

    public String getTensach() {
        return Tensach;
    }

    public String getNgayXB() {
        return NgayXB;
    }

    public void setNgayXB(String ngayXB) {
        NgayXB = ngayXB;
    }

    public void setTensach(String tensach) {
        Tensach = tensach;
    }


    public String getTheloai() {
        return Theloai;
    }

    public void setTheloai(String theloai) {
        Theloai = theloai;
    }

    public int getIdTacgia() {
        return idTacgia;
    }

    public void setIdTacgia(int idTacgia) {
        this.idTacgia = idTacgia;
    }
}
