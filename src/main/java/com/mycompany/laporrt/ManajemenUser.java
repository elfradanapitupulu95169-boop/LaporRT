/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laporrt;

/**
 *
 * @author h y p e
 */


import java.util.ArrayList;


public class ManajemenUser {
    private ArrayList<User> daftarUser = new ArrayList<>();

    public String buatIdBaru() {
        return String.format("U%03d", daftarUser.size() + 1);
    }

    public void tambah(User user) {
        daftarUser.add(user);
    }

    public User cari(String username) {
        for (User u : daftarUser) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User login(String username, String password) {
        User u = cari(username);
        if (u != null && u.login(username, password)) {
            return u;
        }
        return null;
    }
}
