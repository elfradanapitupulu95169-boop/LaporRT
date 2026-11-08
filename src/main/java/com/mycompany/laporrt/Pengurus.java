/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

/**
 *
 * @author h y p e
 */

public class Pengurus extends User {
    private String jabatan;

    public Pengurus(String id, String nama, String username, String password, String jabatan) {
        super(id, nama, username, password);
        setJabatan(jabatan);
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        if (jabatan == null || jabatan.trim().isEmpty()) {
            throw new IllegalArgumentException("Jabatan tidak boleh kosong.");
        }
        this.jabatan = jabatan.trim();
    }

    @Override
    public String getRole() {
        return "Pengurus RT/RW";
    }

    @Override
    public void tampilkanProfil() {
        super.tampilkanProfil();
        System.out.println("Jabatan  : " + jabatan);
    }
}