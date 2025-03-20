package com.example.practicecrud.model;

public class Tacgia {
    private int IDTacgia;
    private String TenTacgia;
    private String Email;
    private String Diachi;
    private String DienThoai;

    public Tacgia(int IDTacgia, String tenTacgia, String email, String diachi, String dienThoai) {
        this.IDTacgia = IDTacgia;
        TenTacgia = tenTacgia;
        Email = email;
        Diachi = diachi;
        DienThoai = dienThoai;
    }

    public Tacgia(int id, String name) {
        IDTacgia = id;
        TenTacgia = name;
    }

    public int getIDTacgia() {
        return IDTacgia;
    }

    public void setIDTacgia(int IDTacgia) {
        this.IDTacgia = IDTacgia;
    }

    public String getTenTacgia() {
        return TenTacgia;
    }

    public void setTenTacgia(String tenTacgia) {
        TenTacgia = tenTacgia;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }
}
