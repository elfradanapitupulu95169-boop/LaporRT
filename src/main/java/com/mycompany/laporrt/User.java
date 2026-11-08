/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

/**
 *
 * @author h y p e
 */

public abstract class User {
    private String id;
    private String nama;
    private String username;
    private String password;

    public User(String id, String nama, String username, String password) {
        this.id = id;
        setNama(nama);
        setUsername(username);
        setPassword(password);
    }

    // ===== Getter =====
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    // Password sengaja tidak punya getter agar tidak bisa dibaca dari luar.

    // ===== Setter dengan validasi =====
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }
        this.username = username.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 3) {
            throw new IllegalArgumentException("Password minimal 3 karakter.");
        }
        this.password = password;
    }

    // ===== Autentikasi =====
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Method abstrak: wajib di-override oleh subclass (Polymorphism)
    public abstract String getRole();

    public void tampilkanProfil() {
        System.out.println("ID       : " + id);
        System.out.println("Nama     : " + nama);
        System.out.println("Username : " + username);
        System.out.println("Role     : " + getRole());
    }
}