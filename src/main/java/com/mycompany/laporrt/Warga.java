/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

/**
 *
 * @author h y p e
 */

public class Warga extends User {
    private String alamat;

    public Warga(String id, String nama, String username, String password, String alamat) {
        super(id, nama, username, password);
        setAlamat(alamat);
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    @Override
    public String getRole() {
        return "Warga";
    }

    @Override
    public void tampilkanProfil() {
        super.tampilkanProfil();
        System.out.println("Alamat   : " + alamat);
    }
}



